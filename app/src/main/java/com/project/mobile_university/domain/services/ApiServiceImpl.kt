package com.project.mobile_university.domain.services

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.UniversityApi
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.AuthUtil
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.createUniversityApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import java.util.*

class ApiServiceImpl(
    private val sharedPreferenceService: SharedPreferenceService,
    private val gson: Gson,
    private val okHttpClient: OkHttpClient,
    private val retrofitExceptionAdapter: ExceptionAdapter,
    private var universityApi: UniversityApi
) : ApiService {

    override fun updateServiceUrl(serviceUrl: String): Observable<Unit> {
        return Observable.fromCallable {
            universityApi = createUniversityApi(
                httpClient = okHttpClient,
                gson = gson,
                retrofitExceptionAdapter = retrofitExceptionAdapter,
                serviceUrl = serviceUrl
            )
        }
    }

    override fun login(login: String, password: String): Observable<BaseServerResponse<User>> {
        val authString = AuthUtil.convertToBase64(login, password)

        return universityApi.login(authString)
            .subscribeOn(Schedulers.io())
    }

    override fun logout(): Observable<Nothing> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        return universityApi.logout(loginPassString)
    }

    override fun getScheduleByDate(
        currentDate: Date,
        subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>> {

        val loginPassString = sharedPreferenceService.getLoginPassString()
        val currentDateString = CalendarUtil.convertToSimpleFormat(currentDate)

        return universityApi.getScheduleDayForSubgroup(loginPassString, currentDateString, subgroupId)
    }

    override fun getScheduleOfWeekForSubgroup(
        startWeek: Date,
        endWeek: Date,
        subgroupId: Long
    ): Observable<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForSubgroup(loginPassString, dateRangeString, subgroupId)
    }

    override fun getScheduleOfWeekForTeacher(
        startWeek: Date,
        endWeek: Date,
        teacherId: Long
    ): Observable<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForTeacher(loginPassString, dateRangeString, teacherId)
    }

    override fun updateLessonStatus(lessonId: Long, body: JsonObject): Observable<Unit> {
        return Observable.fromCallable { sharedPreferenceService.getLoginPassString() }
            .flatMap { loginPassString -> universityApi.patchLessonStatus(loginPassString, lessonId, body) }
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