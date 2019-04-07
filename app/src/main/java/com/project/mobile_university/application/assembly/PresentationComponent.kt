package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.lessonInfo.assembly.LessonInfoComponent
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import com.project.mobile_university.presentation.schedule.host.assembly.ScheduleHostComponent
import com.project.mobile_university.presentation.schedule.subgroup.assembly.ScheduleSubgroupComponent
import com.project.mobile_university.presentation.schedule.teacher.assembly.TeacherScheduleComponent
import com.project.mobile_university.presentation.settings.assembly.SettingsComponent
import dagger.Component

@Component(modules = [PresentationModule::class], dependencies = [BusinessComponent::class])
@PerPresentationLayerScope
interface PresentationComponent {
    fun scheduleHostSubComponent(): ScheduleHostComponent.Builder
    fun loginSubComponent(): LoginComponent.Builder
    fun subgroupScheduleSubComponent(): ScheduleSubgroupComponent.Builder
    fun teacherScheduleSubComponent(): TeacherScheduleComponent.Builder
    fun settingsSubComponent(): SettingsComponent.Builder
    fun lessonInfoSubComponent(): LessonInfoComponent.Builder
}