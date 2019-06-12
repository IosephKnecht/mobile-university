package com.project.mobile_university.presentation.user_info.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import io.reactivex.disposables.CompositeDisposable

class UserInfoInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<UserInfoContract.Listener>(),
    UserInfoContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getUserInfo(userId: Long) {
        compositeDisposable.add(discardResult(scheduleRepository.getUserInfo(userId)) { listener, result ->
            listener?.onObtainUserInfo(result.data, result.throwable)
        })
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}