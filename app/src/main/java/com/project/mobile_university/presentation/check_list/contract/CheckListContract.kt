package com.project.mobile_university.presentation.check_list.contract

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.project.iosephknecht.viper.interacor.MvpInteractor
import com.project.iosephknecht.viper.presenter.MvpPresenter
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.presentation.check_list.view.adapter.CheckListAdapter

interface CheckListContract {
    interface ObservableStorage {
        val errorObserver: LiveData<Throwable>
        val checkListLoadingState: LiveData<Boolean>
        val checkListSyncLoadingState: LiveData<Boolean>
        val emptyState: LiveData<Boolean>
        val checkList: LiveData<List<CheckListAdapter.RecordViewState>>
        val attendanceObserver: LiveData<Pair<Int, Int>>
    }

    interface Presenter : MvpPresenter, ObservableStorage {
        fun getCheckList()
        fun syncCheckList(force: Boolean)
        fun cancelSync()
        fun onChangeStatus(viewState: CheckListAdapter.RecordViewState, status: Int)
    }

    interface Listener : MvpInteractor.Listener {
        fun onObtainCheckList(records: List<CheckListRecord>?, throwable: Throwable?)
        fun onSyncCheckList(throwable: Throwable?)
    }

    interface Interactor : MvpInteractor<Listener> {
        fun getCheckList(checkListExtId: Long)
        fun syncCheckList(checkListExtId: Long, records: List<CheckListRecord>)
    }

    interface InputModule {
        fun createFragment(checkListExtId: Long): Fragment
    }
}