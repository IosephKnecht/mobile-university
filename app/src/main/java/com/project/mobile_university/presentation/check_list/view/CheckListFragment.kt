package com.project.mobile_university.presentation.check_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.check_list.assembly.CheckListComponent
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.check_list.view.adapter.CheckListAdapter
import com.project.mobile_university.presentation.visible
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_check_list.*

class CheckListFragment : AbstractFragment<CheckListContract.Presenter>() {

    companion object {
        const val TAG = "check_list_fragment"
        private const val CHECK_LIST_IDENTIFIER = "check_list_identifier"

        fun createInstance(checkListId: Long) = CheckListFragment().apply {
            arguments = Bundle().apply {
                putLong(CHECK_LIST_IDENTIFIER, checkListId)
            }
        }
    }

    private lateinit var diComponent: CheckListComponent
    private lateinit var adapter: CheckListAdapter

    private var syncWarningDialog: MaterialDialog? = null

    override fun inject() {
        val checkListId = arguments?.getLong(CHECK_LIST_IDENTIFIER)
            ?: throw RuntimeException("check list identifier could not be null")

        diComponent = AppDelegate.presentationComponent
            .checkListSubComponent()
            .checkListId(checkListId)
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CheckListAdapter { viewState, status ->
            presenter.onChangeStatus(viewState, status)
        }

        check_list_swipe_refresh?.setOnRefreshListener {
            presenter.getCheckList()
        }

        student_list?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            this.adapter = this@CheckListFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        sync_button?.setOnClickListener {
            presenter.syncCheckList(false)
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            errorObserver.observe(viewLifecycleOwner, Observer { throwable ->
                Toasty.error(context!!, throwable.localizedMessage).show()
            })

            checkListLoadingState.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    check_list_swipe_refresh.isRefreshing = isLoading
                }
            })

            checkListSyncLoadingState.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    sync_button.visible(!isLoading)
                    sync_progress_bar.visible(isLoading)
                }
            })

            emptyState.observe(viewLifecycleOwner, Observer { isEmpty ->
                if (isEmpty != null) {
                    place_holder.visible(isEmpty)
                    check_list_content.visible(!isEmpty)
                }
            })

            checkList.observe(viewLifecycleOwner, Observer { checkList ->
                if (checkList != null) {
                    adapter.reload(checkList)
                }
            })

            attendanceObserver.observe(viewLifecycleOwner, Observer { pair ->
                if (pair == null) {
                    dismissSyncWarningDialog()
                } else {
                    val (totalCount, hasComeCount) = pair
                    showSyncWarningDialog(totalCount, hasComeCount)
                }
            })
        }
    }

    private fun showSyncWarningDialog(totalCount: Int, hasComeCount: Int) {
        if (syncWarningDialog == null) {
            syncWarningDialog = context?.run {
                MaterialDialog.Builder(this)
                    .content(
                        getString(
                            R.string.sync_warning_content_string,
                            totalCount.toString(),
                            hasComeCount.toString()
                        )
                    )
                    .cancelable(false)
                    .negativeText(R.string.sync_negative_text)
                    .positiveText(R.string.sync_positive_text)
                    .onPositive { _, _ -> presenter.syncCheckList(true) }
                    .onNegative { _, _ -> presenter.cancelSync() }
                    .build()
            }
        }

        syncWarningDialog?.show()
    }

    private fun dismissSyncWarningDialog() {
        syncWarningDialog?.dismiss()
        syncWarningDialog = null
    }
}