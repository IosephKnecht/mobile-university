package com.project.mobile_university.presentation.teachers.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.domain.shared.SharedPreferenceService
import com.project.mobile_university.presentation.common.helpers.pagination.Paginator
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TeachersInteractor(
    private val scheduleRepository: ScheduleRepository,
    private val sharedPreferenceService: SharedPreferenceService
) :
    AbstractInteractor<TeachersContract.Listener>(),
    TeachersContract.Interactor {

    private val TEACHERS_LIMIT = 20

    private val compositeDisposable = CompositeDisposable()

    override fun getRequestFactory(page: Int): Single<List<Teacher>> {
        return scheduleRepository.getTeachers(
            limit = TEACHERS_LIMIT,
            offset = TEACHERS_LIMIT * (page - 1)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun checkUser(userId: Long) {
        val source = Single.fromCallable {
            sharedPreferenceService.getUserInfo()
        }.map {
            Pair(userId, it.userId == userId)
        }

        compositeDisposable.add(
            discardResult(source) { listener, result ->
                listener?.onCheckUser(result.data, result.throwable)
            }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}