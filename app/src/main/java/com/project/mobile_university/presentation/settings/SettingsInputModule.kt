package com.project.mobile_university.presentation.settings

import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.view.SettingsFragment

class SettingsInputModule : SettingsContract.InputModule {
    override fun createFragment(): SettingsFragment {
        return SettingsFragment.createInstance()
    }

}