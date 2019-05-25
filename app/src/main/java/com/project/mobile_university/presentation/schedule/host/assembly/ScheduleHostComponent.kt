package com.project.mobile_university.presentation.schedule.host.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.presenter.ScheduleHostPresenter
import com.project.mobile_university.presentation.schedule.host.router.ScheduleHostRouter
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject


@Subcomponent(modules = [ScheduleHostModule::class])
@PerFeatureLayerScope
interface ScheduleHostComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun identifier(identifier: Long): Builder

        @BindsInstance
        fun screenType(initialScreenType: ScheduleHostContract.InitialScreenType): Builder

        fun build(): ScheduleHostComponent
    }

    fun getPresenter(): ScheduleHostContract.Presenter
}

@Module
class ScheduleHostModule {

    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: ScheduleHostViewModelFactory
    ): ScheduleHostContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(ScheduleHostPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(
        subgroupInputModule: ScheduleSubgroupContract.InputModule,
        teacherInputModule: TeacherScheduleContract.InputModule,
        settingsInputModule: SettingsContract.InputModule,
        lessonInfoStudentInputModule: LessonInfoStudentContract.InputModule,
        lessonInfoTeacherInputModule: LessonInfoTeacherContract.InputModule,
        checkListInputModule: CheckListContract.InputModule
    ): ScheduleHostContract.Router {
        return ScheduleHostRouter(
            subgroupInputModule,
            teacherInputModule,
            settingsInputModule,
            lessonInfoStudentInputModule,
            lessonInfoTeacherInputModule,
            checkListInputModule
        )
    }
}

@PerFeatureLayerScope
class ScheduleHostViewModelFactory @Inject constructor(
    private val identifier: Long,
    private val initialScreenType: ScheduleHostContract.InitialScreenType,
    private val router: ScheduleHostContract.Router
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleHostPresenter(
            identifier,
            initialScreenType,
            router
        ) as T
    }
}
