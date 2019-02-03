package com.project.mobile_university.presentation.schedule.subgroup

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment

class ScheduleInputModule : ScheduleSubgroupContract.InputModule {
    override fun createFragment(subgroupId: Long): Fragment {
        return ScheduleSubgroupFragment.createInstance(subgroupId)
    }
}