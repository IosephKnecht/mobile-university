package com.project.mobile_university.presentation.schedule_range

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.schedule_range.view.ScheduleRangeFragment
import java.util.*

class ScheduleRangeInputModule : ScheduleRangeContract.InputModule {
    override fun createFragment(startDate: Date, endDate: Date, teacherId: Long): Fragment {
        return ScheduleRangeFragment.createInstance(
            startDate = startDate,
            endDate = endDate,
            teacherId = teacherId
        )
    }
}