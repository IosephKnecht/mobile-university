package com.project.mobile_university.presentation.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.mobile_university.R
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import org.greenrobot.eventbus.EventBus
import java.util.*

class CommonScheduleActivity : AppCompatActivity() {
    private lateinit var calendar: HorizontalCalendar

    enum class ScheduleEnum {
        SUBGROUP, TEACHER
    }

    companion object {
        private const val SCHEDULE_TYPE = "schedule_type"
        private const val IDENTIFIER = "identifier"

        fun createInstance(context: Context, identifier: Long, type: ScheduleEnum): Intent {
            return Intent(context, CommonScheduleActivity::class.java).apply {
                putExtra(SCHEDULE_TYPE, type.name)
                putExtra(IDENTIFIER, identifier)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val (scheduleType, identifier) = obtainParams()
        val tag = matchScheduleFragmentTag(scheduleType)

        supportFragmentManager.findFragmentByTag(tag)
            .takeIf { it == null }
            .let {
                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.schedule_fragment_container,
                        matchScheduleFragment(identifier, scheduleType)!!,
                        tag
                    )
                    .commit()
            }

        initCalendar()
    }

    private fun obtainParams(): Pair<ScheduleEnum, Long> {
        return intent.run {
            Pair(
                ScheduleEnum.valueOf(getStringExtra(SCHEDULE_TYPE)),
                getLongExtra(IDENTIFIER, -1L)
            )
        }
    }

    private fun matchScheduleFragmentTag(type: ScheduleEnum): String {
        return when (type) {
            ScheduleEnum.SUBGROUP -> ScheduleFragment.TAG
            else -> ""
        }
    }

    private fun matchScheduleFragment(identifier: Long, type: ScheduleEnum): Fragment? {
        return when (type) {
            ScheduleEnum.SUBGROUP -> ScheduleFragment.createInstance(identifier)
            else -> null
        }
    }

    private fun initCalendar() {
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -3)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 3)

        calendar = HorizontalCalendar.Builder(this, R.id.calendar_view)
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .build()

        calendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                EventBus.getDefault().post(DateChangeEvent(date?.time))
            }
        }
    }

    // TODO: will be transit on presentation data layer
    class DateChangeEvent(val date: Date?)
}