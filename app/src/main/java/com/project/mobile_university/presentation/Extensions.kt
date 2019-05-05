package com.project.mobile_university.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.Gson
import com.project.mobile_university.domain.UniversityApi
import com.project.mobile_university.domain.adapters.exception.ExceptionAdapter
import com.project.mobile_university.domain.adapters.retrofit.RxErrorCallFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

fun <T> MediatorLiveData<T>.addSource(liveData: LiveData<T>) {
    addSource(liveData) {
        postValue(it)
    }
}

fun Date.more(date: Date?): Boolean {
    return date?.let {
        this > it
    } ?: true
}

fun Date.less(date: Date?): Boolean {
    return date?.let {
        this < it
    } ?: true
}

fun createUniversityApi(
    httpClient: OkHttpClient,
    gson: Gson,
    retrofitExceptionAdapter: ExceptionAdapter,
    serviceUrl: String
): UniversityApi {
    return Retrofit.Builder()
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxErrorCallFactory.create(retrofitExceptionAdapter))
        .baseUrl(serviceUrl)
        .build()
        .create(UniversityApi::class.java)
}