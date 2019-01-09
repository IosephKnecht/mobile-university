package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerBusinessLayerScope
import com.project.mobile_university.domain.ApiService
import dagger.Component

@Component(modules = [BusinessModule::class], dependencies = [AppComponent::class])
@PerBusinessLayerScope
interface BusinessComponent {
    fun getApiService(): ApiService
}