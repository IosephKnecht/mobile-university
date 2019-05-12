package com.project.mobile_university.presentation.schedule.check_list.assembly

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.project.mobile_university.domain.shared.ScheduleRepository
import com.project.mobile_university.presentation.PerFeatureLayerScope
import com.project.mobile_university.presentation.schedule.check_list.contract.CheckListContract
import com.project.mobile_university.presentation.schedule.check_list.interactor.CheckListInteractor
import com.project.mobile_university.presentation.schedule.check_list.presenter.CheckListPresenter
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject

@PerFeatureLayerScope
@Subcomponent(modules = [CheckListModule::class])
interface CheckListComponent {

    fun getPresenter(): CheckListContract.Presenter

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun checkListId(checkListId: Long): Builder

        @BindsInstance
        fun with(fragment: Fragment): Builder

        fun build(): CheckListComponent
    }
}


@Module
class CheckListModule {
    @Provides
    @PerFeatureLayerScope
    fun provideInteractor(scheduleRepository: ScheduleRepository): CheckListContract.Interactor {
        return CheckListInteractor(scheduleRepository)
    }

    @Provides
    fun providePresenter(
        fragment: Fragment,
        factory: CheckListViewModelFactory
    ): CheckListContract.Presenter {
        return ViewModelProviders.of(fragment, factory).get(CheckListPresenter::class.java)
    }
}

@PerFeatureLayerScope
class CheckListViewModelFactory @Inject constructor(
    private val checkListId: Long,
    private val interactor: CheckListContract.Interactor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CheckListPresenter(checkListId, interactor) as T
    }
}