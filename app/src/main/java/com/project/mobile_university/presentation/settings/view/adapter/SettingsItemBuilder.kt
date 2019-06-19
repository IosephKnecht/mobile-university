package com.project.mobile_university.presentation.settings.view.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.SettingsItem
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import com.project.mobile_university.presentation.settings.view.SettingsFragment

enum class SettingsEnum(
    val id: Int,
    @StringRes val header: Int,
    @StringRes val description: Int
) {
    CLEAR_CACHE(
        1, R.string.clear_cache_primary_text,
        R.string.clear_cache_description
    ),

    EXIT(
        2, R.string.exit_primary_text,
        R.string.exit_description
    ),
}

object SettingsItemBuilder {
    fun buildList(fragment: SettingsFragment): List<SettingsItem> {
        val settingsList = mutableListOf<SettingsItem>()
        val context = fragment.context

        SettingsEnum.values().forEach { settingsEnum ->
            context?.let {
                settingsList.add(enumToItem(it, settingsEnum, buildAction(settingsEnum, fragment)))
            }
        }

        return settingsList
    }

    private fun buildAction(
        enum: SettingsEnum,
        fragment: SettingsFragment
    ): (() -> Unit)? {
        val presenter = fragment.presenter

        return when (enum) {
            SettingsEnum.CLEAR_CACHE -> {
                { presenter.clearCache() }
            }
            SettingsEnum.EXIT -> {
                { (fragment.parentFragment as? SettingsFragment.Host)?.logout() }
            }
        }
    }

    private fun enumToItem(context: Context, enum: SettingsEnum, action: (() -> Unit)?): SettingsItem {
        return with(enum) {
            SettingsItem(
                id = id,
                header = context.getString(header),
                description = context.getString(description),
                action = action
            )
        }
    }
}