package com.project.mobile_university.presentation.schedule.host.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.gson.User
import com.project.mobile_university.domain.services.ScheduleSyncService
import com.project.mobile_university.domain.shared.LoginRepository
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.InitialScreenType
import com.project.mobile_university.presentation.schedule.host.contract.ScheduleHostContract.CurrentScreenType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.SerialDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ScheduleHostPresenter(
    override val identifier: Long,
    override val initialScreenType: InitialScreenType,
    private val interactor: ScheduleHostContract.Interactor,
    private val router: ScheduleHostContract.Router,
    private val loginRepository: LoginRepository
) : AbstractPresenter(), ScheduleHostContract.Presenter, ScheduleHostContract.Listener,
    ScheduleHostContract.RouterListener {

    override val dateChange = MutableLiveData<String>()
    override val currentScreen = MutableLiveData<CurrentScreenType>()

    private val loginDisposable = SerialDisposable()

    init {
        dateChange.value = CalendarUtil.convertToSimpleFormat(Date())
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
        router.setListener(this)

        if (currentScreen.value == null) {
            when (initialScreenType) {
                InitialScreenType.SUBGROUP -> {
                    router.showSubgroupScreen(androidComponent, identifier)
                }
                InitialScreenType.TEACHER -> {
                    router.showTeacherScreen(androidComponent, identifier)
                }
            }
        }

        loginDisposable.set(
            loginRepository.loginState
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isLogin ->
                    if (isLogin) {
                        ScheduleSyncService.startService(androidComponent.activityComponent!!.applicationContext)
                    } else {
                        ScheduleSyncService.stopService(androidComponent.activityComponent!!.applicationContext)
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                })
        )
    }

    override fun detachAndroidComponent() {
        interactor.setListener(null)
        router.setListener(null)

        super.detachAndroidComponent()
    }

    override fun showProfile() {
        interactor.obtainUserProfile()
    }

    override fun onObtainUserProfile(userProfile: User?, throwable: Throwable?) {
        when {
            userProfile != null -> {
                androidComponent?.let {
                    router.showUserInfo(
                        androidComponent = it,
                        userId = userProfile.userId,
                        isMe = true
                    )
                }
            }
            throwable != null -> {
                throwable.printStackTrace()
            }
        }
    }

    override fun onDateChange(date: Date) {
        dateChange.value = CalendarUtil.convertToSimpleFormat(date)
    }

    override fun onShowSubgroupSchedule(identifier: Long) {
        router.showSubgroupScreen(androidComponent!!, identifier)
    }

    override fun onShowTeacherSchedule(identifier: Long) {
        router.showTeacherScreen(androidComponent!!, identifier)
    }

    override fun onShowSettings() {
        router.showSettingsScreen(androidComponent!!)
    }

    override fun onChangeScreen(currentScreenType: CurrentScreenType) {
        this.currentScreen.value = currentScreenType
    }

    override fun onShowLessonInfo(lessonExtId: Long) {
        router.showLessonInfoStudent(androidComponent!!, lessonExtId)
    }

    override fun onEditLessonInfo(lessonExtId: Long) {
        router.showLessonInfoTeacher(androidComponent!!, lessonExtId)
    }

    override fun onShowCheckList(checkListExtId: Long) {
        router.showCheckList(androidComponent!!, checkListExtId)
    }

    override fun onShowTeachersScreen() {
        router.showTeachersScreen(androidComponent!!)
    }

    override fun onShowUserInfo(userId: Long, isMe: Boolean) {
        router.showUserInfo(androidComponent!!, userId, isMe)
    }

    override fun onShowScheduleRange(teacherId: Long, startDate: Date, endDate: Date) {
        router.showScheduleRange(androidComponent!!, teacherId, startDate, endDate)
    }

    override fun backPressed() {
        router.onBackPressed(androidComponent!!)
    }

    override fun restoreDefaultDate(): Calendar {
        return Calendar.getInstance().apply {
            time = dateChange.value?.run {
                CalendarUtil.parseFromString(this)
            } ?: Date()
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
        loginDisposable.dispose()
    }
}