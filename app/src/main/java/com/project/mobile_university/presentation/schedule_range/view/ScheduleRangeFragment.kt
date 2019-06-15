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
import java.util.*

class ScheduleRangeFragment : AbstractFragment<ScheduleRangeContract.Presenter>() {

    companion object {
        private const val START_DATE_KEY = "start_date_key"
        private const val END_DATE_KEY = "end_date_key"
        private const val TEACHER_ID_KEY = "teacher_id_key"

        fun createInstance(
            startDate: Date,
            endDate: Date, teacherId: Long
        ) = ScheduleRangeFragment().apply {
            arguments = Bundle().apply {
                putSerializable(START_DATE_KEY, startDate)
                putSerializable(END_DATE_KEY, endDate)
                putLong(TEACHER_ID_KEY, teacherId)
            }
        }
    }

    private lateinit var diComponent: ScheduleRangeComponent
    private lateinit var binding: FragmentScheduleRangeBinding

    override fun inject() {
        val startDate = arguments?.getSerializable(START_DATE_KEY) as? Date
            ?: throw RuntimeException("start date could not be null")

        val endDate = arguments?.getSerializable(END_DATE_KEY) as? Date
            ?: throw RuntimeException("end date could not be null")

        val teacherId = arguments?.getLong(TEACHER_ID_KEY)
            ?: throw RuntimeException("teacher id could not be null")

        diComponent = AppDelegate.presentationComponent
            .scheduleRangeSubComponent()
            .with(this)
            .startDate(startDate)
            .endDate(endDate)
            .teacherId(teacherId)
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