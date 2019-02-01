package com.project.mobile_university.presentation.login.view.fragment

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.project.iosephknecht.viper.view.AbstractFragment
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.application.annotations.Refactor
import com.project.mobile_university.data.presentation.ServerConfig
import com.project.mobile_university.databinding.FragmentLoginBinding
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import com.project.mobile_university.presentation.login.contract.LoginContract
import com.project.mobile_university.presentation.login.view.dialog.ChangeServerDialog
import com.project.mobile_university.presentation.login.view.dialog.OnChangeServerDialog
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_login.*
import java.util.concurrent.TimeUnit

class LoginFragment : AbstractFragment<LoginContract.Presenter>(), OnChangeServerDialog {
    private lateinit var diComponent: LoginComponent
    private lateinit var binding: FragmentLoginBinding

    private lateinit var chooseServerDialog: DialogFragment

    private val compositeSubscription = CompositeDisposable()

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
        binding.viewModel = presenter

        chooseServerDialog = ChangeServerDialog.newInstance(OnChangeServerDelegate())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_enter.setOnClickListener { presenter.tryLogin() }
    }

    override fun onStart() {
        super.onStart()

        btn_choose.setOnClickListener {
            chooseServerDialog.show(childFragmentManager, ChangeServerDialog.TAG)
        }

        observeViewModel(presenter)

        compositeSubscription.add(login.afterTextChangeEvents()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.login.postValue(it.editable?.toString())
            })

        compositeSubscription.add(password.afterTextChangeEvents()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe {
                presenter.password.postValue(it.editable?.toString())
            })
    }

    override fun onStop() {
        compositeSubscription.clear()
        super.onStop()
    }

    override fun onChangeServerDialog(serverConfig: ServerConfig) {
        presenter.onChangeServerConfig(serverConfig)
    }

    @Refactor("handle error")
    private fun observeViewModel(viewModel: LoginContract.ObservableStorage) {
        viewModel.state.observe({ lifecycle }) {
            when (it) {
                LoginContract.State.ERROR_AUTHORIZE -> {
                    Toast.makeText(context, "Authorization error", Toast.LENGTH_LONG).show()
                }
                LoginContract.State.FAILED_AUTHORIZE -> {
                    Toast.makeText(context, "Wrong data", Toast.LENGTH_LONG).show()
                }
                else -> {
                }
            }
        }
    }

    class OnChangeServerDelegate : OnChangeServerDialog.ChangeServerDialogProvider {
        companion object {
            private val serialVersionUid = 1984641761180270359L
        }

        override fun getListener(fragment: Fragment) = fragment as OnChangeServerDialog
    }
}