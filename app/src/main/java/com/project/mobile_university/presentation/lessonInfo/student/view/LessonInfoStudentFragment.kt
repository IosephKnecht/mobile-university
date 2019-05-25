package com.project.mobile_university.presentation.lessonInfo.student.view

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
import com.project.mobile_university.databinding.FragmentLessonInfoStudentBinding
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.common.ui.PlaceHolderView
import com.project.mobile_university.presentation.lessonInfo.student.assembly.LessonInfoStudentComponent
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract
import com.project.mobile_university.presentation.lessonInfo.student.view.adapter.StudentSubgroupAdapter
import com.project.mobile_university.presentation.visible

class LessonInfoStudentFragment : AbstractFragment<LessonInfoStudentContract.Presenter>() {

    companion object {
        const val TAG = "lesson_info_student_fragment"
        private const val LESSON_EXT_KEY = "lesson_ext_key"

        fun createInstance(lessonExtId: Long) = LessonInfoStudentFragment().apply {
            arguments = Bundle().apply {
                putLong(LESSON_EXT_KEY, lessonExtId)
            }
        }
    }

    private lateinit var diComponent: LessonInfoStudentComponent
    private lateinit var binding: FragmentLessonInfoStudentBinding
    private lateinit var adapter: StudentSubgroupAdapter

    override fun inject() {
        val lessonExtId =
            arguments?.getLong(LESSON_EXT_KEY) ?: throw RuntimeException("lesson ext id could not be null")

        diComponent = AppDelegate.presentationComponent
            .lessonInfoStudentSubComponent()
            .with(this)
            .lessonExtId(lessonExtId)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_info_student, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lessonInfo = presenter
        binding.setLifecycleOwner(viewLifecycleOwner)

        adapter = StudentSubgroupAdapter()

        binding.subgroupList.apply {
            adapter = this@LessonInfoStudentFragment.adapter
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                this.setDrawable(ContextCompat.getDrawable(context, R.drawable.ic_divider)!!)
            })
        }


        binding.lessonInfoRefresh.setOnRefreshListener {
            presenter.obtainLessonFromOnline()
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            lesson.observe(viewLifecycleOwner, Observer { lesson ->
                lesson?.let {
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

            loadingState.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    binding.lessonInfoRefresh.isRefreshing = isLoading
                }
            })
        }
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