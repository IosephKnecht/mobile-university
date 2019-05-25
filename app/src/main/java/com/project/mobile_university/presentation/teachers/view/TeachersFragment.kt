package com.project.mobile_university.presentation.teachers.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.teachers.assembly.TeachersComponent
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import kotlinx.android.synthetic.main.fragment_teachers.*

class TeachersFragment : AbstractFragment<TeachersContract.Presenter>() {

    companion object {
        fun createInstance() = TeachersFragment()
    }

    private lateinit var diComponent: TeachersComponent

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

        refresh_layout?.setOnRefreshListener {
            presenter.refreshAllPage()
        }

        teachers?.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            //TODO: set adapter -> adapter = null
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            addOnScrollListener(PagingScrollListener(linearLayoutManager) {
                presenter.loadNewPage()
            })
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            pageProgress.observe(viewLifecycleOwner, Observer { isLoading ->
                // TODO: show page progress
            })

            refreshProgress.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    refresh_layout?.isRefreshing = isLoading
                }
            })

            emptyError.observe(viewLifecycleOwner, Observer { isShow ->
                if (isShow != null) {
                    // TODO: show error placeholder
                }
            })

            emptyView.observe(viewLifecycleOwner, Observer { isShow ->
                if (isShow != null) {
                    // TODO: show empty placeholder
                }
            })

            emptyProgress.observe(viewLifecycleOwner, Observer { isShow ->
                if (isShow != null) {
                    // TODO: show progress bar
                }
            })

            errorMessage.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    // TODO: show toast
                }
            })

            showData.observe(viewLifecycleOwner, Observer { teacherList ->
                if (teacherList != null) {
                    // TODO: added teachers to RecyclerView
                }
            })
        }
    }
}