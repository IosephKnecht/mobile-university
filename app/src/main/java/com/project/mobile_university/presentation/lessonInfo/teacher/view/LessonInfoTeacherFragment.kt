package com.project.mobile_university.presentation.lessonInfo.teacher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.databinding.FragmentLessonInfoTeacherBinding
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.common.ui.PlaceHolderView
import com.project.mobile_university.presentation.lessonInfo.teacher.assembly.LessonInfoTeacherComponent
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract
import com.project.mobile_university.presentation.lessonInfo.teacher.view.adapter.TeacherSubgroupAdapter
import com.project.mobile_university.presentation.schedule.host.view.ScheduleHostListener
import com.project.mobile_university.presentation.visible

class LessonInfoTeacherFragment : AbstractFragment<LessonInfoTeacherContract.Presenter>() {

    interface Host : ScheduleHostListener

    companion object {
        const val TAG = "lesson_info_teacher_fragment"
        private const val LESSON_EXT_KEY = "lesson_ext_key"

        fun createInstance(lessonExtId: Long) = LessonInfoTeacherFragment().apply {
            arguments = Bundle().apply {
                putLong(LESSON_EXT_KEY, lessonExtId)
            }
        }
    }

    private lateinit var diComponent: LessonInfoTeacherComponent
    private lateinit var binding: FragmentLessonInfoTeacherBinding
    private lateinit var adapter: TeacherSubgroupAdapter

    override fun inject() {
        val lessonExtId = arguments?.getLong(LESSON_EXT_KEY) ?: throw RuntimeException("lessonExtId could not be null")

        diComponent = AppDelegate.presentationComponent
            .lessonInfoTeacherSubComponent()
            .lessonExtId(lessonExtId)
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_info_teacher, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeacherSubgroupAdapter()

        with(binding) {
            lessonInfo = presenter
            setLifecycleOwner(viewLifecycleOwner)

            subgroupList.apply {
                adapter = this@LessonInfoTeacherFragment.adapter
                setHasFixedSize(false)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                    this.setDrawable(ContextCompat.getDrawable(context, R.drawable.ic_divider)!!)
                })
            }

            lessonInfoRefresh.setOnRefreshListener {
                presenter.obtainLessonFromOnline()
            }

            geoTag.setOnClickListener {
                presenter.showLocation()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            lesson.observe(viewLifecycleOwner, Observer { lesson ->
                lesson?.let {
                    assignClickListenerOnCheckListButton(lesson.checkListExtId)
                    adapter.reload(it.subgroupList)
                }
            })

            errorObserver.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    showPlaceHolder(
                        PlaceHolderView.State.Error(
                            content = context!!.getString(R.string.lesson_info_error_string),
                            iconRes = R.drawable.ic_placeholder_error
                        )
                    )
                } else {
                    hidePlaceHolder()
                }
            })

            loadingStateLesson.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    binding.lessonInfoRefresh.isRefreshing = isLoading
                }
            })

            loadingStateCheckList.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    binding.checkListButton.visible(!isLoading)
                    binding.checkListProgressBar.visible(isLoading)
                }
            })
        }
    }

    private fun assignClickListenerOnCheckListButton(checkListExtId: Long?) {
        val clickListener = if (checkListExtId == null) {
            View.OnClickListener { presenter.createCheckList() }
        } else {
            View.OnClickListener {
                (parentFragment as? Host)?.showCheckList(checkListExtId)
            }
        }

        binding.checkListButton.setOnClickListener(clickListener)
    }

    private fun showPlaceHolder(state: PlaceHolderView.State) {
        with(binding) {
            contentContainer.visible(false)
            placeHolder.visible(true)
            placeHolder.setState(state)
        }
    }

    private fun hidePlaceHolder() {
        with(binding) {
            contentContainer.visible(true)
            placeHolder.visible(false)
        }
    }
}