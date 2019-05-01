package com.project.mobile_university.presentation.settings.view.adapter

import android.content.Context
import androidx.annotation.StringRes
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.SettingsItem
import com.project.mobile_university.presentation.settings.contract.SettingsContract

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
    fun buildList(context: Context, presenter: SettingsContract.Presenter): List<SettingsItem> {
        val settingsList = mutableListOf<SettingsItem>()

        SettingsEnum.values().forEach { settingsEnum ->
            settingsList.add(enumToItem(context, settingsEnum, buildAction(settingsEnum, presenter)))
        }

        return settingsList
    }

    private fun buildAction(
        enum: SettingsEnum,
        presenter: SettingsContract.Presenter
    ): (() -> Unit)? {
        return when (enum) {
            SettingsEnum.CLEAR_CACHE -> {
                { presenter.clearCache() }
            }
            SettingsEnum.EXIT -> {
                { presenter.exit() }
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