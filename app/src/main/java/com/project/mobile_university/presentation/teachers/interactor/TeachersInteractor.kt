package com.project.mobile_university.presentation.teachers.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.common.helpers.pagination.Paginator
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeachersInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<TeachersContract.Listener>(),
    TeachersContract.Interactor {

    private val TEACHERS_LIMIT = 20

    private var paginator: Paginator<Teacher>? = null

    override fun getRequestFactory(page: Int): Single<List<Teacher>> {
        return scheduleRepository.getTeachers(
            limit = TEACHERS_LIMIT,
            offset = TEACHERS_LIMIT * (page - 1)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onDestroy() {
        super.onDestroy()
        paginator?.release()
    }
}