package com.project.mobile_university.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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