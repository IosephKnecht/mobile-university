package com.project.mobile_university.presentation.schedule.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import com.project.mobile_university.presentation.schedule.interactor.ScheduleInteractor
import com.project.mobile_university.presentation.schedule.presenter.SchedulePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ScheduleModule {
    @Provides
    fun providePresenter(androidComponent: AndroidComponent,
                         factory: ScheduleViewModelFactory): ScheduleContract.Presenter {
        return viewModelProvider(androidComponent, factory).get(SchedulePresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(apiService: ApiService,
                          databaseService: DatabaseService): ScheduleContract.Interactor {
        return ScheduleInteractor(apiService, databaseService)
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class ScheduleViewModelFactory @Inject constructor(private val interactor: ScheduleContract.Interactor,
                                                   private val groupId: Long)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SchedulePresenter(interactor, groupId) as T
    }

}