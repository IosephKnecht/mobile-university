package com.project.mobile_university.presentation.teachers.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.teachers.assembly.TeachersComponent
import com.project.mobile_university.presentation.teachers.contract.TeachersContract

class TeachersFragment : AbstractFragment<TeachersContract.Presenter>() {

    private lateinit var diComponent: TeachersComponent

    override fun inject() {
        diComponent = AppDelegate.presentationComponent
            .teachersSubComponent()
            .androidComponent(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teachers, container, false)
    }
}