package com.project.mobile_university.presentation.schedule_range.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.schedule_range.interactor.ScheduleRangeInteractor
import com.project.mobile_university.presentation.schedule_range.presenter.ScheduleRangePresenter
import com.project.mobile_university.presentation.schedule_range.router.ScheduleRangeRouter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@PerFeatureLayerScope
@Subcomponent(modules = [ScheduleRangeModule::class])
interface ScheduleRangeComponent {

    fun getPresenter(): ScheduleRangeContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun startDate(@Named(value = "start_date") startDate: Date): Builder

        @BindsInstance
        fun endDate(@Named(value = "end_date") endDate: Date): Builder

        @BindsInstance
        fun teacherId(teacherId: Long): Builder

        fun build(): ScheduleRangeComponent
    }
}

@Module
class ScheduleRangeModule {
    @Provides
    @PerFeatureLayerScope
    fun provideRouter(): ScheduleRangeContract.Router {
        return ScheduleRangeRouter()
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(
        scheduleRepository: ScheduleRepository,
        sharedPreferenceService: SharedPreferenceService
    ): ScheduleRangeContract.Interactor {
        return ScheduleRangeInteractor(scheduleRepository, sharedPreferenceService)
    }

    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: ScheduleRangeViewModelFactory
    ): ScheduleRangeContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(ScheduleRangePresenter::class.java)
    }
}

@PerFeatureLayerScope
class ScheduleRangeViewModelFactory @Inject constructor(
    private val interactor: ScheduleRangeContract.Interactor,
    private val router: ScheduleRangeContract.Router,
    @Named(value = "start_date")
    private val startDate: Date,
    @Named(value = "end_date")
    private val endDate: Date,
    private val teacherId: Long
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleRangePresenter(
            interactor,
            router,
            startDate,
            endDate,
            teacherId
        ) as T
    }
}