package com.project.mobile_university.presentation.settings.assembly

import androidx.fragment.app.Fragment
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
        fun with(fragment: Fragment): Builder

        fun build(): SettingsComponent
    }

    fun getPresenter(): SettingsContract.Presenter
}
