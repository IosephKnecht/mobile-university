package com.project.mobile_university.presentation.schedule_range.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.databinding.FragmentScheduleRangeBinding
import com.project.mobile_university.presentation.schedule_range.assembly.ScheduleRangeComponent
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract

class ScheduleRangeFragment : AbstractFragment<ScheduleRangeContract.Presenter>() {

    companion object {
        fun createInstance() = ScheduleRangeFragment()
    }

    private lateinit var diComponent: ScheduleRangeComponent
    private lateinit var binding: FragmentScheduleRangeBinding

    override fun inject() {
        diComponent = AppDelegate.presentationComponent
            .scheduleRangeSubComponent()
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentScheduleRangeBinding>(
            inflater,
            R.layout.fragment_schedule_range,
            container,
            false
        )

        return binding.root
    }
}