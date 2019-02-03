package com.project.mobile_university.presentation.login.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.schedule.subgroup.contract.ScheduleSubgroupContract
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment

class LoginRouter(private val scheduleSubgroupInputModule: ScheduleSubgroupContract.InputModule)
    : AbstractRouter<LoginContract.RouterListener>(), LoginContract.Router {

    override fun showStudentScheduleScreen(androidComponent: AndroidComponent, subgroupId: Long) {
        if (androidComponent.fragmentManagerComponent != null) {
            androidComponent.apply {
                val oldFragment = fragmentManagerComponent!!
                    .findFragmentByTag(ScheduleSubgroupFragment.TAG)

                if (oldFragment == null) {
                    fragmentManagerComponent!!.beginTransaction()
                        .replace(R.id.fragment_container,
                            scheduleSubgroupInputModule.createFragment(subgroupId),
                            ScheduleSubgroupFragment.TAG)
                        .commit()
                }
            }
        }
    }
}