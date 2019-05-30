package com.project.mobile_university.presentation.user_info.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.UserInformation
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract

class UserInfoPresenter(
    private val interactor: UserInfoContract.Interactor,
    private val userId: Long
) : AbstractPresenter(),
    UserInfoContract.Presenter, UserInfoContract.Listener {

    override val userInfo = MutableLiveData<UserInformation>()
    override val throwable = MutableLiveData<Throwable>()
    override val loading = MutableLiveData<Boolean>()

    init {
        obtainUserInfo()
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
        interactor.setListener(null)
    }

    override fun obtainUserInfo() {
        loading.value = true
        interactor.getUserInfo(userId)
    }

    override fun onObtainUserInfo(user: UserInformation?, throwable: Throwable?) {
        when {
            throwable != null -> {
                this.throwable.value = throwable
            }
            user != null -> {
                this.userInfo.value = user
            }
        }

        loading.value = false
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}