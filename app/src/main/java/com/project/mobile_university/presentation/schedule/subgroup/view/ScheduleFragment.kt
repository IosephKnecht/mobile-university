package com.project.mobile_university.presentation.schedule.subgroup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.subgroup.assembly.ScheduleComponent
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleContract
import com.project.mobile_university.presentation.schedule.subgroup.view.adapter.ScheduleAdapter
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*

class ScheduleFragment : AbstractFragment<ScheduleContract.Presenter>() {

    companion object {
        const val TAG = "schedule_fragment"
        private val ARG_SUBGROUP_ID = "subgroup_id"

        @JvmStatic
        fun createInstance(groupId: Long) = ScheduleFragment()
            .apply {
                arguments = Bundle().apply {
                    putLong(ARG_SUBGROUP_ID, groupId)
                }
            }
    }

    private lateinit var diComponent: ScheduleComponent
    private lateinit var adapter: ScheduleAdapter
    private lateinit var calendar: HorizontalCalendar
    private var subgroupId: Long = -1

    override fun inject() {
        subgroupId = arguments!!.getLong(ARG_SUBGROUP_ID)

        diComponent = AppDelegate.presentationComponent.scheduleSubComponent()
            .with(this)
            .subgroup(subgroupId)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ScheduleAdapter()

        lesson_list.apply {
            this.adapter = this@ScheduleFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }

        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -3)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 3)

        schedule_swipe_layout.setOnRefreshListener {
            presenter.obtainLessonList(subgroupId)
        }

        calendar = HorizontalCalendar.Builder(activity, R.id.calendar_view)
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .build()

        calendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                presenter.currentDate.postValue(date.time)
                adapter.currentDate = CalendarUtil.convertToSimpleFormat(date.time)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.scheduleDayList.observe(this) {
            if (it != null) {
                adapter.scheduleDayList = it
                adapter.notifyDataSetChanged()
                schedule_swipe_layout.isRefreshing = false
            }
        }

        presenter.errorObserver.observe(this) {
            if (it != null) {
                Toasty.error(context!!, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}