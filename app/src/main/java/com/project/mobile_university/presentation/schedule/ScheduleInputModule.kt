package com.project.mobile_university.presentation.schedule

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import com.project.mobile_university.presentation.schedule.view.ScheduleFragment

class ScheduleInputModule : ScheduleContract.ScheduleInputModuleContract {
    override fun createFragment(groupId: Long): Fragment {
        return ScheduleFragment.createInstance(groupId)
    }
}