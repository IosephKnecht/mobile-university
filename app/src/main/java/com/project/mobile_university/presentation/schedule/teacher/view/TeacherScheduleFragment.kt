package com.project.mobile_university.presentation.schedule.teacher.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.data.presentation.Lesson
import com.project.mobile_university.data.presentation.LessonStatus
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.common.helpers.swipe.SwipeHelper
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostListener
import com.project.mobile_university.presentation.schedule.teacher.assembly.TeacherScheduleComponent
import com.project.mobile_university.presentation.schedule.teacher.contract.TeacherScheduleContract
import com.project.mobile_university.presentation.schedule.teacher.view.adapter.TeacherScheduleAdapter
import com.project.mobile_university.presentation.schedule.teacher.view.adapter.TeacherScheduleSwipeHelper
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_teacher_schedule.*

class TeacherScheduleFragment : AbstractFragment<TeacherScheduleContract.Presenter>(), FragmentBackPressed {

    companion object {
        const val TAG = "teacher_schedule_fragment"
        private const val TEACHER_ID_KEY = "teacher_id"

        fun createInstance(teacherId: Long) = TeacherScheduleFragment().apply {
            arguments = Bundle().apply {
                putLong(TEACHER_ID_KEY, teacherId)
            }
        }
    }

    interface Host : ScheduleHostListener

    private lateinit var adapter: TeacherScheduleAdapter
    private lateinit var diComponent: TeacherScheduleComponent
    private lateinit var swipeHelper: SwipeHelper
    private var teacherId: Long = -1L

    private var lessonStatusWarningDialog: MaterialDialog? = null

    override fun inject() {
        teacherId = arguments!!.getLong(TEACHER_ID_KEY)

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

        swipeHelper = TeacherScheduleSwipeHelper(
            view.context, lesson_list, ItemTouchHelper.LEFT, 100
        ) { position, lessonStatus ->
            val lesson = presenter.getLessonByPosition(position)
            presenter.updateLessonStatus(lesson, lessonStatus, false)
        }

        adapter = TeacherScheduleAdapter { lessonExtId ->
            (parentFragment as Host).showLessonInfo(lessonExtId)
        }

        lesson_list.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            this.adapter = this@TeacherScheduleFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        schedule_swipe_layout.setOnRefreshListener {
            presenter.obtainScheduleDayList(teacherId)
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            errorObserver.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toasty.error(context!!, it, Toast.LENGTH_LONG).show()
                }
            })

            lessonsObserver.observe(viewLifecycleOwner, Observer { lessons ->
                if (lessons != null) {
                    adapter.reload(lessons, schedule_swipe_layout)
                    if (lessons.isEmpty()) {
                        // TODO: show placeholder
                    }
                }
            })

            showWarningDialog.observe(viewLifecycleOwner, Observer { pair ->
                if (pair != null) {
                    context?.let {
                        val (lesson, lessonStatus) = pair
                        showWarningDialog(it, lesson, lessonStatus)
                    }
                }
            })

            cancelWarningDialog.observe(viewLifecycleOwner, Observer { isClosed ->
                if (isClosed == true) {
                    dismissWarningDialog()
                }
            })
        }
    }

    override fun onBackPressed() = true

    private fun showWarningDialog(
        context: Context,
        lesson: Lesson,
        lessonStatus: LessonStatus
    ) {
        if (lessonStatusWarningDialog == null) {
            lessonStatusWarningDialog = MaterialDialog.Builder(context)
                .content(
                    context.getString(R.string.warning_change_lesson_status_string)
                        .format(
                            lesson.lessonStatus?.run { context.getString(description) },
                            lessonStatus.description.run { context.getString(this) }
                        )
                )
                .positiveText(R.string.positive_change_lesson_status_string)
                .negativeText(R.string.negative_text_lesson_status_string)
                .onPositive { _, _ -> presenter.updateLessonStatus(lesson, lessonStatus, true) }
                .onNegative { _, _ -> presenter.cancelUpdateLessonStatus() }
                .cancelable(false)
                .build()
        }

        lessonStatusWarningDialog?.show()
    }

    private fun dismissWarningDialog() {
        lessonStatusWarningDialog?.dismiss()
        lessonStatusWarningDialog = null
    }
}