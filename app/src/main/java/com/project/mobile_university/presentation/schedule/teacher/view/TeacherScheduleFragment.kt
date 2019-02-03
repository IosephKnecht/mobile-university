package com.project.mobile_university.presentation.schedule.teacher.view

import android.os.Bundle
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract

class TeacherScheduleFragment : AbstractFragment<TeacherScheduleContract.Presenter>() {

    companion object {
        private const val TEACHER_ID_KEY = "teacher_id"

        fun createInstance(teacherId: Long) = TeacherScheduleFragment().apply {
            arguments = Bundle().apply {
                putLong(TEACHER_ID_KEY, teacherId)
            }
        }
    }

    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun providePresenter(): TeacherScheduleContract.Presenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        super.onStart()

        presenter.scheduleDayList.observe(this) {

        }

        presenter.errorObserver.observe(this) {

        }
    }
}