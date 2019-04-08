package com.project.mobile_university.presentation.schedule.host.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.view.TeacherScheduleFragment
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.view.SettingsFragment

class ScheduleHostRouter(
    private val subgroupInputModule: ScheduleSubgroupContract.InputModule,
    private val teacherInputModule: TeacherScheduleContract.InputModule,
    private val settingsInputModule: SettingsContract.InputModule,
    private val lessonInfoInputModule: LessonInfoContract.InputModule
) : AbstractRouter<ScheduleHostContract.RouterListener>(), ScheduleHostContract.Router {

    override fun showSubgroupScreen(androidComponent: AndroidComponent, identifier: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeed(ScheduleSubgroupFragment.TAG, {
            subgroupInputModule.createFragment(identifier)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.SUBGROUP)
        })
    }

    override fun showTeacherScreen(androidComponent: AndroidComponent, identifier: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeed(TeacherScheduleFragment.TAG, {
            teacherInputModule.createFragment(identifier)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.TEACHER)
        })
    }

    override fun showSettingsScreen(androidComponent: AndroidComponent) {
        androidComponent.fragmentManagerComponent?.showIfNeed(SettingsFragment.TAG, {
            settingsInputModule.createFragment()
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.SETTINGS)
        })
    }

    override fun showLessonInfo(androidComponent: AndroidComponent, lessonId: Long) {
        androidComponent.fragmentManagerComponent
            ?.beginTransaction()
            ?.replace(R.id.schedule_fragment_container, lessonInfoInputModule.createFragment(lessonId))
            ?.addToBackStack(null)
            ?.commit()

        routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.LESSON_INFO)
    }

    private fun FragmentManager.showIfNeed(tag: String, block: () -> Fragment, callback: ((Fragment) -> Unit)? = null) {
        val fragment = findFragmentByTag(tag)
        if (fragment == null) {
            val newInstanceFragment = block.invoke()

            beginTransaction()
                .replace(R.id.schedule_fragment_container, newInstanceFragment, tag)
                .commit()

            callback?.invoke(newInstanceFragment)
        }
    }
}