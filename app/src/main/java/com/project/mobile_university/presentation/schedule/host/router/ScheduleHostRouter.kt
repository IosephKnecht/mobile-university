package com.project.mobile_university.presentation.schedule.host.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import com.project.mobile_university.presentation.lessonInfo.student.view.LessonInfoStudentFragment
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import com.project.mobile_university.presentation.lessonInfo.teacher.view.LessonInfoTeacherFragment
import com.project.mobile_university.presentation.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.check_list.view.CheckListFragment
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
    private val lessonInfoStudentInputModule: LessonInfoStudentContract.InputModule,
    private val lessonInfoTeacherInputModule: LessonInfoTeacherContract.InputModule,
    private val checkListInputModule: CheckListContract.InputModule
) : AbstractRouter<ScheduleHostContract.RouterListener>(), ScheduleHostContract.Router {

    override fun showSubgroupScreen(androidComponent: AndroidComponent, identifier: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeed(ScheduleSubgroupFragment.TAG, { fragmentManager ->
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStackImmediate()
            }

            subgroupInputModule.createFragment(identifier)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.SUBGROUP)
        })
    }

    override fun showTeacherScreen(androidComponent: AndroidComponent, identifier: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeed(TeacherScheduleFragment.TAG, { fragmentManager ->
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStackImmediate()
            }

            teacherInputModule.createFragment(identifier)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.TEACHER)
        })
    }

    override fun showSettingsScreen(androidComponent: AndroidComponent) {
        androidComponent.fragmentManagerComponent?.showIfNeed(SettingsFragment.TAG, { fragmentManager ->
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStackImmediate()
            }

            settingsInputModule.createFragment()
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.SETTINGS)
        })
    }

    override fun showLessonInfoStudent(androidComponent: AndroidComponent, lessonExtId: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeedAndAddToBackStack(LessonInfoStudentFragment.TAG, {
            lessonInfoStudentInputModule.createFragment(lessonExtId)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.LESSON_INFO)
        })
    }

    override fun showLessonInfoTeacher(androidComponent: AndroidComponent, lessonExtId: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeedAndAddToBackStack(LessonInfoTeacherFragment.TAG, {
            lessonInfoTeacherInputModule.createFragment(lessonExtId)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.LESSON_INFO)
        })
    }

    override fun showCheckList(androidComponent: AndroidComponent, checkListExtId: Long) {
        androidComponent.fragmentManagerComponent?.showIfNeedAndAddToBackStack(CheckListFragment.TAG, {
            checkListInputModule.createFragment(checkListExtId)
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.CHECK_LIST)
        })
    }

    override fun onBackPressed(androidComponent: AndroidComponent) {
        val currentScreenType = androidComponent.fragmentManagerComponent?.run {
            popBackStackImmediate()
            mapFragmentToScreenType(fragments.firstOrNull())
        }

        if (currentScreenType != null) {
            routerListener?.onChangeScreen(currentScreenType)
        }
    }

    private fun FragmentManager.showIfNeedAndAddToBackStack(
        tag: String,
        block: () -> Fragment,
        callback: ((Fragment) -> Unit)? = null
    ) {
        val fragment = findFragmentByTag(tag)
        if (fragment == null) {
            val newInstanceFragment = block.invoke()

            beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.schedule_fragment_container, newInstanceFragment, tag)
                .commit()

            callback?.invoke(newInstanceFragment)
        }
    }

    private fun FragmentManager.showIfNeed(
        tag: String,
        block: (FragmentManager) -> Fragment,
        callback: ((Fragment) -> Unit)? = null
    ) {
        val fragment = findFragmentByTag(tag)
        if (fragment == null) {
            val newInstanceFragment = block.invoke(this)

            beginTransaction()
                .replace(R.id.schedule_fragment_container, newInstanceFragment, tag)
                .commit()

            callback?.invoke(newInstanceFragment)
        }
    }

    private fun mapFragmentToScreenType(fragment: Fragment?): ScheduleHostContract.CurrentScreenType? {
        return when (fragment) {
            is SettingsFragment -> ScheduleHostContract.CurrentScreenType.SETTINGS
            is TeacherScheduleFragment -> ScheduleHostContract.CurrentScreenType.TEACHER
            is ScheduleSubgroupFragment -> ScheduleHostContract.CurrentScreenType.SUBGROUP
            is LessonInfoStudentFragment -> ScheduleHostContract.CurrentScreenType.LESSON_INFO
            is CheckListFragment -> ScheduleHostContract.CurrentScreenType.CHECK_LIST
            else -> null
        }
    }
}