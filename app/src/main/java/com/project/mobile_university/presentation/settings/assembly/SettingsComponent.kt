package com.project.mobile_university.presentation.settings.assembly

import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.settings.contract.SettingsContract
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SettingsModule::class])
@PerFeatureLayerScope
interface SettingsComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(androidComponent: AndroidComponent): Builder

        fun build(): SettingsComponent
    }

    fun getPresenter(): SettingsContract.Presenter
}
