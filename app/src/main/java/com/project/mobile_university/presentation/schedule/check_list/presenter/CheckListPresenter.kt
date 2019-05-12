package com.project.mobile_university.presentation.schedule.check_list.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.schedule.check_list.contract.CheckListContract

class CheckListPresenter(
    private val checkListId: Long,
    private val interactor: CheckListContract.Interactor
) : AbstractPresenter(),
    CheckListContract.Presenter, CheckListContract.Listener {


    override val errorObserver = SingleLiveData<Throwable>()
    override val checkListLoadingState = MutableLiveData<Boolean>()
    override val checkListSyncLoadingState = MutableLiveData<Boolean>()
    override val emptyState = MutableLiveData<Boolean>()
    override val checkList = MutableLiveData<List<CheckListRecord>>()

    init {
        getCheckList()
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun getCheckList() {
        checkListLoadingState.value = true
        interactor.getCheckList(checkListId)
    }

    override fun syncCheckList(records: List<CheckListRecord>) {
        checkListSyncLoadingState.value = true
        interactor.syncCheckList(records)
    }

    override fun onObtainCheckList(records: List<CheckListRecord>?, throwable: Throwable?) {
        when {
            records != null -> {
                if (records.isEmpty()) {
                    emptyState.value = true
                } else {
                    checkList.value = records
                    emptyState.value = false
                }
            }
            throwable != null -> {
                errorObserver.value = throwable
            }
        }
        checkListLoadingState.value = false
    }

    override fun onSyncCheckList(throwable: Throwable?) {
        if (throwable == null) {
            getCheckList()
        } else {
            errorObserver.value = throwable
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}