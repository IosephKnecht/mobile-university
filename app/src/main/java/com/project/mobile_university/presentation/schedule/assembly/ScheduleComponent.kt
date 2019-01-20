package com.project.mobile_university.presentation.schedule.assembly

import com.project.mobile_university.presentation.PerFeatureLayerScope
import dagger.Subcomponent

@Subcomponent(modules = [ScheduleModule::class])
@PerFeatureLayerScope
interface ScheduleComponent {
}