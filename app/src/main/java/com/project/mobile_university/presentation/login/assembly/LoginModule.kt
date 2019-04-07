package com.project.mobile_university.presentation.login.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.login.interactor.LoginInteractor
import com.project.mobile_university.presentation.login.presenter.LoginPresenter
import com.project.mobile_university.presentation.login.router.LoginRouter
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class LoginModule {
    @Provides
    fun providePresenter(fragment: Fragment, viewModelFactory: LoginViewModelFactory): LoginContract.Presenter {
        return ViewModelProviders.of(fragment, viewModelFactory).get(LoginPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(
        loginRepository: LoginRepository,
        errorHandler: ExceptionConverter
    ): LoginContract.Interactor {
        return LoginInteractor(loginRepository, errorHandler)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(scheduleHostInputModule: ScheduleHostContract.InputModule): LoginContract.Router {
        return LoginRouter(scheduleHostInputModule)
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class LoginViewModelFactory @Inject constructor(
    private val interactor: LoginContract.Interactor,
    private val router: LoginContract.Router
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginPresenter(interactor, router) as T
    }
}