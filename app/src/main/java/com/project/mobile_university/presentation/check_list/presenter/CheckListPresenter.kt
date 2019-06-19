package com.project.mobile_university.presentation.check_list.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.data.presentation.CheckListStatus
import com.project.mobile_university.domain.mappers.CheckListStatusMapper
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.check_list.view.adapter.CheckListAdapter
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
    override val attendanceObserver = MutableLiveData<Pair<Int, Int>>()

    private val compositeDisposable = CompositeDisposable()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)

        if (checkList.value == null) {
            getCheckList()
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun getCheckList() {
        checkListLoadingState.value = true
        interactor.getCheckList(checkListId)
    }

    override fun syncCheckList(force: Boolean) {
        checkListSyncLoadingState.value = true

        if (force) {
            attendanceObserver.value = null

            compositeDisposable.add(
                Observable.just(checkList.value)
                    .subscribeOn(Schedulers.io())
                    .map { checkList -> checkList.mapToModel() }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ records ->
                        interactor.syncCheckList(checkListId, records)
                    }, { throwable ->
                        checkListSyncLoadingState.value = false
                        errorObserver.value = throwable
                    })
            )
        } else {
            compositeDisposable.add(
                Observable.just(checkList.value)
                    .subscribeOn(Schedulers.io())
                    .map { viewStates ->
                        var hasComeStudents = 0
                        viewStates.forEach { viewState ->
                            val status = viewState.changedStatus ?: viewState.record.status
                            if (status == CheckListStatus.HAS_COME) hasComeStudents++
                        }
                        Pair(viewStates.size, hasComeStudents)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ attendancePair ->
                        attendanceObserver.value = attendancePair
                    }, { throwable ->
                        checkListSyncLoadingState.value = false
                        throwable.printStackTrace()
                    })
            )
        }
    }

    override fun cancelSync() {
        attendanceObserver.value = null
        checkListSyncLoadingState.value = false
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
                if (checkList.value == null) {
                    emptyState.value = true
                }

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

        checkListSyncLoadingState.value = false
    }

    private fun List<CheckListRecord>.mapToViewState(): List<CheckListAdapter.RecordViewState> {
        return this.map {
            CheckListAdapter.RecordViewState(record = it)
        }
    }

    private fun List<CheckListAdapter.RecordViewState>.mapToModel(): List<CheckListRecord> {
        val needUpdatedRecords = mutableListOf<CheckListRecord>()

        this.forEach { viewState ->
            val oldState = viewState.record.status
            val newState = viewState.changedStatus

            needUpdatedRecords.add(
                CheckListRecord(
                    id = viewState.record.id,
                    studentId = viewState.record.studentId,
                    subgroupId = viewState.record.subgroupId,
                    checkListId = viewState.record.checkListId,
                    studentFirstName = viewState.record.studentFirstName,
                    studentLastName = viewState.record.studentLastName,
                    subgroupName = viewState.record.subgroupName,
                    status = newState ?: oldState
                )
            )
        }

        return needUpdatedRecords
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        interactor.onDestroy()
    }
}