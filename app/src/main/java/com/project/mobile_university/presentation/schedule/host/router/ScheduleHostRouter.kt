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
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.view.TeacherScheduleFragment
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.schedule_range.view.ScheduleRangeFragment
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.view.SettingsFragment
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.teachers.view.TeachersFragment
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import com.project.mobile_university.presentation.user_info.view.UserInfoFragment
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.InitialScreenType
import java.util.*

class ScheduleHostRouter(
    private val subgroupInputModule: ScheduleSubgroupContract.InputModule,
    private val teacherInputModule: TeacherScheduleContract.InputModule,
    private val settingsInputModule: SettingsContract.InputModule,
    private val lessonInfoStudentInputModule: LessonInfoStudentContract.InputModule,
    private val lessonInfoTeacherInputModule: LessonInfoTeacherContract.InputModule,
    private val checkListInputModule: CheckListContract.InputModule,
    private val teachersInputModule: TeachersContract.InputModule,
    private val userInfoInputModule: UserInfoContract.InputModule,
    private val scheduleRangeInputModule: ScheduleRangeContract.InputModule,
    private val loginInputModule: LoginContract.InputModule
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

    override fun showTeachersScreen(
        androidComponent: AndroidComponent,
        needPopBackStack: InitialScreenType?
    ) {
        androidComponent.fragmentManagerComponent?.showIfNeedAndAddToBackStack(
            TeachersFragment.TAG,
            { fragmentManager ->
                needPopBackStack?.let { initialScreenType ->
                    popBackStackUntilInitialScreenType(fragmentManager, initialScreenType)
                }

                teachersInputModule.createFragment()
            },
            {
                routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.TEACHERS_SCREEN)
            })
    }

    override fun showUserInfo(
        androidComponent: AndroidComponent,
        userId: Long,
        isMe: Boolean,
        needPopBackStack: InitialScreenType?
    ) {
        androidComponent.fragmentManagerComponent?.showIfNeedAndAddToBackStack(
            "${UserInfoFragment.TAG}/isMe = $isMe",
            { fragmentManager ->
                needPopBackStack?.let { initialScreenType ->
                    popBackStackUntilInitialScreenType(fragmentManager, initialScreenType)
                }

                userInfoInputModule.createFragment(userId, isMe)
            },
            {
                routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.USER_INFO)
            })
    }

    override fun showScheduleRange(
        androidComponent: AndroidComponent,
        teacherId: Long,
        startDate: Date,
        endDate: Date
    ) {
        androidComponent.fragmentManagerComponent?.showIfNeedAndAddToBackStack(ScheduleRangeFragment.TAG, {
            scheduleRangeInputModule.createFragment(
                teacherId = teacherId,
                startDate = startDate,
                endDate = endDate
            )
        }, {
            routerListener?.onChangeScreen(ScheduleHostContract.CurrentScreenType.SCHEDULE_RANGE)
        })
    }

    override fun logout(androidComponent: AndroidComponent) {
        androidComponent.activityComponent?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, loginInputModule.createFragment())
            ?.commit()
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
        block: (FragmentManager) -> Fragment,
        callback: ((Fragment) -> Unit)? = null
    ) {
        val fragment = findFragmentByTag(tag)
        if (fragment == null) {
            val newInstanceFragment = block.invoke(this)

            beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
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

    private fun FragmentManager.popBackStackUntil(tag: String) {
        fragments.forEach { fragment ->
            if (fragment.tag != tag) popBackStackImmediate()
        }
    }

    private fun popBackStackUntilInitialScreenType(
        fragmentManager: FragmentManager,
        initialScreenType: InitialScreenType
    ) {
        val name = when (initialScreenType) {
            InitialScreenType.SUBGROUP -> ScheduleSubgroupFragment.TAG
            InitialScreenType.TEACHER -> TeacherScheduleFragment.TAG
        }

        fragmentManager.popBackStackUntil(name)
    }

    private fun mapFragmentToScreenType(fragment: Fragment?): ScheduleHostContract.CurrentScreenType? {
        return when (fragment) {
            is SettingsFragment -> ScheduleHostContract.CurrentScreenType.SETTINGS
            is TeacherScheduleFragment -> ScheduleHostContract.CurrentScreenType.TEACHER
            is ScheduleSubgroupFragment -> ScheduleHostContract.CurrentScreenType.SUBGROUP
            is LessonInfoStudentFragment -> ScheduleHostContract.CurrentScreenType.LESSON_INFO
            is CheckListFragment -> ScheduleHostContract.CurrentScreenType.CHECK_LIST
            is TeachersFragment -> ScheduleHostContract.CurrentScreenType.TEACHERS_SCREEN
            is UserInfoFragment -> ScheduleHostContract.CurrentScreenType.USER_INFO
            else -> null
        }
    }
}