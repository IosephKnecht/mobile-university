package com.project.mobile_university.presentation.schedule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.schedule.assembly.ScheduleComponent
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import com.project.mobile_university.presentation.schedule.view.adapter.ScheduleAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*

class ScheduleFragment : AbstractFragment<ScheduleContract.Presenter>() {

    companion object {
        const val TAG = "schedule_fragment"
        private val ARG_GROUP_ID = "group_id"

        @JvmStatic
        fun createInstance(groupId: Long) = ScheduleFragment()
            .apply {
                arguments = Bundle().apply {
                    putLong(ARG_GROUP_ID, groupId)
                }
            }
    }

    private lateinit var diComponent: ScheduleComponent
    private lateinit var adapter: ScheduleAdapter

    override fun inject() {
        val groupId = arguments!!.getLong(ARG_GROUP_ID, -1L)

        diComponent = AppDelegate.presentationComponent.scheduleSubComponent()
            .with(this)
            .groupId(groupId)
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
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.lessonList.observe(this) {
            if (it != null) {
                adapter.lessonList = it
                adapter.notifyDataSetChanged()
            }
        }
    }
}