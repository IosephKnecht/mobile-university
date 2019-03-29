package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.domain.services.ApiServiceImpl
import com.project.mobile_university.domain.services.DatabaseServiceImpl
import com.project.mobile_university.domain.services.ScheduleService
import com.project.mobile_university.domain.services.SharedPreferenceServiceImpl
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import dagger.Component

@Component(modules = [BusinessModule::class], dependencies = [AppComponent::class])
@PerBusinessLayerScope
interface BusinessComponent {
    fun loginRepository(): LoginRepository
    fun sharedPrefService(): SharedPreferenceService
    fun errorHandler(): ExceptionConverter
    fun scheduleService(): ScheduleService
}