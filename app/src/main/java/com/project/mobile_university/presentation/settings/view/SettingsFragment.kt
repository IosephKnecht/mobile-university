package com.project.mobile_university.presentation.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.databinding.FragmentSettingsBinding
import com.project.mobile_university.presentation.settings.assembly.SettingsComponent
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : AbstractFragment<SettingsContract.Presenter>() {

    companion object {
        const val TAG = "settings_screen"

        fun createInstance() = SettingsFragment()
    }

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var diComponent: SettingsComponent

    override fun inject() {
        diComponent = AppDelegate.presentationComponent
            .settingsSubComponent()
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.viewModel = presenter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clear_cache.setOnClickListener {
            presenter.clearCache()
        }

        logout.setOnClickListener {
            presenter.exit()
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.throwableObserver.observe(this) {
            if (it != null) Toasty.error(context!!, it, Toast.LENGTH_LONG).show()
        }

        presenter.successLogout.observe(this) {
            if (it == true) {
                val message = context!!.getString(R.string.success_logout)
                Toasty.success(context!!, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}