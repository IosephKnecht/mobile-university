package com.project.mobile_university.presentation.lessonInfo.assembly

import androidx.fragment.app.Fragment
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import dagger.BindsInstance
import dagger.Subcomponent

@PerFeatureLayerScope
@Subcomponent(modules = [LessonInfoModule::class])
interface LessonInfoComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        fun build(): LessonInfoComponent
    }

    fun getPresenter(): LessonInfoContract.Presenter
}