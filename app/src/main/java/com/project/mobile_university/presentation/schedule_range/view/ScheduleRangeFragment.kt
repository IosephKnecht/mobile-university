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
import com.project.mobile_university.presentation.schedule_range.view.adapter.ScheduleRangeAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.mobile_university.presentation.common.helpers.pagination.PagingScrollListener
import com.project.mobile_university.presentation.common.ui.PlaceHolderView
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostListener
import com.project.mobile_university.presentation.visible
import es.dmoral.toasty.Toasty
import java.util.*

class ScheduleRangeFragment : AbstractFragment<ScheduleRangeContract.Presenter>() {

    companion object {
        const val TAG = "schedule_range_fragment"

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

    interface Host : ScheduleHostListener

    private lateinit var diComponent: ScheduleRangeComponent
    private lateinit var binding: FragmentScheduleRangeBinding
    private lateinit var adapter: ScheduleRangeAdapter

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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule_range,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ScheduleRangeAdapter()

        with(binding) {
            lessons.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(false)
                adapter = this@ScheduleRangeFragment.adapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                addOnScrollListener(
                    PagingScrollListener(
                        layoutManager = layoutManager as LinearLayoutManager,
                        loadMoreItems = { presenter.loadNewPage() }
                    )
                )
            }

            refreshLayout.setOnRefreshListener {
                presenter.refreshAllPage()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            pageProgress.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading == true) {
                    adapter.showLoadMoreProgress()
                }
            })

            refreshProgress.observe(viewLifecycleOwner, Observer { isLoading ->
                isLoading?.let {
                    binding.refreshLayout.isRefreshing = it
                }
            })

            emptyError.observe(viewLifecycleOwner, Observer { isShow ->
                when (isShow) {
                    true -> showPlaceHolder(
                        PlaceHolderView.State.Empty(
                            contentRes = R.string.schedule_range_empty_error_string,
                            iconRes = R.drawable.ic_placeholder_error
                        )
                    )
                    false -> hidePlaceHolder()
                }
            })

            emptyView.observe(viewLifecycleOwner, Observer { isShow ->
                when (isShow) {
                    true -> showPlaceHolder(
                        PlaceHolderView.State.Empty(
                            contentRes = R.string.schedule_range_empty_string,
                            iconRes = R.drawable.ic_placeholder_empty
                        )
                    )
                    false -> hidePlaceHolder()
                }
            })

            emptyProgress.observe(viewLifecycleOwner, Observer { isShow ->
                if (isShow != null) {
                    with(binding) {
                        content.visible(!isShow)
                        progressBar.visible(isShow)
                        refreshLayout.isEnabled = !isShow
                    }
                }
            })

            errorMessage.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    Toasty.error(context!!, throwable.localizedMessage).show()
                }
            })

            showData.observe(viewLifecycleOwner, Observer { viewStates ->
                if (viewStates != null) {
                    adapter.reload(viewStates)
                    binding.refreshLayout.isEnabled = true
                }
            })

            showLessonInfo.observe(viewLifecycleOwner, Observer { lessonId ->
                lessonId?.let {
                    (parentFragment as? Host)?.showLessonInfo(it)
                }
            })

            editLessonInfo.observe(viewLifecycleOwner, Observer { lessonId ->
                lessonId?.let {
                    (parentFragment as? Host)?.editLessonInfo(it)
                }
            })
        }
    }

    private fun showPlaceHolder(state: PlaceHolderView.State) {
        with(binding) {
            lessons.visible(false)
            placeHolder.visible(true)
            placeHolder.setState(state)
        }
    }

    private fun hidePlaceHolder() {
        with(binding) {
            lessons.visible(true)
            placeHolder.visible(false)
        }
    }
}