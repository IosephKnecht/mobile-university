package com.project.mobile_university.presentation.schedule.screen.assembly

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract
import com.project.mobile_university.presentation.schedule.screen.presenter.CommonSchedulePresenter
import com.project.mobile_university.presentation.schedule.screen.router.CommonScheduleRouter
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class CommonScheduleModule {
    @Provides
    fun providePresenter(activity: FragmentActivity,
                         factory: CommonScheduleFactory): CommonScheduleContract.Presenter {
        return ViewModelProviders.of(activity, factory).get(CommonSchedulePresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(subgroupModule: ScheduleSubgroupContract.InputModule,
                      teacherScheduleModule: TeacherScheduleContract.InputModule,
                      settingsModule: SettingsContract.InputModule): CommonScheduleContract.Router {
        return CommonScheduleRouter(subgroupModule,
            teacherScheduleModule,
            settingsModule)
    }
}

@Suppress("UNCHECKED_CAST")
class CommonScheduleFactory @Inject constructor(private val screenState: CommonScheduleContract.ScreenState,
                                                private val identifier: Long,
                                                private val router: CommonScheduleContract.Router) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommonSchedulePresenter(screenState, identifier, router) as T
    }
}