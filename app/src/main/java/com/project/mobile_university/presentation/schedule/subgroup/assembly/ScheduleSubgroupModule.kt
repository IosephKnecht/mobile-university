package com.project.mobile_university.presentation.schedule.subgroup.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.interactor.ScheduleSubgroupInteractor
import com.project.mobile_university.presentation.schedule.subgroup.presenter.ScheduleSubgroupPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class ScheduleModule {
    @Provides
    fun providePresenter(androidComponent: AndroidComponent,
                         factory: ScheduleViewModelFactory
    ): ScheduleSubgroupContract.Presenter {
        return viewModelProvider(androidComponent, factory).get(ScheduleSubgroupPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(apiService: ApiService,
                          databaseService: DatabaseService,
                          errorHandler: ExceptionConverter): ScheduleSubgroupContract.Interactor {
        return ScheduleSubgroupInteractor(
            apiService,
            databaseService,
            errorHandler
        )
    }
}

@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class ScheduleViewModelFactory @Inject constructor(private val interactor: ScheduleSubgroupContract.Interactor,
                                                   private val groupId: Long)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleSubgroupPresenter(
            interactor,
            groupId
        ) as T
    }

}