package com.project.mobile_university.presentation.check_list.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import io.reactivex.disposables.CompositeDisposable

class CheckListInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<CheckListContract.Listener>(),
    CheckListContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getCheckList(checkListExtId: Long) {
        val observable = scheduleRepository.getCheckList(checkListExtId)

        compositeDisposable.add(discardResult(observable) { listener, result ->
            listener!!.onObtainCheckList(result.data, result.throwable)
        })
    }

    override fun syncCheckList(checkListExtId: Long, records: List<CheckListRecord>) {
        val observable = scheduleRepository.putCheckList(checkListExtId, records)

        compositeDisposable.add(discardResult(observable) { listener, result ->
            listener!!.onSyncCheckList(result.throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}