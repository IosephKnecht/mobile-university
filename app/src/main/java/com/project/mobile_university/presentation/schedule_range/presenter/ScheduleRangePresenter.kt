package com.project.mobile_university.presentation.schedule_range.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.presentation.common.helpers.pagination.Paginator
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.renotify
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import com.project.mobile_university.presentation.schedule_range.view.adapter.ScheduleDayViewState
import java.util.*

class ScheduleRangePresenter(
    private val interactor: ScheduleRangeContract.Interactor,
    private val router: ScheduleRangeContract.Router,
    private val startDate: Date,
    private val endDate: Date,
    private val teacherId: Long
) : AbstractPresenter(),
    ScheduleRangeContract.Presenter,
    ScheduleRangeContract.Listener,
    ScheduleRangeContract.RouterListener,
    Paginator.ViewController<ScheduleDayViewState> {

    private val paginator = Paginator(
        requestFactory = { page ->
            interactor.obtainScheduleList(
                startDate = startDate,
                endDate = endDate,
                teacherId = teacherId,
                page = page
            ).map { scheduleDaysPresentation ->
                mapToViewState(scheduleDaysPresentation)
            }
        },
        viewController = this
    )

    override val pageProgress = MutableLiveData<Boolean>()
    override val refreshProgress = MutableLiveData<Boolean>()
    override val emptyError = MutableLiveData<Boolean>()
    override val emptyView = MutableLiveData<Boolean>()
    override val emptyProgress = MutableLiveData<Boolean>()
    override val errorMessage = SingleLiveData<Throwable>()
    override val showData = MutableLiveData<List<ScheduleDayViewState>>()

    init {
        paginator.refresh()
    }

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)

        interactor.setListener(this)
        router.setListener(this)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()

        interactor.setListener(null)
        router.setListener(null)
    }

    override fun loadNewPage() {
        paginator.loadNewPage()
    }

    override fun refreshAllPage() {
        showData.value = null
        paginator.restart()
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

    override fun showData(show: Boolean, data: List<ScheduleDayViewState>) {
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

    private fun mapToViewState(scheduleDays: List<ScheduleDay>): List<ScheduleDayViewState> {
        val viewStates = mutableListOf<ScheduleDayViewState>()

        scheduleDays.forEach { scheduleDay ->
            viewStates.add(
                ScheduleDayViewState.Header(scheduleDay.currentDate)
            )

            scheduleDay.lessons.forEach { lesson ->
                viewStates.add(
                    ScheduleDayViewState.Lesson(
                        lesson = lesson,
                        clickListener = {}
                    )
                )
            }
        }

        return viewStates
    }

    override fun onDestroy() {
        paginator.release()
        interactor.onDestroy()
    }
}