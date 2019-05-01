package com.project.mobile_university.data.presentation

import com.project.mobile_university.data.shared.InfoItem

data class SettingsItem(
    override val id: Int,
    override val header: String?,
    override val description: String?,
    override val action: (() -> Unit)? = null
) : InfoItem