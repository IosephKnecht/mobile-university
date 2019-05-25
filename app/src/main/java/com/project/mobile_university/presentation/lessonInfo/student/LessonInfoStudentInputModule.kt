package com.project.mobile_university.presentation.lessonInfo.student

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import com.project.mobile_university.presentation.lessonInfo.student.view.LessonInfoStudentFragment

class LessonInfoStudentInputModule : LessonInfoStudentContract.InputModule {
    override fun createFragment(lessonExtId: Long): Fragment {
        return LessonInfoStudentFragment.createInstance(lessonExtId)
    }

}