package com.project.mobile_university.presentation.login.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.widget.RxTextView
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.mobile_university.R
import com.project.mobile_university.mobile_university.databinding.FragmentLoginBinding
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import com.project.mobile_university.presentation.login.contract.LoginContract
import kotlinx.android.synthetic.main.fragment_login.*
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class LoginFragment : AbstractFragment<LoginContract.Presenter>() {
    private lateinit var diComponent: LoginComponent
    private lateinit var binding: FragmentLoginBinding
    private val compositeSubscription = CompositeSubscription()

    companion object {
        const val TAG = "login_fragment"
        fun createInstance() = LoginFragment()
    }

    override fun inject() {
        diComponent = AppDelegate.presentationComponent
            .loginSubComponent()
            .with(this)
            .build()
    }

    override fun providePresenter() = diComponent.getPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = presenter as LoginContract.ObservableStorage
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        compositeSubscription.add(RxTextView.textChanges(service_url)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.saveServiceUrl(it.toString())
            })

        compositeSubscription.add(RxTextView.textChanges(login)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.saveLogin(it.toString())
            })

        compositeSubscription.add(RxTextView.textChanges(password)
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.savePassword(it.toString())
            })
    }

    override fun onStop() {
        compositeSubscription.clear()
        super.onStop()
    }
}