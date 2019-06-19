package com.project.mobile_university.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.data.gson.Student
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.login.view.fragment.LoginFragment
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostFragment

class MainActivity : AppCompatActivity() {

    companion object {
        fun createInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    private val sharedPrefService by lazy {
        AppDelegate.businessComponent.sharedPrefService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            val previousUser = isPreviousUser()

            previousUser?.let { user ->
                when (user) {
                    is Student -> {
                        supportFragmentManager.addFragment(
                            ScheduleHostFragment.createInstance(
                                user.subgroupId,
                                ScheduleHostContract.InitialScreenType.SUBGROUP
                            )
                        )
                    }
                    is Teacher -> {
                        supportFragmentManager.addFragment(
                            ScheduleHostFragment.createInstance(
                                user.teacherId,
                                ScheduleHostContract.InitialScreenType.TEACHER
                            )
                        )
                    }
                }
            } ?: supportFragmentManager.addFragment(LoginFragment.createInstance())
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.fragment_container)?.let { fragment ->
            if (fragment is FragmentBackPressed && fragment.onBackPressed()) {
                super.onBackPressed()
            }
        }
    }

    private fun isPreviousUser(): User? {
        return try {
            return sharedPrefService.getUserInfo()
        } catch (e: Exception) {
            null
        }
    }

    private fun FragmentManager.addFragment(fragment: Fragment) {
        beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}
