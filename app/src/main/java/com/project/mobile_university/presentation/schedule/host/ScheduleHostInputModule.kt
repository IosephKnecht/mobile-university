package com.project.mobile_university.presentation.schedule.host

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostFragment

class ScheduleHostInputModule : ScheduleHostContract.InputModule {
    override fun createFragment(identifier: Long, screenType: ScheduleHostContract.ScreenType): Fragment {
        return ScheduleHostFragment.createInstance(identifier, screenType)
    }
}