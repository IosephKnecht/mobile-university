package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import com.project.mobile_university.presentation.schedule.subgroup.assembly.ScheduleSubgroupComponent
import com.project.mobile_university.presentation.schedule.teacher.assembly.TeacherScheduleComponent
import com.project.mobile_university.presentation.teachers.assembly.TeachersComponent
import dagger.Component

@Component(modules = [PresentationModule::class], dependencies = [BusinessComponent::class])
@PerPresentationLayerScope
interface PresentationComponent {
    fun loginSubComponent(): LoginComponent.Builder
    fun subgroupScheduleSubComponent(): ScheduleSubgroupComponent.Builder
    fun teacherScheduleSubComponent(): TeacherScheduleComponent.Builder
    fun teachersSubComponent(): TeachersComponent.Builder
}