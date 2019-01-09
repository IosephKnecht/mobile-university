package com.project.mobile_university.application.assembly

import android.app.Application
import android.content.Context
import com.project.mobile_university.application.annotations.PerApplicationLayerScope
import dagger.Component

@Component(modules = [AppModule::class])
@PerApplicationLayerScope
interface AppComponent {
    fun getContext(): Context
    fun getApplication(): Application
}