package com.project.mobile_university.presentation.user_info.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.user_info.contract.UserInfoContract
import com.project.mobile_university.presentation.user_info.interactor.UserInfoInteractor
import com.project.mobile_university.presentation.user_info.presenter.UserInfoPresenter
import com.project.mobile_university.presentation.user_info.router.UserInfoRouter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

@Subcomponent(modules = [UserInfoModule::class])
@PerFeatureLayerScope
interface UserInfoComponent {

    fun getPresenter(): UserInfoContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(fragment: Fragment): Builder

        @BindsInstance
        fun userId(userId: Long): Builder

        @BindsInstance
        fun isMe(isMe: Boolean): Builder

        fun build(): UserInfoComponent
    }
}

@Module
class UserInfoModule {

    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleRepository: ScheduleRepository): UserInfoContract.Interactor {
        return UserInfoInteractor(scheduleRepository)
    }

    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: UserInfoViewModelFactory
    ): UserInfoContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(UserInfoPresenter::class.java)
    }

    @Provides
    @PerFeatureLayerScope
    fun provideRouter(): UserInfoContract.Router {
        return UserInfoRouter()
    }
}

@PerFeatureLayerScope
class UserInfoViewModelFactory @Inject constructor(
    private val interactor: UserInfoContract.Interactor,
    private val router: UserInfoContract.Router,
    private val userId: Long,
    private val isMe: Boolean
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserInfoPresenter(
            interactor,
            router,
            userId,
            isMe
        ) as T
    }
}