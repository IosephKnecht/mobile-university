package com.project.mobile_university.presentation.schedule.subgroup

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleContract
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleFragment

class ScheduleInputModule : ScheduleContract.ScheduleInputModuleContract {
    override fun createFragment(subgroupId: Long): Fragment {
        return ScheduleFragment.createInstance(subgroupId)
    }
}