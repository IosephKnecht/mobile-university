package com.project.mobile_university.presentation.lessonInfo.teacher.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import io.reactivex.disposables.CompositeDisposable

class LessonInfoTeacherInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<LessonInfoTeacherContract.Listener>(), LessonInfoTeacherContract.Interactor {

    private val compositeDisposable = CompositeDisposable()

    override fun getLessonFromCache(lessonExtId: Long) {
        compositeDisposable.add(discardResult(scheduleRepository.getLesson(lessonExtId)) { listener, result ->
            listener?.onObtainLesson(result.data, result.throwable)
        })
    }

    override fun getLessonFromOnline(lessonExtId: Long) {
        compositeDisposable.add(
            discardResult(scheduleRepository.syncLesson(lessonExtId)) { listener, result ->
                listener?.onObtainLesson(result.data, result.throwable)
            }
        )
    }

    override fun createCheckList(lessonExtId: Long) {
        compositeDisposable.add(
            discardResult(scheduleRepository.createCheckList(lessonExtId)) { listener, result ->
                listener?.onCreateCheckList(result.data, result.throwable)
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}