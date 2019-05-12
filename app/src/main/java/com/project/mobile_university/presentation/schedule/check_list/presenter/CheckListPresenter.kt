package com.project.mobile_university.presentation.schedule.check_list.presenter

import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.presentation.schedule.check_list.contract.CheckListContract

class CheckListPresenter(
    private val checkListId: Long,
    private val interactor: CheckListContract.Interactor
) : AbstractPresenter(),
    CheckListContract.Presenter {

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}