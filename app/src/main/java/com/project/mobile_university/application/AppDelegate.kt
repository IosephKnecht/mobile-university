package com.project.mobile_university.application

import android.app.Application
import com.project.mobile_university.application.assembly.*
import com.project.mobile_university.domain.services.ScheduleSyncService

class AppDelegate : Application() {

    companion object {
        lateinit var presentationComponent: PresentationComponent
        lateinit var businessComponent: BusinessComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDependencies()
        startScheduleSyncService()
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

    private fun startScheduleSyncService() {
        val user = try {
            businessComponent.sharedPrefService().getUserInfo()
        } catch (e: Exception) {
            null
        }

        user?.let {
            ScheduleSyncService.startService(this@AppDelegate)
        }
    }
}