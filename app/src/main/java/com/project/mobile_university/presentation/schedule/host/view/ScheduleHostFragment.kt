package com.project.mobile_university.presentation.schedule.host.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.lessonInfo.view.LessonInfoFragment
import com.project.mobile_university.presentation.schedule.host.assembly.ScheduleHostComponent
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.InitialScreenType
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment
import com.project.mobile_university.presentation.schedule.teacher.view.TeacherScheduleFragment
import com.project.mobile_university.presentation.settings.view.SettingsFragment
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.activity_common_schedule.*
import java.util.*

class ScheduleHostFragment : AbstractFragment<ScheduleHostContract.Presenter>(), FragmentBackPressed,
    ScheduleSubgroupFragment.Host, TeacherScheduleFragment.Host {

    private lateinit var diComponent: ScheduleHostComponent
    private lateinit var calendar: HorizontalCalendar

    companion object {
        private const val SCREEN_TYPE = "screen_type"
        private const val IDENTIFIER = "identifier"

        fun createInstance(identifier: Long, typeInitial: InitialScreenType) = ScheduleHostFragment().apply {
            arguments = Bundle().apply {
                putLong(IDENTIFIER, identifier)
                putString(SCREEN_TYPE, typeInitial.name)
            }
        }
    }

    override val fragmentManagerComponent: FragmentManager?
        get() = childFragmentManager

    override fun inject() {
        val identifier = arguments!!.getLong(IDENTIFIER)
        val screenType = arguments!!.getString(SCREEN_TYPE)?.run {
            InitialScreenType.valueOf(this)
        } ?: throw RuntimeException("Screen type could not be null")

        diComponent = AppDelegate.presentationComponent.scheduleHostSubComponent()
            .with(this)
            .identifier(identifier)
            .screenType(screenType)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule_host, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCalendar()
        initBottomNavigationPanel()

        with(presenter) {
            currentScreen.observe(viewLifecycleOwner, Observer { currentScreen ->
                when (currentScreen) {
                    ScheduleHostContract.CurrentScreenType.SETTINGS,
                    ScheduleHostContract.CurrentScreenType.LESSON_INFO -> {
                        calendar.calendarView.visibility = View.GONE
                    }
                    ScheduleHostContract.CurrentScreenType.TEACHER,
                    ScheduleHostContract.CurrentScreenType.SUBGROUP -> {
                        calendar.calendarView.visibility = View.VISIBLE
                    }
                    else -> {

                    }
                }
            })
        }
    }

    override fun showLessonInfo(lessonId: Long) {
        presenter.onShowLessonInfo(lessonId)
    }

    override fun onBackPressed(): Boolean {
        if (childFragmentManager.backStackEntryCount == 0) {
            return true
        }

        childFragmentManager.findFragmentById(R.id.schedule_fragment_container)?.let { fragment ->
            if (fragment is FragmentBackPressed && fragment.onBackPressed()) {
                childFragmentManager.popBackStackImmediate()
                val currentScreenType = mapFragmentToScreenType(childFragmentManager.fragments.lastOrNull())
                currentScreenType?.let { presenter.onBackPressed(it) }
                return false
            }
        }

        return false
    }

    private fun initCalendar() {
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -3)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 3)

        calendar = HorizontalCalendar.Builder(activity, R.id.calendar_view)
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .build()

        calendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                date?.let {
                    presenter.onDateChange(it.time)
                }
            }
        }
    }

    private fun initBottomNavigationPanel() {
        BottomNavigationBuilder.buildMenu(bottom_navigation.menu, presenter.initialScreenType)

        bottom_navigation.setOnNavigationItemSelectedListener listener@{
            when (it.itemId) {
                BottomNavigationItem.SCHEDULE.itemId -> {
                    presenter.onShowSubgroupSchedule(presenter.identifier)
                }
                BottomNavigationItem.WORK_SCHEDULE.itemId -> {
                    presenter.onShowTeacherSchedule(presenter.identifier)
                }
                BottomNavigationItem.SETTINGS.itemId -> {
                    presenter.onShowSettings()
                }
            }
            return@listener true
        }
    }

    // FIXME: bad solve
    private fun mapFragmentToScreenType(fragment: Fragment?): ScheduleHostContract.CurrentScreenType? {
        return when (fragment) {
            is SettingsFragment -> ScheduleHostContract.CurrentScreenType.SETTINGS
            is TeacherScheduleFragment -> ScheduleHostContract.CurrentScreenType.TEACHER
            is ScheduleSubgroupFragment -> ScheduleHostContract.CurrentScreenType.SUBGROUP
            is LessonInfoFragment -> ScheduleHostContract.CurrentScreenType.LESSON_INFO
            else -> null
        }
    }
}