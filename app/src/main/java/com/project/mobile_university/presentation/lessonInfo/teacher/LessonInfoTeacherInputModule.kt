package com.project.mobile_university.presentation.lessonInfo.teacher

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import com.project.mobile_university.presentation.lessonInfo.teacher.view.LessonInfoTeacherFragment

class LessonInfoTeacherInputModule : LessonInfoTeacherContract.InputModule {
    override fun createFragment(lessonExtId: Long): Fragment {
        return LessonInfoTeacherFragment.createInstance(lessonExtId)
    }
}