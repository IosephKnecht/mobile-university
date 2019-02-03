package com.project.mobile_university.presentation.schedule.teacher

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.view.TeacherScheduleFragment

class TeacherScheduleModuleInput : TeacherScheduleContract.InputModule {
    override fun createFragment(teacherId: Long): Fragment {
        return TeacherScheduleFragment.createInstance(teacherId)
    }
}