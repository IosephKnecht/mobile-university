package com.project.mobile_university.presentation.schedule.screen.view

import android.view.Menu
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.project.mobile_university.R
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract

enum class BottomNavigationItem(val itemId: Int,
                                @StringRes val title: Int,
                                @DrawableRes
                                val icon: Int) {
    SCHEDULE(1, R.string.teacher_schedule_string, R.drawable.ic_schedule),
    WORK_SCHEDULE(2, R.string.student_schedule_string, R.drawable.ic_schedule),
    SETTINGS(3, R.string.settings_string, R.drawable.ic_settings)
}

object BottomNavigationBuilder {
    fun buildMenu(menu: Menu, screenType: CommonScheduleContract.ScreenState) {
        if (screenType == CommonScheduleContract.ScreenState.SUBGROUP) {
            menu.add(BottomNavigationItem.SCHEDULE)
        } else {
            menu.add(BottomNavigationItem.WORK_SCHEDULE)
        }
        menu.add(BottomNavigationItem.SETTINGS)
    }

    private fun Menu.add(menuItem: BottomNavigationItem) {
        menuItem.apply {
            this@add.add(Menu.NONE, itemId, Menu.NONE, title)
                .setIcon(icon)
        }
    }
}