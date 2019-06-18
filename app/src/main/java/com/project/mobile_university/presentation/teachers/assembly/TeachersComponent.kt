package com.project.mobile_university.presentation.teachers.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.teachers.interactor.TeachersInteractor
import com.project.mobile_university.presentation.teachers.presenter.TeachersPresenter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

@Subcomponent(modules = [TeachersModule::class])
@PerFeatureLayerScope
interface TeachersComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        fun build(): TeachersComponent
    }

    fun getPresenter(): TeachersContract.Presenter
}

@Module
class TeachersModule {
    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: TeachersViewModelFactory
    ): TeachersContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(TeachersPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(
        scheduleRepository: ScheduleRepository,
        sharedPreferenceService: SharedPreferenceService
    ): TeachersContract.Interactor {
        return TeachersInteractor(
            scheduleRepository,
            sharedPreferenceService
        )
    }
}

@PerFeatureLayerScope
class TeachersViewModelFactory @Inject constructor(private val interactor: TeachersContract.Interactor) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeachersPresenter(interactor) as T
    }
}