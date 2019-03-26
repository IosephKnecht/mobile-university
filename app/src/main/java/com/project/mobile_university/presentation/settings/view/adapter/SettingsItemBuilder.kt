package com.project.mobile_university.presentation.settings.view.adapter

import android.content.Context
import androidx.annotation.StringRes
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.SettingsItem

enum class SettingsEnum(val id: Int,
                        @StringRes val header: Int,
                        @StringRes val description: Int) {
    CLEAR_CACHE(1, R.string.clear_cache_primary_text,
        R.string.clear_cache_description),

    EXIT(2, R.string.exit_primary_text,
        R.string.exit_description),
}

object SettingsItemBuilder {
    fun buildList(context: Context): List<SettingsItem> {
        val settingsList = mutableListOf<SettingsItem>()

        SettingsEnum.values().forEach {
            settingsList.add(enumToItem(context, it))
        }

        return settingsList
    }

    private fun enumToItem(context: Context, enum: SettingsEnum): SettingsItem {
        return with(enum) {
            SettingsItem(id, context.getString(header),
                context.getString(description))
        }
    }
}