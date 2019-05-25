package com.project.mobile_university.presentation.lessonInfo.student.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import com.project.mobile_university.presentation.lessonInfo.student.interactor.LessonInfoStudentInteractor
import com.project.mobile_university.presentation.lessonInfo.student.presenter.LessonInfoStudentPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class LessonInfoModule {
    @Provides
    fun providePresenter(fragment: Fragment, factory: LessonInfoStudentFactory): LessonInfoStudentContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(LessonInfoStudentPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleRepository: ScheduleRepository): LessonInfoStudentContract.Interactor {
        return LessonInfoStudentInteractor(scheduleRepository)
    }
}

@PerFeatureLayerScope
class LessonInfoStudentFactory @Inject constructor(private val interactor: LessonInfoStudentContract.Interactor,
                                            private val lessonId: Long) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LessonInfoStudentPresenter(lessonId, interactor) as T
    }

}