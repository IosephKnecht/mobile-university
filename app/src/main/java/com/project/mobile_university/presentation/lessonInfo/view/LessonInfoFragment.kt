package com.project.mobile_university.presentation.lessonInfo.view

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
import com.project.mobile_university.databinding.FragmentLessonInfoBinding
import com.project.mobile_university.presentation.common.FragmentBackPressed
import com.project.mobile_university.presentation.lessonInfo.assembly.LessonInfoComponent
import com.project.mobile_university.presentation.lessonInfo.contract.LessonInfoContract
import com.project.mobile_university.presentation.lessonInfo.view.adapter.SubgroupAdapter
import es.dmoral.toasty.Toasty

class LessonInfoFragment : AbstractFragment<LessonInfoContract.Presenter>(), FragmentBackPressed {

    companion object {
        private const val LESSON_EXT_KEY = "lesson_ext_key"

        fun createInstance(lessonExtId: Long) = LessonInfoFragment().apply {
            arguments = Bundle().apply {
                putLong(LESSON_EXT_KEY, lessonExtId)
            }
        }
    }

    private lateinit var diComponent: LessonInfoComponent
    private lateinit var binding: FragmentLessonInfoBinding
    private lateinit var adapter: SubgroupAdapter

    override fun inject() {
        val lessonExtId =
            arguments?.getLong(LESSON_EXT_KEY) ?: throw RuntimeException("lesson ext id could not be null")

        diComponent = AppDelegate.presentationComponent
            .lessonInfoSubComponent()
            .with(this)
            .lessonExtId(lessonExtId)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lessonInfo = presenter
        binding.setLifecycleOwner(viewLifecycleOwner)

        adapter = SubgroupAdapter()

        binding.subgroupList.apply {
            adapter = this@LessonInfoFragment.adapter
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                this.setDrawable(ContextCompat.getDrawable(context, R.drawable.ic_divider)!!)
            })
        }


        binding.lessonInfoRefresh.setOnRefreshListener {
            presenter.obtainLessonFromOnline()
            binding.lessonInfoRefresh.isRefreshing = true
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            lesson.observe(viewLifecycleOwner, Observer { lesson ->
                lesson?.let {
                    adapter.reload(it.subgroupList)
                }
                binding.lessonInfoRefresh.isRefreshing = false
            })

            errorObserver.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    context?.let { Toasty.error(it, throwable.localizedMessage).show() }
                    binding.lessonInfoRefresh.isRefreshing = false
                }
            })
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}