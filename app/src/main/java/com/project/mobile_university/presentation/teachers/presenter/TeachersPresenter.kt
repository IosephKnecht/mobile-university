package com.project.mobile_university.presentation.teachers.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.common.helpers.pagination.Paginator
import com.project.mobile_university.presentation.renotify
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import com.project.mobile_university.presentation.teachers.view.adapter.SwipeAction
import java.util.*

class TeachersPresenter(private val interactor: TeachersContract.Interactor) : AbstractPresenter(),
    TeachersContract.Presenter,
    TeachersContract.Listener,
    Paginator.ViewController<Teacher> {

    override val pageProgress = MutableLiveData<Boolean>()
    override val refreshProgress = MutableLiveData<Boolean>()
    override val emptyError = MutableLiveData<Boolean>()
    override val emptyView = MutableLiveData<Boolean>()
    override val emptyProgress = MutableLiveData<Boolean>()
    override val errorMessage = SingleLiveData<Throwable>()
    override val showData = MutableLiveData<List<Teacher>>()


    override val showProfile = SingleLiveData<Pair<Long, Boolean>>()
    override val showScheduleRange = SingleLiveData<Triple<Long, Date, Date>>()

    private val paginator = Paginator(
        requestFactory = { page -> interactor.getRequestFactory(page) },
        viewController = this
    )

    init {
        paginator.refresh()
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
        interactor.setListener(null)
    }

    override fun loadNewPage() {
        paginator.loadNewPage()
    }

    override fun refreshAllPage() {
        showData.value = null
        paginator.restart()
    }

    override fun handleSwipeAction(position: Int, swipeAction: SwipeAction) {
        when (swipeAction) {
            SwipeAction.PROFILE -> {
                showData.value?.get(position)?.let {
                    interactor.checkUser(it.id)
                }
            }
            SwipeAction.SCHEDULE_RANGE -> {
                showData.value?.get(position)?.let { teacher ->
                    val (startDate, endDate) = buildDateRange(7)
                    showScheduleRange.value = Triple(teacher.teacherId, startDate, endDate)
                }
            }
        }
    }

    override fun onCheckUser(data: Pair<Long, Boolean>?, throwable: Throwable?) {
        when {
            data != null -> {
                showProfile.value = data
            }
            throwable != null -> {
                this.errorMessage.value = throwable
            }
        }
    }

    private fun buildDateRange(offset: Int): Pair<Date, Date> {
        val (monday, sunday) = CalendarUtil.obtainMondayAndSunday(Date())
        val sundayWithOffset = Calendar.getInstance().run {
            time = sunday
            add(Calendar.DATE, offset)
            this.time
        }

        return Pair(monday, sundayWithOffset)
    }

    // region ViewController callbacks
    override fun showEmptyProgress(show: Boolean) {
        emptyProgress.value = show
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        emptyError.value = show
        refreshProgress.value = false
    }

    override fun showEmptyView(show: Boolean) {
        emptyView.value = show
        refreshProgress.value = false
    }

    override fun showData(show: Boolean, data: List<Teacher>) {
        if (show) {
            showData.value = data
        } else {
            refreshProgress.value = show
        }
    }

    override fun showErrorMessage(error: Throwable) {
        errorMessage.value = error
        refreshProgress.value = false
    }

    override fun showRefreshProgress(show: Boolean) {
        refreshProgress.value = show
    }

    override fun showPageProgress(show: Boolean) {
        pageProgress.value = show
        if (!show) showData.renotify()
    }
    // endregion

    override fun onDestroy() {
        paginator.release()
        interactor.onDestroy()
    }
}