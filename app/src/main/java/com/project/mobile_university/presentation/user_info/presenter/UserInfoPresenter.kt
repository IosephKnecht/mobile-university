package com.project.mobile_university.presentation.user_info.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.UserInformation
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import com.project.mobile_university.presentation.user_info.view.adapter.UserContactViewState

class UserInfoPresenter(
    private val interactor: UserInfoContract.Interactor,
    private val router: UserInfoContract.Router,
    private val userId: Long,
    private val isMe: Boolean
) : AbstractPresenter(),
    UserInfoContract.Presenter,
    UserInfoContract.Listener,
    UserInfoContract.RouterListener {

    override val userInfo = MutableLiveData<UserInformation>()
    override val throwable = MutableLiveData<Throwable>()
    override val loading = MutableLiveData<Boolean>()
    override val userContacts = MutableLiveData<List<UserContactViewState>>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        if (userInfo.value == null) {
            obtainUserInfo()
        }

        interactor.setListener(this)
        router.setListener(this)
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        router.setListener(null)

        super.detachAndroidComponent()
    }

    override fun obtainUserInfo() {
        loading.value = true
        interactor.getUserInfo(userId)
    }

    override fun sendEmail(email: String) {
        androidComponent?.let { router.trySendEmail(it, email) }
    }

    override fun onFailSendEmail(throwable: Throwable) {
        this.throwable.value = throwable
    }

    override fun onObtainUserInfo(user: UserInformation?, throwable: Throwable?) {
        when {
            throwable != null -> {
                this.throwable.value = throwable
            }
            user != null -> {
                this.userInfo.value = user
                this.userContacts.value = mapUserContacts(user)
            }
        }

        loading.value = false
    }

    private fun mapUserContacts(userInformation: UserInformation): List<UserContactViewState> {
        return userInformation.userContacts.map {
            val clickListener = if (!isMe) {
                val click: () -> Unit = {
                    sendEmail(it.value)
                }
                click
            } else {
                null
            }

            UserContactViewState(
                model = it,
                clickListener = clickListener
            )
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}