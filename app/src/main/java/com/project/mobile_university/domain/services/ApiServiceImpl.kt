package com.project.mobile_university.domain.services

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.*
import com.project.mobile_university.domain.UniversityApi
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.AuthUtil
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.createUniversityApi
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.OkHttpClient
import java.util.*

class ApiServiceImpl(
    private val sharedPreferenceService: SharedPreferenceService,
    private val gson: Gson,
    private val okHttpClient: OkHttpClient,
    private val retrofitExceptionAdapter: ExceptionAdapter,
    private var universityApi: UniversityApi
) : ApiService {

    override fun updateServiceUrl(serviceUrl: String): Completable {
        return Completable.fromAction {
            universityApi = createUniversityApi(
                httpClient = okHttpClient,
                gson = gson,
                retrofitExceptionAdapter = retrofitExceptionAdapter,
                serviceUrl = serviceUrl
            )
        }
    }

    override fun login(login: String, password: String): Single<BaseServerResponse<User>> {
        val authString = AuthUtil.convertToBase64(login, password)

        return universityApi.login(authString)
    }

    override fun getScheduleByDate(
        currentDate: Date,
        subgroupId: Long
    ): Single<BaseServerResponse<ScheduleDay>> {

        val loginPassString = sharedPreferenceService.getLoginPassString()
        val currentDateString = CalendarUtil.convertToSimpleFormat(currentDate)

        return universityApi.getScheduleDayForSubgroup(loginPassString, currentDateString, subgroupId)
    }

    override fun getScheduleOfWeekForSubgroup(
        startWeek: Date,
        endWeek: Date,
        subgroupId: Long
    ): Single<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForSubgroup(loginPassString, dateRangeString, subgroupId)
    }

    override fun getScheduleOfWeekForTeacher(
        startWeek: Date,
        endWeek: Date,
        teacherId: Long
    ): Single<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForTeacher(loginPassString, dateRangeString, teacherId)
    }

    override fun updateLessonStatus(lessonId: Long, body: JsonObject): Completable {
        return Single.fromCallable { sharedPreferenceService.getLoginPassString() }
            .flatMapCompletable { loginPassString -> universityApi.patchLessonStatus(loginPassString, lessonId, body) }
    }

    override fun getLesson(lessonId: Long): Single<Lesson> {
        return Single.fromCallable {
            sharedPreferenceService.getLoginPassString()
        }.flatMap { loginPassString -> universityApi.getLesson(loginPassString, lessonId) }
    }

    override fun getCheckList(checkListExtId: Long): Single<List<CheckListRecord>> {
        return Single.fromCallable {
            sharedPreferenceService.getLoginPassString()
        }.flatMap { loginPassString ->
            universityApi.getCheckList(loginPassString, checkListExtId)
                .map { serverResponse -> serverResponse.objectList!! }
        }
    }

    override fun putCheckList(checkListExtId: Long, records: JsonObject): Completable {
        return Single.fromCallable {
            sharedPreferenceService.getLoginPassString()
        }.flatMapCompletable { loginPassString ->
            universityApi.putCheckList(loginPassString, checkListExtId, records)
        }
    }

    override fun createCheckList(lessonId: Long): Completable {
        return Single.fromCallable {
            sharedPreferenceService.getLoginPassString()
        }.flatMapCompletable { loginPassString ->
            val lessonOwner = JsonObject().apply {
                addProperty("schedule_cell", "${UniversityApi.SCHEDULE_CELL_PATH}$lessonId/")
            }
            universityApi.createCheckList(loginPassString, lessonOwner)
        }
    }

    override fun getTeachers(limit: Int, offset: Int): Single<BaseServerResponse<Teacher>> {
        return Single.fromCallable {
            sharedPreferenceService.getLoginPassString()
        }.flatMap { loginPassString ->
            universityApi.getTeachers(loginPassString, limit, offset)
        }
    }

    override fun getUserInfo(userId: Long): Single<UserInformation> {
        return Single.fromCallable {
            sharedPreferenceService.getLoginPassString()
        }.flatMap { loginPassString -> universityApi.getUserInfo(loginPassString, userId) }
    }

    override fun getScheduleOfWeekForTeacher(
        startWeek: Date,
        endWeek: Date,
        teacherId: Long,
        limit: Int,
        offset: Int
    ): Single<BaseServerResponse<ScheduleDay>> {
        return Single.fromCallable {
            val loginPassString = sharedPreferenceService.getLoginPassString()
            val dateRangeString = obtainDateRangeString(startWeek, endWeek)

            Pair(loginPassString, dateRangeString)
        }.flatMap { (loginPassString, dateRangeString) ->
            universityApi.getScheduleWeekForTeacher(loginPassString, dateRangeString, teacherId, limit, offset)
        }
    }

    // TODO: will be transited on CalendarUtil
    private fun obtainDateRangeString(
        startWeek: Date,
        endWeek: Date
    ): String {
        val startWeekString = CalendarUtil.convertToSimpleFormat(startWeek)
        val endWeekString = CalendarUtil.convertToSimpleFormat(endWeek)
        return "$startWeekString,$endWeekString"
    }
}