package com.project.mobile_university.application

import android.app.Application
import com.project.mobile_university.application.assembly.*

class AppDelegate : Application() {

    companion object {
        lateinit var presentationComponent: PresentationComponent
        lateinit var businessComponent: BusinessComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        val appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        businessComponent = DaggerBusinessComponent.builder()
            .appComponent(appComponent)
            .businessModule(BusinessModule())
            .build()

        presentationComponent = DaggerPresentationComponent.builder()
            .businessComponent(businessComponent)
            .presentationModule(PresentationModule())
            .build()
    }
}