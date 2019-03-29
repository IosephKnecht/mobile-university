package com.project.mobile_university.domain.services

import com.google.gson.Gson
import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.UniversityApi
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.adapters.retrofit.RxErrorCallFactory
import com.project.mobile_university.domain.shared.ApiService
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.domain.utils.AuthUtil
import com.project.mobile_university.domain.utils.CalendarUtil
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ApiServiceImpl(private val sharedPreferenceService: SharedPreferenceService,
                     private val gson: Gson,
                     private val okHttpClient: OkHttpClient,
                     private val retrofitExceptionAdapter: ExceptionAdapter) : ApiService {

    private lateinit var universityApi: UniversityApi

    override fun updateServiceUrl(serviceUrl: String): Observable<Unit> {
        return Observable.fromCallable {
            universityApi = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorCallFactory.create(retrofitExceptionAdapter))
                .baseUrl(serviceUrl)
                .build()
                .create(UniversityApi::class.java)
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

    override fun getScheduleByDate(currentDate: Date,
                                   subgroupId: Long): Observable<BaseServerResponse<ScheduleDay>> {

        val loginPassString = sharedPreferenceService.getLoginPassString()
        val currentDateString = CalendarUtil.convertToSimpleFormat(currentDate)

        return universityApi.getScheduleDayForSubgroup(loginPassString, currentDateString, subgroupId)
    }

    override fun getScheduleOfWeekForSubgroup(startWeek: Date,
                                              endWeek: Date,
                                              subgroupId: Long): Observable<BaseServerResponse<ScheduleDay>> {
        val loginPassString = sharedPreferenceService.getLoginPassString()
        val dateRangeString = obtainDateRangeString(startWeek, endWeek)

        return universityApi.getScheduleWeekForSubgroup(loginPassString, dateRangeString, subgroupId)
    }

    override fun getScheduleOfWeekForTeacher(startWeek: Date,
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