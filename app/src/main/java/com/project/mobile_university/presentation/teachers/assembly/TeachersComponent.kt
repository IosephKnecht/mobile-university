package com.project.mobile_university.presentation.teachers.assembly

import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.teachers.contract.TeachersContract
import dagger.BindsInstance
import dagger.Subcomponent

@PerFeatureLayerScope
@Subcomponent(modules = [TeachersModule::class])
interface TeachersComponent {

    fun getPresenter(): TeachersContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun androidComponent(androidComponent: AndroidComponent): Builder

        fun build(): TeachersComponent
    }
}