package com.project.mobile_university.presentation.schedule_range.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.schedule_range.interactor.ScheduleRangeInteractor
import com.project.mobile_university.presentation.schedule_range.presenter.ScheduleRangePresenter
import com.project.mobile_university.presentation.schedule_range.router.ScheduleRangeRouter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

@PerFeatureLayerScope
@Subcomponent(modules = [ScheduleRangeModule::class])
interface ScheduleRangeComponent {

    fun getPresenter(): ScheduleRangeContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

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
    fun provideInteractor(): ScheduleRangeContract.Interactor {
        return ScheduleRangeInteractor()
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
    private val router: ScheduleRangeContract.Router
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleRangePresenter(
            interactor,
            router
        ) as T
    }
}