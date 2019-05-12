package com.project.mobile_university.presentation.lessonInfo.student.assembly

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import dagger.BindsInstance
import dagger.Subcomponent

@PerFeatureLayerScope
@Subcomponent(modules = [LessonInfoModule::class])
interface LessonInfoStudentComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun lessonExtId(lessonExtId: Long): Builder

        fun build(): LessonInfoStudentComponent
    }

    fun getPresenter(): LessonInfoStudentContract.Presenter
}