package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.lessonInfo.LessonInfoInputModule
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import com.project.mobile_university.presentation.schedule.host.ScheduleHostInputModule
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.ScheduleInputModule
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.teacher.TeacherScheduleModuleInput
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.settings.SettingsInputModule
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    @PerPresentationLayerScope
    fun provideScheduleHostInputModule(): ScheduleHostContract.InputModule {
        return ScheduleHostInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideScheduleInputModule(): ScheduleSubgroupContract.InputModule {
        return ScheduleInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideTeacherScheduleInputModule(): TeacherScheduleContract.InputModule {
        return TeacherScheduleModuleInput()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideSettingsInputModule(): SettingsContract.InputModule {
        return SettingsInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideLessonInfoInputModule(): LessonInfoContract.InputModule {
        return LessonInfoInputModule()
    }
}