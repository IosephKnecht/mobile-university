package com.project.mobile_university.presentation.settings.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.interactor.SettingsInteractor
import com.project.mobile_university.presentation.settings.presenter.SettingsPresenter
import com.project.mobile_university.presentation.settings.router.SettingsRouter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class SettingsModule {
    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: SettingsViewModelFabric
    ): SettingsContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(SettingsPresenter::class.java)
    }

    @PerFeatureLayerScope
    @Provides
    fun provideInteractor(
        loginRepository: LoginRepository,
        exceptionConverter: ExceptionConverter
    ): SettingsContract.Interactor {
        return SettingsInteractor(
            loginRepository,
            exceptionConverter
        )
    }

    @PerFeatureLayerScope
    @Provides
    fun provideRouter(): SettingsContract.Router {
        return SettingsRouter()
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class SettingsViewModelFabric @Inject constructor(
    private val interactor: SettingsContract.Interactor,
    private val router: SettingsContract.Router
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsPresenter(interactor, router) as T
    }

}