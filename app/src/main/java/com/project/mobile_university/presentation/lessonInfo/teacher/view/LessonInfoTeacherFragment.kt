package com.project.mobile_university.presentation.lessonInfo.teacher.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.databinding.FragmentLessonInfoTeacherBinding
import com.project.mobile_university.presentation.lessonInfo.teacher.assembly.LessonInfoTeacherComponent
import com.project.mobile_university.presentation.lessonInfo.teacher.contract.LessonInfoTeacherContract

class LessonInfoTeacherFragment : AbstractFragment<LessonInfoTeacherContract.Presenter>() {

    companion object {
        private const val LESSON_EXT_KEY = "lesson_ext_key"

        fun createInstance(lessonExtId: Long) = LessonInfoTeacherFragment().apply {
            arguments = Bundle().apply {
                putLong(LESSON_EXT_KEY, lessonExtId)
            }
        }
    }

    private lateinit var diComponent: LessonInfoTeacherComponent
    private lateinit var binding: FragmentLessonInfoTeacherBinding

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

        binding.lessonInfo = presenter
        binding.setLifecycleOwner(viewLifecycleOwner)
    }
}