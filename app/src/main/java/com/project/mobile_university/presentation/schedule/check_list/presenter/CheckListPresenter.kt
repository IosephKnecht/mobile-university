package com.project.mobile_university.presentation.schedule.check_list.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.domain.mappers.CheckListStatusMapper
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.schedule.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.schedule.check_list.view.adapter.CheckListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CheckListPresenter(
    private val checkListId: Long,
    private val interactor: CheckListContract.Interactor
) : AbstractPresenter(),
    CheckListContract.Presenter, CheckListContract.Listener {


    override val errorObserver = SingleLiveData<Throwable>()
    override val checkListLoadingState = MutableLiveData<Boolean>()
    override val checkListSyncLoadingState = MutableLiveData<Boolean>()
    override val emptyState = MutableLiveData<Boolean>()
    override val checkList = MutableLiveData<List<CheckListAdapter.RecordViewState>>()

    private val compositeDisposable = CompositeDisposable()

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

    override fun syncCheckList() {
        checkListSyncLoadingState.value = true

        compositeDisposable.add(Observable.just(checkList.value)
            .subscribeOn(Schedulers.io())
            .map { checkList -> checkList.mapToModel() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ records ->
                interactor.syncCheckList(records)
            }, { throwable ->
                errorObserver.value = throwable
            })
        )
    }

    override fun onChangeStatus(viewState: CheckListAdapter.RecordViewState, status: Int) {
        viewState.changedStatus = CheckListStatusMapper.toPresentation(status)
    }

    override fun onObtainCheckList(records: List<CheckListRecord>?, throwable: Throwable?) {
        when {
            records != null -> {
                if (records.isEmpty()) {
                    emptyState.value = true
                } else {
                    checkList.value = records.mapToViewState()
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

    private fun List<CheckListRecord>.mapToViewState(): List<CheckListAdapter.RecordViewState> {
        return this.map {
            CheckListAdapter.RecordViewState(record = it)
        }
    }

    private fun List<CheckListAdapter.RecordViewState>.mapToModel(): List<CheckListRecord> {
        val needUpdatedRecords = mutableListOf<CheckListRecord>()

        this.forEach { viewState ->
            viewState.changedStatus?.let { changedStatus ->
                needUpdatedRecords.add(
                    CheckListRecord(
                        id = viewState.record.id,
                        status = changedStatus,
                        student = viewState.record.student
                    )
                )
            }
        }

        return needUpdatedRecords
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        interactor.onDestroy()
    }
}