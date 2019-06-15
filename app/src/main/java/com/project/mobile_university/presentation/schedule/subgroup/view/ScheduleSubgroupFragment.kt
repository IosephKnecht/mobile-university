package com.project.mobile_university.presentation.schedule.subgroup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.common.ui.PlaceHolderView
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostListener
import com.project.mobile_university.presentation.schedule.subgroup.assembly.ScheduleSubgroupComponent
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.adapter.ScheduleSubgroupAdapter
import com.project.mobile_university.presentation.visible
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_subgroup_schedule.*

class ScheduleSubgroupFragment : AbstractFragment<ScheduleSubgroupContract.Presenter>() {

    interface Host : ScheduleHostListener

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

        adapter = ScheduleSubgroupAdapter { lessonExtId ->
            (parentFragment as Host).showLessonInfo(lessonExtId)
        }

        lesson_list.apply {
            this.adapter = this@ScheduleSubgroupFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                this.setDrawable(ContextCompat.getDrawable(context, R.drawable.ic_divider)!!)
            })
        }

        schedule_swipe_layout.setOnRefreshListener {
            presenter.obtainLessonList(subgroupId)
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            errorObserver.observe(viewLifecycleOwner, Observer { errorMessage ->
                if (errorMessage != null) {
                    Toasty.error(context!!, errorMessage, Toast.LENGTH_LONG).show()
                }
            })

            lessonsObserver.observe(viewLifecycleOwner, Observer { lessons ->
                if (lessons != null) {
                    adapter.reload(lessons)
                }
            })

            emptyState.observe(viewLifecycleOwner, Observer { isEmpty ->
                if (isEmpty != null) {
                    if (isEmpty) {
                        showPlaceHolder(
                            PlaceHolderView.State.Empty(
                                contentRes = R.string.schedule_subgroup_empty_string,
                                iconRes = R.drawable.ic_placeholder_empty
                            )
                        )
                    } else {
                        hidePlaceHolder()
                    }
                }
            })

            loadingState.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    schedule_swipe_layout.isRefreshing = isLoading
                }
            })
        }
    }

    private fun showPlaceHolder(state: PlaceHolderView.State) {
        lesson_list.visible(false)
        place_holder.visible(true)

        place_holder.setState(state)
    }

    private fun hidePlaceHolder() {
        lesson_list.visible(true)
        place_holder.visible(false)
    }
}