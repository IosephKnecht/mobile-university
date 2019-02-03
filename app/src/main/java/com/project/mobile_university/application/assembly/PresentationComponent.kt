package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import com.project.mobile_university.presentation.schedule.subgroup.assembly.ScheduleComponent
import com.project.mobile_university.presentation.schedule.teacher.assembly.TeacherScheduleComponent
import dagger.Component

@Component(modules = [PresentationModule::class], dependencies = [BusinessComponent::class])
@PerPresentationLayerScope
interface PresentationComponent {
    fun loginSubComponent(): LoginComponent.Builder
    fun scheduleSubComponent(): ScheduleComponent.Builder
    fun teacherScheduleSubComponent(): TeacherScheduleComponent.Builder
}