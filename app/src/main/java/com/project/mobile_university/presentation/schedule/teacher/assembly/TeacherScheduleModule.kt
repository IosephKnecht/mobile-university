package com.project.mobile_university.presentation.schedule.teacher.assembly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.iosephknecht.viper.viewModelProvider
import com.project.mobile_university.domain.ApiService
import com.project.mobile_university.domain.DatabaseService
import com.project.mobile_university.domain.ScheduleService
import com.project.mobile_university.domain.adapters.exception.ExceptionConverter
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
    fun providePresenter(androidComponent: AndroidComponent,
                         viewModelFactory: TeacherScheduleViewModelFactory): TeacherScheduleContract.Presenter {
        return viewModelProvider(androidComponent, viewModelFactory).get(TeacherSchedulePresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleService: ScheduleService,
                          exceptionConverter: ExceptionConverter): TeacherScheduleContract.Interactor {

        return TeacherScheduleInteractor(scheduleService, exceptionConverter)
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