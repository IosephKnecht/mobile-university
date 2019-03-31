package com.project.mobile_university.presentation.schedule.teacher.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.interactor.TeacherScheduleInteractor
import com.project.mobile_university.presentation.schedule.teacher.presenter.TeacherSchedulePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class TeacherScheduleModule {
    @Provides
    fun providePresenter(fragment: Fragment,
                         viewModelFactory: TeacherScheduleViewModelFactory): TeacherScheduleContract.Presenter {
        return ViewModelProviders.of(fragment, viewModelFactory).get(TeacherSchedulePresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleRepository: ScheduleRepository,
                          exceptionConverter: ExceptionConverter): TeacherScheduleContract.Interactor {

        return TeacherScheduleInteractor(scheduleRepository, exceptionConverter)
    }
}


@Suppress("UNCHECKED_CAST")
@PerFeatureLayerScope
class TeacherScheduleViewModelFactory @Inject constructor(private val interactor: TeacherScheduleContract.Interactor,
                                                          private val teacherId: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TeacherSchedulePresenter(interactor, teacherId) as T
    }

}