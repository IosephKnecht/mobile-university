package com.project.mobile_university.presentation.schedule.subgroup.assembly

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ScheduleModule::class])
@PerFeatureLayerScope
interface ScheduleSubgroupComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun subgroup(subgroupId: Long): Builder

        fun build(): ScheduleSubgroupComponent
    }

    fun getPresenter(): ScheduleSubgroupContract.Presenter
}