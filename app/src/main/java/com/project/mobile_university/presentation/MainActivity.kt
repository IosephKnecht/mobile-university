package com.project.mobile_university.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.mobile_university.R
import com.project.mobile_university.presentation.login.view.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (supportFragmentManager.findFragmentByTag(LoginFragment.TAG) == null) {
            val fragment = LoginFragment.createInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, LoginFragment.TAG)
                .commit()
        }
    }
}
