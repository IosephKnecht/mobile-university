package com.project.mobile_university.application.assembly

import com.project.mobile_university.application.annotations.PerPresentationLayerScope
import com.project.mobile_university.presentation.login.assembly.LoginComponent
import dagger.Component

@Component(modules = [PresentationModule::class], dependencies = [BusinessComponent::class])
@PerPresentationLayerScope
interface PresentationComponent {
    fun loginSubComponent(): LoginComponent.Builder
}