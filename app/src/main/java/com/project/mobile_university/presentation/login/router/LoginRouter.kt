package com.project.mobile_university.presentation.login.router

import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import com.project.mobile_university.presentation.schedule.view.ScheduleFragment

class LoginRouter(private val scheduleInputModule: ScheduleContract.ScheduleInputModuleContract)
    : AbstractRouter<LoginContract.RouterListener>(), LoginContract.Router {

    override fun showNextScreen(androidComponent: AndroidComponent) {
        if (androidComponent.fragmentManagerComponent != null) {
            androidComponent.apply {
                val oldFragment = fragmentManagerComponent!!
                    .findFragmentByTag(ScheduleFragment.TAG)

                if (oldFragment == null) {
                    fragmentManagerComponent!!.beginTransaction()
                        .replace(R.id.fragment_container,
                            //FIXME: provide group id if user is student
                            scheduleInputModule.createFragment(1L),
                            ScheduleFragment.TAG)
                        .commit()
                }
            }
        }
    }
}