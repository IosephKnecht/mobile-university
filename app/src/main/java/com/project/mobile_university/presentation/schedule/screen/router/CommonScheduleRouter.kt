package com.project.mobile_university.presentation.schedule.screen.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract.ScreenState
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.view.TeacherScheduleFragment
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.view.SettingsFragment

class CommonScheduleRouter(private val subgroupInputModule: ScheduleSubgroupContract.InputModule,
                           private val teacherInputModule: TeacherScheduleContract.InputModule,
                           private val settingsInputModule: SettingsContract.InputModule)
    : AbstractRouter<CommonScheduleContract.RouterListener>(), CommonScheduleContract.Router {

    override fun showSubgroupScreen(androidComponent: AndroidComponent, identifier: Long) {
        androidComponent.fragmentManagerComponent?.let {
            val tag = matchScheduleFragmentTag(ScreenState.SUBGROUP)
            val fragment = it.findFragmentByTag(tag)

            if (fragment == null) {
                it.beginTransaction()
                    .replace(R.id.schedule_fragment_container,
                        subgroupInputModule.createFragment(identifier),
                        tag)
                    .commit()

                routerListener?.onChangeScreen(ScreenState.SUBGROUP)
            }
        }
    }

    override fun showTeacherScreen(androidComponent: AndroidComponent, identifier: Long) {
        androidComponent.fragmentManagerComponent?.let {
            val tag = matchScheduleFragmentTag(ScreenState.TEACHER)
            val fragment = it.findFragmentByTag(tag)

            if (fragment == null) {
                it.beginTransaction()
                    .replace(R.id.schedule_fragment_container,
                        teacherInputModule.createFragment(identifier),
                        tag)
                    .commit()

                routerListener?.onChangeScreen(ScreenState.TEACHER)
            }
        }
    }

    override fun showSettingsScreen(androidComponent: AndroidComponent) {
        androidComponent.fragmentManagerComponent?.let {
            val tag = matchScheduleFragmentTag(ScreenState.SETTINGS)
            val fragment = it.findFragmentByTag(tag)

            if (fragment == null) {
                it.beginTransaction()
                    .replace(R.id.schedule_fragment_container,
                        settingsInputModule.createFragment(),
                        tag)
                    .commit()

                routerListener?.onChangeScreen(ScreenState.SETTINGS)
            }
        }
    }

    private fun matchScheduleFragmentTag(type: CommonScheduleContract.ScreenState): String {
        return when (type) {
            CommonScheduleContract.ScreenState.SUBGROUP -> ScheduleSubgroupFragment.TAG
            CommonScheduleContract.ScreenState.TEACHER -> TeacherScheduleFragment.TAG
            CommonScheduleContract.ScreenState.SETTINGS -> SettingsFragment.TAG
        }
    }
}