package com.project.mobile_university.presentation.login.assembly

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.login.interactor.LoginInteractor
import com.project.mobile_university.presentation.login.presenter.LoginPresenter
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
                          sharedPreferenceService: SharedPreferenceService): LoginContract.Interactor {
        return LoginInteractor(apiService, sharedPreferenceService)
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class LoginViewModelFactory @Inject constructor(private val interactor: LoginContract.Interactor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginPresenter(interactor) as T
    }
}