package com.project.mobile_university.presentation.schedule.check_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.schedule.check_list.assembly.CheckListComponent
import com.project.mobile_university.presentation.schedule.check_list.contract.CheckListContract

class CheckListFragment : AbstractFragment<CheckListContract.Presenter>() {

    companion object {
        private const val CHECK_LIST_IDENTIFIER = "check_list_identifier"

        fun createInstance(checkListId: Long) = CheckListFragment().apply {
            arguments = Bundle().apply {
                putLong(CHECK_LIST_IDENTIFIER, checkListId)
            }
        }
    }

    private lateinit var diComponent: CheckListComponent

    override fun inject() {
        val checkListId = arguments?.getLong(CHECK_LIST_IDENTIFIER)
            ?: throw RuntimeException("check list identifier could not be null")

        diComponent = AppDelegate.presentationComponent
            .checkListSubComponent()
            .checkListId(checkListId)
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_list, container, false)
    }
}