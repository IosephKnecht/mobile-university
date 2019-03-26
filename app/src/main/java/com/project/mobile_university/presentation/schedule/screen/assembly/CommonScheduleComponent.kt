package com.project.mobile_university.presentation.schedule.screen.assembly

import androidx.fragment.app.FragmentActivity
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CommonScheduleModule::class])
@PerFeatureLayerScope
interface CommonScheduleComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(activity: FragmentActivity): Builder

        @BindsInstance
        fun screenState(state: CommonScheduleContract.ScreenState): Builder

        @BindsInstance
        fun identifier(id: Long): Builder

        fun build(): CommonScheduleComponent
    }

    fun getPresenter(): CommonScheduleContract.Presenter
}