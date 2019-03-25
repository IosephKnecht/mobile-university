package com.project.mobile_university.presentation.login.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.presentation.addSource
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.login.contract.LoginContract

class LoginPresenter(private val interactor: LoginContract.Interactor,
                     private val router: LoginContract.Router) : AbstractPresenter(), LoginContract.Presenter,
    LoginContract.Listener {

    override val enterEnabled = MutableLiveData<Boolean>()
    override val state = MutableLiveData<LoginContract.State>(LoginContract.State.IDLE)

    override val serviceUrl = MutableLiveData<String>()
    override val login = MutableLiveData<String>()
    override val password = MutableLiveData<String>()
    override val errorObserver = SingleLiveData<String>()

    private val params = MediatorLiveData<String>()


    init {
        params.apply {
            addSource(serviceUrl)
            addSource(login)
            addSource(password)
        }
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)

        if (state.value == LoginContract.State.IDLE) {
            obtainServerConfig()
        }

        // FIXME: maybe remake on rx java?
        params.observe(androidComponent.activityComponent as LifecycleOwner, Observer {
            val serviceCondition = serviceUrl.value?.run {
                isNotEmpty()
            } ?: false

            val loginCondition = login.value?.run {
                length > 3
            } ?: false

            val passwordCondition = password.value?.run {
                length > 3
            } ?: false

            enterEnabled.postValue(
                serviceCondition &&
                    loginCondition &&
                    passwordCondition
            )
        })
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun tryLogin() {
        state.postValue(LoginContract.State.PROCESSING_AUTHORIZE)
        interactor.login(login.value!!, password.value!!)
    }

    override fun onChangeServerConfig(serverConfig: ServerConfig) {
        interactor.saveServerConfig(serverConfig)
    }

    override fun obtainServerConfig() {
        interactor.getServerConfig()
    }

    override fun onLogin(user: User?, throwable: Throwable?) {
        when {
            throwable != null -> {
                errorObserver.postValue(throwable.localizedMessage)
                state.postValue(LoginContract.State.ERROR_AUTHORIZE)
            }
            user != null -> {
                state.postValue(LoginContract.State.SUCCESS_AUTHORIZE)
                interactor.saveLoginPassString(login.value!!, password.value!!)

                when (user) {
                    is Student -> {
                        router.showStudentScheduleScreen(androidComponent!!, user.subgroupId)
                    }
                    is Teacher -> {
                        router.showTeacherScheduleScreen(androidComponent!!, user.teacherId)
                    }
                }
            }
            else -> state.postValue(LoginContract.State.NOT_AUTHORIZE)
        }
    }

    override fun onObtainServerConfig(serverConfig: ServerConfig?, throwable: Throwable?) {
        if (throwable == null && serverConfig != null) {
            val serverConfigString = serverConfig.toString()
            this.serviceUrl.postValue(serverConfigString)
            interactor.setServiceUrl(serverConfigString)
        }
        state.postValue(LoginContract.State.NOT_AUTHORIZE)
    }
}