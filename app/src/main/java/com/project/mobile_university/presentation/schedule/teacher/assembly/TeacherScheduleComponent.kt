package com.project.mobile_university.presentation.schedule.teacher.assembly

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [TeacherScheduleModule::class])
@PerFeatureLayerScope
interface TeacherScheduleComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun teacherId(teacherId: Long): Builder

        fun build(): TeacherScheduleComponent
    }

    fun getPresenter(): TeacherScheduleContract.Presenter
}