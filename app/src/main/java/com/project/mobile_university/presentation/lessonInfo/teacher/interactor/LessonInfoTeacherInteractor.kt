package com.project.mobile_university.presentation.lessonInfo.teacher.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract

class LessonInfoTeacherInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<LessonInfoTeacherContract.Listener>(), LessonInfoTeacherContract.Interactor {


    override fun onDestroy() {
        super.onDestroy()
    }
}