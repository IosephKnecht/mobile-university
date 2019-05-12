package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.lessonInfo.student.assembly.LessonInfoStudentComponent
import com.project.mobile_university.presentation.lessonInfo.teacher.assembly.LessonInfoTeacherComponent
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import com.project.mobile_university.presentation.check_list.assembly.CheckListComponent
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
    fun lessonInfoStudentSubComponent(): LessonInfoStudentComponent.Builder
    fun lessonInfoTeacherSubComponent(): LessonInfoTeacherComponent.Builder
    fun checkListSubComponent(): CheckListComponent.Builder
}