package com.project.mobile_university.presentation.login.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.services.ApiService
import com.project.mobile_university.domain.services.SharedPreferenceService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.login.interactor.LoginInteractor
import com.project.mobile_university.presentation.login.presenter.LoginPresenter
import com.project.mobile_university.presentation.login.router.LoginRouter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class LoginModule {
    @Provides
    fun providePresenter(androidComponent: AndroidComponent, viewModelFactory: LoginViewModelFactory): LoginContract.Presenter {
        return viewModelProvider(androidComponent, viewModelFactory).get(LoginPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(apiService: ApiService,
                          sharedPreferenceService: SharedPreferenceService,
                          errorHandler: ExceptionConverter): LoginContract.Interactor {
        return LoginInteractor(apiService, sharedPreferenceService, errorHandler)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(): LoginContract.Router {
        return LoginRouter()
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class LoginViewModelFactory @Inject constructor(private val interactor: LoginContract.Interactor,
                                                private val router: LoginContract.Router) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginPresenter(interactor, router) as T
    }
}