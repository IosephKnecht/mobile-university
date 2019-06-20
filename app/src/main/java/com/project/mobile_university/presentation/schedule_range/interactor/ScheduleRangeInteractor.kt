package com.project.mobile_university.presentation.schedule_range.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.gson.Teacher
import com.project.mobile_university.data.presentation.ScheduleDay
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.schedule_range.contract.ScheduleRangeContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ScheduleRangeInteractor(
    private val scheduleRepository: ScheduleRepository,
    private val sharedPreferenceService: SharedPreferenceService
) :
    AbstractInteractor<ScheduleRangeContract.Listener>(),
    ScheduleRangeContract.Interactor {

    companion object {
        private const val PAGE_LIMIT = 20
    }

    private val compositeDisposable = CompositeDisposable()

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

    override fun checkUser(lessonId: Long, teacherId: Long) {
        val observable = Single.fromCallable {
            sharedPreferenceService.getUserInfo()
        }.map { userGson ->
            return@map when (userGson) {
                is Teacher -> {
                    val isOwner = userGson.teacherId == teacherId
                    Pair(lessonId, isOwner)
                }
                else -> Pair(lessonId, false)
            }
        }

        compositeDisposable.add(
            discardResult(observable) { listener, result ->
                listener?.onCheckUser(result.data, result.throwable)
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}