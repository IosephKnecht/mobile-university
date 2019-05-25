package com.project.mobile_university.presentation.teachers.presenter

import androidx.lifecycle.MutableLiveData
import com.project.iosephknecht.viper.presenter.AbstractPresenter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.data.presentation.Teacher
import com.project.mobile_university.presentation.common.helpers.SingleLiveData
import com.project.mobile_university.presentation.teachers.contract.TeachersContract

class TeachersPresenter(private val interactor: TeachersContract.Interactor) : AbstractPresenter(),
    TeachersContract.Presenter,
    TeachersContract.Listener {

    override val pageProgress = MutableLiveData<Boolean>()
    override val refreshProgress = MutableLiveData<Boolean>()
    override val emptyError = MutableLiveData<Boolean>()
    override val emptyView = MutableLiveData<Boolean>()
    override val emptyProgress = MutableLiveData<Boolean>()
    override val errorMessage = SingleLiveData<Throwable>()
    override val showData = MutableLiveData<List<Teacher>>()

    override fun attachAndroidComponent(androidComponent: AndroidComponent) {
        super.attachAndroidComponent(androidComponent)
        interactor.setListener(this)
    }

    override fun detachAndroidComponent() {
        super.detachAndroidComponent()
        interactor.setListener(null)
    }

    override fun loadNewPage() {
        interactor.loadPage()
    }

    override fun refreshAllPage() {
        interactor.refreshAllPage()
    }

    // region ViewController callbacks
    override fun showEmptyProgress(show: Boolean) {
        emptyProgress.value = show
    }

    override fun showEmptyError(show: Boolean, error: Throwable?) {
        if (show) {
        }
    }

    override fun showEmptyView(show: Boolean) {
        emptyView.value = show
    }

    override fun showData(show: Boolean, data: List<Teacher>) {
        if (show) {
            showData.value = data
        }
    }

    override fun showErrorMessage(error: Throwable) {
        errorMessage.value = error
    }

    override fun showRefreshProgress(show: Boolean) {
        refreshProgress.value = show
    }

    override fun showPageProgress(show: Boolean) {
        pageProgress.value = show
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    // endregion
}