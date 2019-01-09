package com.project.mobile_university.application.assembly

import android.app.Application
import android.content.Context
import com.project.mobile_university.application.annotations.PerApplicationLayerScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    @PerApplicationLayerScope
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @PerApplicationLayerScope
    fun provideContext(): Context {
        return application.applicationContext
    }
}