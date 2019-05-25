package com.project.mobile_university.presentation.teachers

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.teachers.view.TeachersFragment

class TeachersInputModule : TeachersContract.InputModule {
    override fun createFragment(): Fragment {
        return TeachersFragment.createInstance()
    }
}