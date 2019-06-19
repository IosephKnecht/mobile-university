package com.project.mobile_university.domain.shared

import io.reactivex.Observable

interface NetworkService {
    fun getNetworkObservable(): Observable<Boolean>
}