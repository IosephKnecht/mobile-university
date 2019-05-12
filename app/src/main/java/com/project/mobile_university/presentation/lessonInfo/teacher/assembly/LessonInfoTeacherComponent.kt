package com.project.mobile_university.presentation.lessonInfo.teacher.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import com.project.mobile_university.presentation.lessonInfo.teacher.interactor.LessonInfoTeacherInteractor
import com.project.mobile_university.presentation.lessonInfo.teacher.presenter.LessonInfoTeacherPresenter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

@Subcomponent(modules = [LessonInfoTeacherModule::class])
@PerFeatureLayerScope
interface LessonInfoTeacherComponent {
    fun getPresenter(): LessonInfoTeacherContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun lessonExtId(lessonExtId: Long): Builder

        @BindsInstance
        fun with(fragment: Fragment): Builder

        fun build(): LessonInfoTeacherComponent
    }
}

@Module
class LessonInfoTeacherModule {

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleRepository: ScheduleRepository): LessonInfoTeacherContract.Interactor {
        return LessonInfoTeacherInteractor(scheduleRepository)
    }

    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: LessonInfoTeacherViewModelFactory
    ): LessonInfoTeacherContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(LessonInfoTeacherPresenter::class.java)
    }
}

@PerFeatureLayerScope
class LessonInfoTeacherViewModelFactory @Inject constructor(
    private val lessonExtId: Long,
    private val interactor: LessonInfoTeacherContract.Interactor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LessonInfoTeacherPresenter(lessonExtId, interactor) as T
    }
}