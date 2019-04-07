package com.project.mobile_university.presentation.settings.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.UserInfo
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.contract.SettingsContract.State

class SettingsPresenter(
    private val interactor: SettingsContract.Interactor,
    private val router: SettingsContract.Router
) : AbstractPresenter(), SettingsContract.Presenter, SettingsContract.Listener,
    SettingsContract.ObservableStorage, SettingsContract.RouterListener {

    override val userInfo = MutableLiveData<UserInfo>()
    override val throwableObserver = SingleLiveData<String>()
    override val successLogout = SingleLiveData<Boolean>()

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
        throwableObserver.setValue("The functionality is in development.")
    }

    override fun exit() {
        interactor.logout()
    }

    override fun onObtainUserInfo(userInfo: UserInfo?, throwable: Throwable?) {
        when {
            throwable != null -> {
                throwableObserver.postValue(throwable.localizedMessage)
            }
            userInfo != null -> {
                this.userInfo.value = userInfo
            }
        }
    }

    override fun onExit(throwable: Throwable?) {
        if (throwable != null) {
            throwableObserver.postValue(throwable.localizedMessage)
        } else {
            successLogout.postValue(true)
            router.goToAuthScreen(androidComponent!!)
        }
    }

    override fun onClearCache() {
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}