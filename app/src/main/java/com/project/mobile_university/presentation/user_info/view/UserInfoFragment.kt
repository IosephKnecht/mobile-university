package com.project.mobile_university.presentation.user_info.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.databinding.FragmentUserInfoBinding
import com.project.mobile_university.presentation.user_info.assembly.UserInfoComponent
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import com.project.mobile_university.presentation.user_info.view.adapter.AdditionalAdapter
import com.project.mobile_university.presentation.user_info.view.adapter.ContactsAdapter
import es.dmoral.toasty.Toasty

class UserInfoFragment : AbstractFragment<UserInfoContract.Presenter>() {

    companion object {
        const val TAG = "user_info_fragment"
        private const val USER_ID_KEY = "user_id"
        private const val IS_ME_KEY = "is_me"

        fun createInstance(userId: Long, isMe: Boolean) = UserInfoFragment().apply {
            arguments = Bundle().apply {
                putLong(USER_ID_KEY, userId)
                putBoolean(IS_ME_KEY, isMe)
            }
        }
    }

    private lateinit var diComponent: UserInfoComponent
    private lateinit var binding: FragmentUserInfoBinding
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var additionalAdapter: AdditionalAdapter

    override fun inject() {

        val userId = arguments?.getLong(USER_ID_KEY) ?: throw RuntimeException("user id could not be null")
        val isMe = arguments?.getBoolean(IS_ME_KEY) ?: throw RuntimeException("isMe flag could not be null")

        diComponent = AppDelegate.presentationComponent
            .userInfoSubComponent()
            .with(this)
            .userId(userId)
            .isMe(isMe)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentUserInfoBinding>(
            inflater,
            R.layout.fragment_user_info, container,
            false
        ).apply {
            setLifecycleOwner(viewLifecycleOwner)
            observableStorage = presenter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsAdapter = ContactsAdapter()
        additionalAdapter = AdditionalAdapter()

        with(binding) {
            userContacts.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@UserInfoFragment.contactsAdapter
                setHasFixedSize(false)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            userAdditional.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = this@UserInfoFragment.additionalAdapter
                setHasFixedSize(false)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            refreshLayout.setOnRefreshListener { presenter.obtainUserInfo() }
        }
    }

    override fun onStart() {
        super.onStart()

        with(presenter) {
            throwable.observe(viewLifecycleOwner, Observer { throwable ->
                if (throwable != null) {
                    context?.let { Toasty.error(it, throwable.localizedMessage).show() }
                }
            })

            loading.observe(viewLifecycleOwner, Observer { isLoading ->
                if (isLoading != null) {
                    binding.refreshLayout.isRefreshing = isLoading
                }
            })

            userContacts.observe(viewLifecycleOwner, Observer { contacts ->
                contacts?.let {
                    contactsAdapter.reload(it)
                }
            })
        }
    }
}