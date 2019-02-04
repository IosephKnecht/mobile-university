package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.ScheduleService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import dagger.Component

@Component(modules = [BusinessModule::class], dependencies = [AppComponent::class])
@PerBusinessLayerScope
interface BusinessComponent {
    fun apiService(): ApiService
    fun sharedPrefService(): SharedPreferenceService
    fun databaseService(): DatabaseService
    fun errorHandler(): ExceptionConverter
    fun scheduleService(): ScheduleService
}