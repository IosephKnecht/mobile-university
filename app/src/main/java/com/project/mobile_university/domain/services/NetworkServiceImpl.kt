package com.project.mobile_university.domain.services

import android.content.Context
import android.net.ConnectivityManager
import com.project.mobile_university.domain.shared.NetworkService
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class NetworkServiceImpl(private val context: Context) : NetworkService {

    companion object {
        private const val INTERVAL = 5L
    }

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun getNetworkObservable(): Observable<Boolean> {
        val source = Observable.interval(INTERVAL, TimeUnit.SECONDS).map {
            connectivityManager.activeNetworkInfo?.run { true } ?: false
        }

        return source
    }
}