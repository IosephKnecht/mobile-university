package com.project.mobile_university.presentation.teachers.interactor

import com.project.iosephknecht.viper.interacor.AbstractInteractor
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.common.helpers.Paginator
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeachersInteractor(private val scheduleRepository: ScheduleRepository) :
    AbstractInteractor<TeachersContract.Listener>(),
    TeachersContract.Interactor {

    private val TEACHERS_LIMIT = 20

    private var paginator: Paginator<Teacher>? = null

    override fun setListener(listener: TeachersContract.Listener?) {
        super.setListener(listener)

        if (listener != null) {
            paginator = Paginator(
                requestFactory = { page ->
                    scheduleRepository.getTeachers(
                        limit = TEACHERS_LIMIT,
                        offset = TEACHERS_LIMIT * (page - 1)
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                },
                viewController = listener
            )

            paginator?.refresh()
        } else {
            paginator?.release()
            paginator = null
        }
    }

    override fun loadPage() {
        paginator?.loadNewPage()
    }

    override fun refreshAllPage() {
        paginator?.restart()
    }

    override fun onDestroy() {
        super.onDestroy()
        paginator?.release()
    }
}