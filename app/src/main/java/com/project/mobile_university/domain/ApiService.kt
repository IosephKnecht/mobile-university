package com.project.mobile_university.domain

import com.google.gson.Gson
import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.adapters.retrofit.RxErrorCallFactory
import com.project.mobile_university.domain.utils.AuthUtil
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ApiService(private val sharedPreferenceService: SharedPreferenceService,
                 private val gson: Gson,
                 private val okHttpClient: OkHttpClient,
                 private val retrofitExceptionAdapter: ExceptionAdapter) {

    private lateinit var universityApi: UniversityApi

    var serviceUrl: String? = null
        set(value) {
            field = value

            if (value != null && value.isNotEmpty()) {
                universityApi = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxErrorCallFactory.create(retrofitExceptionAdapter))
                    .baseUrl(value)
                    .build()
                    .create(UniversityApi::class.java)
            }
        }

    fun login(login: String, password: String): Observable<BaseServerResponse<User>> {
        val authString = AuthUtil.convertToBase64(login, password)

        return universityApi.login(authString)
            .subscribeOn(Schedulers.io())
    }

    fun getScheduleByDate(currentDate: Date,
                          subgroupId: Long): Observable<BaseServerResponse<ScheduleDay>> {

        val loginPassString = sharedPreferenceService.getLoginPassString()
        val currentDateString = CalendarUtil.convertToSimpleFormat(currentDate)

        return universityApi.getScheduleDayForSubgroup(loginPassString, currentDateString, subgroupId)
    }

    fun getScheduleOfWeekForSubgroup(startWeek: Date,
                                     endWeek: Date,
                                     subgroupId: Long): Observable<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForSubgroup(loginPassString, dateRangeString, subgroupId)
    }

    fun getScheduleOfWeekForTeacher(startWeek: Date,
                                    endWeek: Date,
                                    teacherId: Long): Observable<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForTeacher(loginPassString, dateRangeString, teacherId)
    }

    // TODO: will be transited on CalendarUtil
    private fun obtainDateRangeString(startWeek: Date,
                                      endWeek: Date): String {
        val startWeekString = CalendarUtil.convertToSimpleFormat(startWeek)
        val endWeekString = CalendarUtil.convertToSimpleFormat(endWeek)
        return "$startWeekString,$endWeekString"
    }
}