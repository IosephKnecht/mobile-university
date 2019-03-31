package com.project.mobile_university.presentation.lessonInfo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract

class LessonInfoFragment : AbstractFragment<LessonInfoContract.Presenter>() {
    override fun inject() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun providePresenter(): LessonInfoContract.Presenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lesson_info, container, false)
    }
}