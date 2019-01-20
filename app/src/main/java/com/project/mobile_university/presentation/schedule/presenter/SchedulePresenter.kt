package com.project.mobile_university.presentation.schedule.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.gson.Lesson
import com.project.mobile_university.presentation.schedule.contract.ScheduleContract
import java.util.*

class SchedulePresenter(private val interactor: ScheduleContract.Interactor,
                        private val groupId: Long)
    : AbstractPresenter(), ScheduleContract.Presenter, ScheduleContract.Listener {

    override val lessonList = MutableLiveData<List<Lesson>>(arrayListOf())

    private var state = ScheduleContract.State.IDLE

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        registerObservers(lessonList)

        interactor.setListener(this)

        if (state == ScheduleContract.State.IDLE) {
            obtainLessonList(Date(), groupId)
        }
    }

    override fun detachAndroidComponent() {
        unregisterObservers()
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun obtainLessonList(currentDate: Date, groupId: Long) {
        interactor.getLessonList(currentDate, groupId)
    }

    override fun onObtainLessonList(lessonList: List<Lesson>?, throwable: Throwable?) {
        when {
            throwable != null -> {
                //TODO: error handle
            }
            lessonList != null -> {
                this.lessonList.postValue(lessonList)
            }
        }
        state = ScheduleContract.State.INIT
    }
}