package com.project.mobile_university.presentation.schedule.teacher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.observe
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.domain.utils.CalendarUtil
import com.project.mobile_university.presentation.schedule.teacher.assembly.TeacherScheduleComponent
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.view.adapter.TeacherScheduleAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_teacher_schedule.*

class TeacherScheduleFragment : AbstractFragment<TeacherScheduleContract.Presenter>() {

    companion object {
        private const val TEACHER_ID_KEY = "teacher_id"

        fun createInstance(teacherId: Long) = TeacherScheduleFragment().apply {
            arguments = Bundle().apply {
                putLong(TEACHER_ID_KEY, teacherId)
            }
        }
    }

    private lateinit var adapter: TeacherScheduleAdapter
    private lateinit var diComponent: TeacherScheduleComponent
    private var teacherId: Long = -1L

    override fun inject() {
        teacherId = arguments!!.getLong(TEACHER_ID_KEY, -1L)

        diComponent = AppDelegate.presentationComponent
            .teacherScheduleSubComponent()
            .with(this)
            .teacherId(teacherId)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teacher_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lesson_list.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            this.adapter = this@TeacherScheduleFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }

        schedule_swipe_layout.setOnRefreshListener {
            presenter.obtainScheduleDayList(teacherId)
        }
    }

    override fun onStart() {
        super.onStart()

        presenter.scheduleDayList.observe(this) {
            if (it != null) {
                adapter.scheduleDayList = it
                adapter.notifyDataSetChanged()
                schedule_swipe_layout.isRefreshing = false
            }
        }

        presenter.errorObserver.observe(this) {
            if (it != null) {
                Toasty.error(context!!, it, Toast.LENGTH_LONG).show()
            }
        }

        presenter.dateObserver.observe(this) {
            // TODO: util does not inject in view element
            adapter.currentDate = CalendarUtil.convertToSimpleFormat(it!!)
            adapter.notifyDataSetChanged()
        }
    }
}