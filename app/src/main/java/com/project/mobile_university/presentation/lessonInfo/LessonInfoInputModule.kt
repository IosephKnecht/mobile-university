package com.project.mobile_university.presentation.lessonInfo

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import com.project.mobile_university.presentation.lessonInfo.view.LessonInfoFragment

class LessonInfoInputModule : LessonInfoContract.InputModule {
    override fun createFragment(lessonExtId: Long): Fragment {
        return LessonInfoFragment.createInstance(lessonExtId)
    }

}