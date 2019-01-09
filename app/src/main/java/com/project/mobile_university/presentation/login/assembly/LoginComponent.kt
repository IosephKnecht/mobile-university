package com.project.mobile_university.presentation.login.assembly

import com.project.mobile_university.presentation.PerFeatureLayerScope
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
@PerFeatureLayerScope
interface LoginComponent {
}