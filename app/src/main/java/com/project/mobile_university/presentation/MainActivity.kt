package com.project.mobile_university.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.mobile_university.R
import com.project.mobile_university.presentation.login.view.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

    companion object {
        fun createInstance(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            val fragment = LoginFragment.createInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, LoginFragment.TAG)
                .commit()
        }
    }
}
