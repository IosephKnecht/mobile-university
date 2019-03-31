package com.project.mobile_university.presentation.lessonInfo.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import io.reactivex.disposables.CompositeDisposable

class LessonInfoInteractor(private val scheduleRepository: ScheduleRepository) : AbstractInteractor<LessonInfoContract.Listener>(),
    LessonInfoContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getLesson(lessonId: Long, fromCache: Boolean) {
        compositeDisposable.add(discardResult(scheduleRepository.getLesson(lessonId)) { listener, result ->
            listener?.onObtainLessonFromCache(result.data, result.throwable)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}