package com.project.mobile_university.presentation.schedule.subgroup.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.services.ScheduleService
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
    fun providePresenter(fragment: Fragment,
                         factory: ScheduleViewModelFactory
    ): ScheduleSubgroupContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(ScheduleSubgroupPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleService: ScheduleService,
                          errorHandler: ExceptionConverter): ScheduleSubgroupContract.Interactor {
        return ScheduleSubgroupInteractor(scheduleService,
            errorHandler)
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