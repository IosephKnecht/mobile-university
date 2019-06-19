package com.project.mobile_university.presentation.schedule.host.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.NetworkService
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ScheduleHostInteractor(
    private val sharedPreferenceService: SharedPreferenceService,
    private val loginRepository: LoginRepository,
    private val networkService: NetworkService
) :
    AbstractInteractor<ScheduleHostContract.Listener>(),
    ScheduleHostContract.Interactor {

    private val compositeDisposable = CompositeDisposable()
    private var networkDisposable: Disposable? = null

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

    override fun setListener(listener: ScheduleHostContract.Listener?) {
        super.setListener(listener)

        subscribeOnNetworkChannel(listener)
    }

    private fun subscribeOnNetworkChannel(listener: ScheduleHostContract.Listener?) {
        if (listener == null) {
            networkDisposable?.dispose()
        } else {
            if (networkDisposable?.isDisposed == false) networkDisposable?.dispose()

            networkDisposable = networkService.getNetworkObservable()
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isConnected ->
                    getListener()?.onNetworkStateChanged(isConnected)
                }, { throwable ->
                    throwable.printStackTrace()
                })
        }
    }

    override fun onDestroy() {
        networkDisposable?.dispose()
        compositeDisposable.clear()
        super.onDestroy()
    }
}