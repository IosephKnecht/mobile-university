package com.project.mobile_university.presentation.schedule.host.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.navigation.NavigationView
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.lessonInfo.teacher.view.LessonInfoTeacherFragment
import com.project.mobile_university.presentation.reset
import com.project.mobile_university.presentation.schedule.host.assembly.ScheduleHostComponent
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.InitialScreenType
import com.project.mobile_university.presentation.schedule.subgroup.view.ScheduleSubgroupFragment
import com.project.mobile_university.presentation.schedule.teacher.view.TeacherScheduleFragment
import com.project.mobile_university.presentation.schedule_range.view.ScheduleRangeFragment
import com.project.mobile_university.presentation.settings.view.SettingsFragment
import com.project.mobile_university.presentation.teachers.view.TeachersFragment
import com.project.mobile_university.presentation.visible
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.android.synthetic.main.activity_common_schedule.bottom_navigation
import kotlinx.android.synthetic.main.fragment_schedule_host.*
import java.util.*

class ScheduleHostFragment : AbstractFragment<ScheduleHostContract.Presenter>(), FragmentBackPressed,
    NavigationView.OnNavigationItemSelectedListener,
    ScheduleSubgroupFragment.Host,
    TeacherScheduleFragment.Host,
    LessonInfoTeacherFragment.Host,
    TeachersFragment.Host,
    ScheduleRangeFragment.Host,
    SettingsFragment.Host {

    private lateinit var diComponent: ScheduleHostComponent
    private lateinit var calendar: HorizontalCalendar

    private var logoutWarningDialog: MaterialDialog? = null

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

        navigation_view?.setNavigationItemSelectedListener(this)

        with(presenter) {
            currentScreen.observe(viewLifecycleOwner, Observer { currentScreen ->
                when (currentScreen) {
                    ScheduleHostContract.CurrentScreenType.SETTINGS,
                    ScheduleHostContract.CurrentScreenType.LESSON_INFO,
                    ScheduleHostContract.CurrentScreenType.CHECK_LIST -> {
                        calendar.calendarView.visibility = View.GONE
                        bottom_navigation?.visible(true)
                    }
                    ScheduleHostContract.CurrentScreenType.TEACHER,
                    ScheduleHostContract.CurrentScreenType.SUBGROUP -> {
                        calendar.calendarView.visible(true)
                        bottom_navigation?.visible(true)
                        navigation_view?.reset()
                    }
                    ScheduleHostContract.CurrentScreenType.TEACHERS_SCREEN,
                    ScheduleHostContract.CurrentScreenType.USER_INFO,
                    ScheduleHostContract.CurrentScreenType.SCHEDULE_RANGE -> {
                        calendar.calendarView?.visible(false)
                        bottom_navigation?.visible(false)
                    }
                    else -> {

                    }
                }
            })
        }
    }

    override fun showLessonInfo(lessonExtId: Long) {
        presenter.onShowLessonInfo(lessonExtId)
    }

    override fun editLessonInfo(lessonExtId: Long) {
        presenter.onEditLessonInfo(lessonExtId)
    }

    override fun showCheckList(checkListExtId: Long) {
        presenter.onShowCheckList(checkListExtId)
    }

    override fun showUserInfo(userId: Long, isMe: Boolean) {
        presenter.onShowUserInfo(userId, isMe)
    }

    override fun showScheduleRange(teacherId: Long, startDate: Date, endDate: Date) {
        presenter.onShowScheduleRange(teacherId, startDate, endDate)
    }

    override fun logout() {
        context?.let {
            if (logoutWarningDialog == null) {
                logoutWarningDialog = buildWarningLogoutDialog(it)
            }
            logoutWarningDialog?.show()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val isLogout = menuItem.itemId == R.id.logout

        when (menuItem.itemId) {
            R.id.teacher_schedule -> {
                presenter.onShowTeachersScreen()
            }
            R.id.profile -> {
                presenter.showProfile()
            }
            R.id.logout -> {
                logout()
            }
            else -> {

            }
        }
        drawer?.closeDrawers()

        return !isLogout
    }

    override fun onBackPressed(): Boolean {
        return if (childFragmentManager.backStackEntryCount == 0) {
            true
        } else {
            presenter.backPressed()
            false
        }
    }

    private fun initCalendar() {
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -3)

        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 3)

        calendar = HorizontalCalendar.Builder(activity, R.id.calendar_view)
            .range(startDate, endDate)
            .datesNumberOnScreen(7)
            .defaultSelectedDate(presenter.restoreDefaultDate())
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

    private fun buildWarningLogoutDialog(context: Context): MaterialDialog {
        return MaterialDialog.Builder(context)
            .content(R.string.logout_warning_string)
            .negativeText(android.R.string.ok)
            .positiveText(android.R.string.cancel)
            .onNegative { _, _ ->
                presenter.logout()
            }
            .onPositive { dialog, _ ->
                dialog.dismiss()
            }
            .cancelable(false)
            .build()
    }
}