package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.check_list.CheckListInputModule
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.lessonInfo.student.LessonInfoStudentInputModule
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import com.project.mobile_university.presentation.lessonInfo.teacher.LessonInfoTeacherInputModule
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import com.project.mobile_university.presentation.schedule.host.ScheduleHostInputModule
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.ScheduleInputModule
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.teacher.TeacherScheduleModuleInput
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule_range.ScheduleRangeInputModule
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.settings.SettingsInputModule
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.teachers.TeachersInputModule
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.user_info.UserInfoInputModule
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
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
    fun provideLessonInfoInputModule(): LessonInfoStudentContract.InputModule {
        return LessonInfoStudentInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideLessonInfoTeacherInputModule(): LessonInfoTeacherContract.InputModule {
        return LessonInfoTeacherInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideCheckListFragment(): CheckListContract.InputModule {
        return CheckListInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideTeachersInputModule(): TeachersContract.InputModule {
        return TeachersInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideUserInfoModule(): UserInfoContract.InputModule {
        return UserInfoInputModule()
    }

    @Provides
    @PerPresentationLayerScope
    fun provideScheduleRangeInputModule(): ScheduleRangeContract.InputModule {
        return ScheduleRangeInputModule()
    }
}