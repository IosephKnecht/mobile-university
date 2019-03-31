package com.project.mobile_university.presentation.lessonInfo.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import com.project.mobile_university.presentation.lessonInfo.presenter.LessonInfoPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class LessonInfoModule {
    @Provides
    fun providePresenter(fragment: Fragment, factory: LessonInfoFactory): LessonInfoContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(LessonInfoPresenter::class.java)
    }
}

@PerFeatureLayerScope
class LessonInfoFactory @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LessonInfoPresenter() as T
    }

}