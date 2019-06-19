package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.*
import dagger.Component

@Component(modules = [BusinessModule::class], dependencies = [AppComponent::class])
@PerBusinessLayerScope
interface BusinessComponent {
    fun loginRepository(): LoginRepository
    fun sharedPrefService(): SharedPreferenceService
    fun errorHandler(): ExceptionConverter
    fun scheduleRepository(): ScheduleRepository
    fun databaseService(): DatabaseService
    fun networkService(): NetworkService
}