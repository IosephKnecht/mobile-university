package com.project.mobile_university.presentation.settings.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.SettingsItem
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class SettingsAdapter : ViewModelAdapter() {
    init {
        cell<SettingsItem>(R.layout.item_settings, BR.item)
    }
}