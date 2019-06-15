package com.project.mobile_university.presentation.schedule_range

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.schedule_range.view.ScheduleRangeFragment

class ScheduleRangeInputModule : ScheduleRangeContract.InputModule {
    override fun createFragment(): Fragment {
        return ScheduleRangeFragment.createInstance()
    }
}