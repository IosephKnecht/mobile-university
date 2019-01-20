package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.schedule.ScheduleInputModule
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {
    @Provides
    @PerPresentationLayerScope
    fun provideScheduleInputModule(): ScheduleContract.ScheduleInputModuleContract {
        return ScheduleInputModule()
    }
}