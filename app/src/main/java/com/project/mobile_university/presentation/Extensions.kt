package com.project.mobile_university.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T> MediatorLiveData<T>.addSource(liveData: LiveData<T>) {
    addSource(liveData) {
        postValue(it)
    }
}