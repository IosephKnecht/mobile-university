package com.project.mobile_university.presentation.lessonInfo.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import io.reactivex.disposables.CompositeDisposable

class LessonInfoInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<LessonInfoContract.Listener>(),
    LessonInfoContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getLessonFromCache(lessonExtId: Long) {
        compositeDisposable.add(discardResult(scheduleRepository.getLesson(lessonExtId)) { listener, result ->
            listener!!.onObtainLesson(result.data, result.throwable)
        })
    }

    override fun getLessonFromOnline(lessonExtId: Long) {
        compositeDisposable.add(
            discardResult(scheduleRepository.syncLesson(lessonExtId)) { listener, result ->
                listener!!.onObtainLesson(result.data, result.throwable)
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}