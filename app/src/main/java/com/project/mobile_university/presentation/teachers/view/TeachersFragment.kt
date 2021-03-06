package com.project.mobile_university.presentation.teachers.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.common.ui.PlaceHolderView
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostListener
import com.project.mobile_university.presentation.teachers.assembly.TeachersComponent
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.common.helpers.pagination.PagingScrollListener
import com.project.mobile_university.presentation.teachers.view.adapter.TeachersAdapter
import com.project.mobile_university.presentation.teachers.view.adapter.TeachersSwipeHelper
import com.project.mobile_university.presentation.visible
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_teachers.*

class TeachersFragment : AbstractFragment<TeachersContract.Presenter>() {

    companion object {
        const val TAG = "teachers_screen"

        fun createInstance() = TeachersFragment()
    }

    interface Host : ScheduleHostListener

    private lateinit var diComponent: TeachersComponent
    private lateinit var adapter: TeachersAdapter

    override fun inject() {
        diComponent = AppDelegate.presentationComponent
            .teachersSubComponent()
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teachers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeachersAdapter()

        TeachersSwipeHelper(
            context = view.context,
            swipeDirection = ItemTouchHelper.LEFT,
            recyclerView = teachers,
            itemWidth = 100
        ) { position, swipeAction ->
            presenter.handleSwipeAction(position, swipeAction)
        }

        refresh_layout?.setOnRefreshListener {
            presenter.refreshAllPage()
        }

        teachers?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = this@TeachersFragment.adapter
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(
                PagingScrollListener(
                    layoutManager as LinearLayoutManager
                ) {
                    presenter.loadNewPage()
                })
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
                if (isLoading != null) {
                    refresh_layout?.isRefreshing = isLoading
                }
            })

            emptyError.observe(viewLifecycleOwner, Observer { isShow ->
                when (isShow) {
                    true -> showPlaceHolder(
                        PlaceHolderView.State.Empty(
                            contentRes = R.string.teachers_empty_error_string,
                            iconRes = R.drawable.ic_placeholder_empty
                        )
                    )
                    false -> hidePlaceHolder()
                }
            })

            emptyView.observe(viewLifecycleOwner, Observer { isShow ->
                when (isShow) {
                    true -> showPlaceHolder(
                        PlaceHolderView.State.Empty(
                            contentRes = R.string.teachers_empty_string,
                            iconRes = R.drawable.ic_placeholder_empty
                        )
                    )
                    false -> hidePlaceHolder()
                }
            })

            emptyProgress.observe(viewLifecycleOwner, Observer { isShow ->
                if (isShow != null) {
                    content?.visible(!isShow)
                    progress_bar?.visible(isShow)
                    refresh_layout?.isEnabled = !isShow
                }
            })

            errorMessage.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    Toasty.error(context!!, throwable.localizedMessage).show()
                }
            })

            showData.observe(viewLifecycleOwner, Observer { teacherList ->
                if (teacherList != null) {
                    adapter.reload(teacherList)
                    refresh_layout?.isEnabled = true
                }
            })

            showScheduleRange.observe(viewLifecycleOwner, Observer { triple ->
                triple?.let {
                    val (teacherId, startDate, endDate) = it
                    (parentFragment as? Host)?.showScheduleRange(teacherId, startDate, endDate)
                }
            })

            showProfile.observe(viewLifecycleOwner, Observer { bundle ->
                bundle?.let { (userId, isMe) -> (parentFragment as? Host)?.showUserInfo(userId, isMe) }
            })
        }
    }

    private fun showPlaceHolder(state: PlaceHolderView.State) {
        teachers?.visible(false)
        place_holder?.visible(true)
        place_holder?.setState(state)
    }

    private fun hidePlaceHolder() {
        teachers?.visible(true)
        place_holder?.visible(false)
    }
}