package com.project.mobile_university.presentation.settings.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.SharedPreferenceService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
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
    fun providePresenter(androidComponent: AndroidComponent,
                         factory: SettingsViewModelFabric): SettingsContract.Presenter {
        return viewModelProvider(androidComponent, factory).get(SettingsPresenter::class.java)
    }

    @PerFeatureLayerScope
    @Provides
    fun provideInteractor(sharedPreferenceService: SharedPreferenceService,
                          exceptionConverter: ExceptionConverter): SettingsContract.Interactor {
        return SettingsInteractor(sharedPreferenceService,
            exceptionConverter)
    }

    @PerFeatureLayerScope
    @Provides
    fun provideRouter(): SettingsContract.Router {
        return SettingsRouter()
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class SettingsViewModelFabric @Inject constructor(private val interactor: SettingsContract.Interactor,
                                                  private val router: SettingsContract.Router)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsPresenter(interactor, router) as T
    }

}