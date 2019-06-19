package com.project.mobile_university.presentation.settings.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.UserInfo
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.contract.SettingsContract.State

class SettingsPresenter(
    private val interactor: SettingsContract.Interactor
) : AbstractPresenter(),
    SettingsContract.Presenter,
    SettingsContract.Listener,
    SettingsContract.ObservableStorage {

    override val userInfo = MutableLiveData<UserInfo>()
    override val throwableObserver = SingleLiveData<String>()
    override val successClear = SingleLiveData<Boolean>()

    private var state = State.IDLE

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)

        when (state) {
            State.IDLE -> obtainUserInfo()
            else -> {

            }
        }
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun obtainUserInfo() {
        interactor.getUserInfo()
    }

    override fun clearCache() {
        interactor.clearAllTable()
    }

    override fun onObtainUserInfo(userInfo: UserInfo?, throwable: Throwable?) {
        when {
            throwable != null -> {
                throwableObserver.value = throwable.localizedMessage
            }
            userInfo != null -> {
                this.userInfo.value = userInfo
            }
        }
    }

    override fun onClearCache(throwable: Throwable?) {
        when {
            throwable != null -> {
                this.throwableObserver.value = throwable.localizedMessage
            }
            else -> {
                successClear.value = true
            }
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}