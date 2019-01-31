package com.project.mobile_university.presentation.login.assembly

import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.login.contract.LoginContract
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
@PerFeatureLayerScope
interface LoginComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(androidComponent: AndroidComponent): Builder

        fun build(): LoginComponent
    }

    fun getPresenter(): LoginContract.Presenter
}