package com.project.mobile_university.presentation.schedule.screen.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.view.AbstractActivity
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.data.presentation.Events
import com.project.mobile_university.presentation.schedule.screen.assembly.CommonScheduleComponent
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.activity_common_schedule.*
import org.greenrobot.eventbus.EventBus
import com.project.mobile_university.presentation.schedule.screen.contract.CommonScheduleContract.ScreenState
import java.util.*

class CommonScheduleActivity : AbstractActivity<CommonScheduleContract.Presenter>() {
    private lateinit var calendar: HorizontalCalendar
    private lateinit var diComponent: CommonScheduleComponent

    companion object {
        private const val SCREEN_TYPE = "screen_type"
        private const val IDENTIFIER = "identifier"

        fun createInstance(context: Context, identifier: Long, type: ScreenState): Intent {
            return Intent(context, CommonScheduleActivity::class.java).apply {
                putExtra(SCREEN_TYPE, type.name)
                putExtra(IDENTIFIER, identifier)
            }
        }
    }

    override fun inject() {
        val screenState = intent.getStringExtra(SCREEN_TYPE)
        val identifier = intent.getLongExtra(IDENTIFIER, -1L)

        diComponent = AppDelegate.presentationComponent
            .commonScheduleSubComponent()
            .with(this)
            .screenState(ScreenState.valueOf(screenState))
            .identifier(identifier)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_schedule)

        initCalendar()
        initBottomNavigationPanel()
    }

    override fun onStart() {
        super.onStart()
        initObservers()
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
                EventBus.getDefault().post(Events.DateChangeEvent(date?.time))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        // FIXME: bad solve
        val screenState = intent.getStringExtra(SCREEN_TYPE)?.run {
            ScreenState.valueOf(this)
        }

        if (menu != null) {
            val scheduleTitle = when (screenState) {
                ScreenState.SUBGROUP -> activityComponent.resources.getString(R.string.teacher_schedule_string)
                ScreenState.TEACHER -> activityComponent.resources.getString(R.string.student_schedule_string)
                else -> null
            }

            val scheduleItem = menu.findItem(R.id.schedule_item)

            scheduleTitle?.let { scheduleItem.title = it }
        }

        return true
    }

    private fun initBottomNavigationPanel() {
        // FIXME: bad solve
        val screenState = intent.getStringExtra(SCREEN_TYPE)?.run {
            ScreenState.valueOf(this)
        }

        val identifier = intent.getLongExtra(IDENTIFIER, -1L)

        bottom_navigation.setOnNavigationItemSelectedListener listener@{
            when (it.itemId) {
                R.id.schedule_item -> {
                    if (screenState == ScreenState.TEACHER) presenter.obtainTeacherScreen(identifier)
                    if (screenState == ScreenState.SUBGROUP) presenter.obtainSubgroupScreen(identifier)
                }
                R.id.setting_item -> {
                    presenter.obtainSettingsScreen()
                }
                else -> {
                }
            }
            return@listener true
        }
    }

    private fun initObservers() {
        presenter.currentScreenState.observe(this) {
            when (it) {
                ScreenState.SETTINGS -> {
                    calendar.calendarView.visibility = View.GONE
                }
                else -> {
                    calendar.calendarView.visibility = View.VISIBLE
                }
            }
        }
    }
}