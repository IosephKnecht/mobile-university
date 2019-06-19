package com.project.mobile_university.presentation.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.databinding.FragmentSettingsBinding
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostListener
import com.project.mobile_university.presentation.settings.assembly.SettingsComponent
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.view.adapter.SettingsAdapter
import com.project.mobile_university.presentation.settings.view.adapter.SettingsItemBuilder
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : AbstractFragment<SettingsContract.Presenter>(), FragmentBackPressed {

    companion object {
        const val TAG = "settings_screen"

        fun createInstance() = SettingsFragment()
    }

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var diComponent: SettingsComponent
    private lateinit var adapter: SettingsAdapter

    interface Host : ScheduleHostListener

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

        adapter = SettingsAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SettingsFragment.adapter
            setHasFixedSize(false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                this.setDrawable(ContextCompat.getDrawable(context, R.drawable.ic_divider)!!)
            })
        }

        adapter.reload(SettingsItemBuilder.buildList(this))

        presenter.throwableObserver.observe(viewLifecycleOwner, Observer { message ->
            if (message != null) context?.let { Toasty.error(it, message, Toast.LENGTH_LONG).show() }
        })

        presenter.successClear.observe(viewLifecycleOwner, Observer { success ->
            if (success == true) {
                val message = context!!.getString(R.string.success_clear_cache)
                context?.let { Toasty.success(it, message, Toast.LENGTH_SHORT).show() }
            }
        })
    }

    override fun onBackPressed() = true
}