package com.project.mobile_university.presentation.check_list

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.check_list.view.CheckListFragment

class CheckListInputModule : CheckListContract.InputModule {
    override fun createFragment(checkListExtId: Long): Fragment {
        return CheckListFragment.createInstance(checkListExtId)
    }
}