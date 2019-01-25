package com.project.mobile_university.domain

import com.google.gson.Gson
import com.project.mobile_university.data.gson.BaseServerResponse
import com.project.mobile_university.data.gson.ScheduleDay
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.utils.AuthUtil
import com.project.mobile_university.domain.utils.DateUtil
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ApiService(private val sharedPreferenceService: SharedPreferenceService,
                 private val gson: Gson) {
    lateinit var universityApi: UniversityApi

    var serviceUrl: String? = null
        set(value) {
            field = value

            if (value != null && value.isNotEmpty()) {
                universityApi = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
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
        val currentDateString = DateUtil.convertToSimpleFormat(currentDate)

        return universityApi.scheduleDay(loginPassString, currentDateString, subgroupId)
    }
}