package com.project.mobile_university.presentation.schedule.subgroup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.subgroup.assembly.ScheduleSubgroupComponent
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.adapter.ScheduleSubgroupAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_subgroup_schedule.*

class ScheduleSubgroupFragment : AbstractFragment<ScheduleSubgroupContract.Presenter>() {

    companion object {
        const val TAG = "schedule_fragment"
        private val ARG_SUBGROUP_ID = "subgroup_id"

        @JvmStatic
        fun createInstance(groupId: Long) = ScheduleSubgroupFragment()
            .apply {
                arguments = Bundle().apply {
                    putLong(ARG_SUBGROUP_ID, groupId)
                }
            }
    }

    private lateinit var diComponent: ScheduleSubgroupComponent
    private lateinit var adapter: ScheduleSubgroupAdapter
    private var subgroupId: Long = -1

    override fun inject() {
        subgroupId = arguments!!.getLong(ARG_SUBGROUP_ID)

        diComponent = AppDelegate.presentationComponent.subgroupScheduleSubComponent()
            .with(this)
            .subgroup(subgroupId)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subgroup_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ScheduleSubgroupAdapter()

        lesson_list.apply {
            this.adapter = this@ScheduleSubgroupFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }

        schedule_swipe_layout.setOnRefreshListener {
            presenter.obtainLessonList(subgroupId)
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.scheduleDayList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                adapter.scheduleDayList = it
                adapter.notifyDataSetChanged()
                schedule_swipe_layout.isRefreshing = false
            }
        })

        presenter.errorObserver.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toasty.error(context!!, it, Toast.LENGTH_LONG).show()
            }
        })


        presenter.dateObserver.observe(viewLifecycleOwner, Observer {
            // TODO: util does not inject in view element
            adapter.currentDate = CalendarUtil.convertToSimpleFormat(it!!)
            adapter.notifyDataSetChanged()
        })
    }
}