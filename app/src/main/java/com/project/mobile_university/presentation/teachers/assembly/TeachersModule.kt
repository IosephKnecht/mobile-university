package com.project.mobile_university.presentation.teachers.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.teachers.interactor.TeachersInteractor
import com.project.mobile_university.presentation.teachers.presenter.TeachersPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class TeachersModule {
    @Provides
    fun provideViewModel(androidComponent: AndroidComponent,
                         factory: TeachersViewModelFactory): TeachersContract.Presenter {
        return viewModelProvider(androidComponent, factory).get(TeachersPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(exceptionConverter: ExceptionConverter): TeachersContract.Interactor {
        return TeachersInteractor(exceptionConverter)
    }
}

@PerFeatureLayerScope
class TeachersViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeachersPresenter() as T
    }
}