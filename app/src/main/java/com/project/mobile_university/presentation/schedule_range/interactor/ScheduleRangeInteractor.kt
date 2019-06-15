package com.project.mobile_university.presentation.schedule_range.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ScheduleRangeInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<ScheduleRangeContract.Listener>(),
    ScheduleRangeContract.Interactor {

    companion object {
        private const val PAGE_LIMIT = 20
    }

    override fun obtainScheduleList(
        startDate: Date,
        endDate: Date,
        teacherId: Long,
        page: Int
    ): Single<List<ScheduleDay>> {
        return scheduleRepository.getScheduleDaysForTeacher(
            startDate = startDate,
            endDate = endDate,
            teacherId = teacherId,
            limit = PAGE_LIMIT,
            offset = PAGE_LIMIT * (page - 1)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}