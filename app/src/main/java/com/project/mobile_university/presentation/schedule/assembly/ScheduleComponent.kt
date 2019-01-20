package com.project.mobile_university.presentation.schedule.assembly

import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import dagger.Binds
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ScheduleModule::class])
@PerFeatureLayerScope
interface ScheduleComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(androidComponent: AndroidComponent): Builder

        @BindsInstance
        fun groupId(groupId: Long): Builder

        fun build(): ScheduleComponent
    }

    fun getPresenter(): ScheduleContract.Presenter
}