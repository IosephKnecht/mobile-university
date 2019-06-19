package com.project.mobile_university.presentation.schedule.host.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class ScheduleHostInteractor(
    private val sharedPreferenceService: SharedPreferenceService,
    private val loginRepository: LoginRepository
) :
    AbstractInteractor<ScheduleHostContract.Listener>(),
    ScheduleHostContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun obtainUserProfile() {
        val source = Single.fromCallable {
            sharedPreferenceService.getUserInfo()
        }

        compositeDisposable.add(
            discardResult(source) { listener, result ->
                listener?.onObtainUserProfile(result.data, result.throwable)
            }
        )
    }

    override fun logout() {
        compositeDisposable.add(
            discardResult(loginRepository.logout()) { listener, throwable ->
                listener?.onLogout(throwable)
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}