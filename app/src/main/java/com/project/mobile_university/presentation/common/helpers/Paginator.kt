package com.project.mobile_university.presentation.common.helpers

import io.reactivex.Single
import io.reactivex.disposables.Disposable

class Paginator<T>(
    private val requestFactory: (Int) -> Single<List<T>>,
    private val viewController: ViewController<T>
) {
    interface ViewController<T> {
        fun showEmptyProgress(show: Boolean)
        fun showEmptyError(show: Boolean, error: Throwable? = null)
        fun showEmptyView(show: Boolean)
        fun showData(show: Boolean, data: List<T> = emptyList())
        fun showErrorMessage(error: Throwable)
        fun showRefreshProgress(show: Boolean)
        fun showPageProgress(show: Boolean)
    }

    private interface State<T> {
        fun restart() {}
        fun refresh() {}
        fun loadNewPage() {}
        fun release() {}
        fun newData(data: List<T>) {}
        fun fail(error: Throwable) {}
    }

    private val FIRST_PAGE = 1

    private var currentState: State<T> = Empty()
    private var currentPage = 0
    private val currentData = mutableListOf<T>()
    private var disposable: Disposable? = null

    fun restart() {
        currentState.restart()
    }

    fun refresh() {
        currentState.refresh()
    }

    fun loadNewPage() {
        currentState.loadNewPage()
    }

    fun release() {
        currentState.release()
    }

    private fun loadPage(page: Int) {
        disposable?.dispose()
        disposable = requestFactory.invoke(page)
            .subscribe(
                { currentState.newData(it) },
                { currentState.fail(it) }
            )
    }


    private inner class Empty : State<T> {
        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class EmptyProgress : State<T> {
        override fun restart() {
            loadPage(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            when {
                data.isNotEmpty() -> {
                    currentState = Data()
                    currentData.clear()
                    currentData.addAll(data)
                    currentPage = FIRST_PAGE
                    viewController.showEmptyProgress(false)
                    viewController.showData(true, currentData)
                }
                else -> {
                    currentState = EmptyData()
                    viewController.showEmptyProgress(false)
                    viewController.showEmptyView(true)
                }
            }
        }

        override fun fail(error: Throwable) {
            currentState = EmptyError()
            viewController.showEmptyProgress(false)
            viewController.showEmptyError(true, error)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()

        }
    }

    private inner class EmptyError : State<T> {
        override fun restart() {
            currentState = EmptyProgress()
            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class EmptyData : State<T> {
        override fun restart() {
            currentState = EmptyProgress()
            viewController.showEmptyView(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyView(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class Data : State<T> {
        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = Refresh()
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun loadNewPage() {
            currentState = PageProgress()
            viewController.showPageProgress(true)
            loadPage(currentPage + 1)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class Refresh : State<T> {
        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showRefreshProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            when {
                data.isNotEmpty() -> {
                    currentState = Data()
                    currentData.clear()
                    currentData.addAll(data)
                    currentPage = FIRST_PAGE
                    viewController.showRefreshProgress(false)
                    viewController.showData(true, currentData)
                }
                else -> {
                    currentState = EmptyData()
                    currentData.clear()
                    viewController.showData(false)
                    viewController.showRefreshProgress(false)
                    viewController.showEmptyView(true)
                }
            }
        }

        override fun fail(error: Throwable) {
            currentState = Data()
            viewController.showRefreshProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class PageProgress : State<T> {
        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showPageProgress(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun newData(data: List<T>) {
            when {
                data.isNotEmpty() -> {
                    currentState = Data()
                    currentData.addAll(data)
                    currentPage++
                    viewController.showPageProgress(false)
                    viewController.showData(true, currentData)
                }
                else -> {
                    currentState = AllData()
                    viewController.showPageProgress(false)
                }
            }
        }

        override fun fail(error: Throwable) {
            currentState = Data()
            viewController.showPageProgress(false)
            viewController.showErrorMessage(error)
        }

        override fun refresh() {
            currentState = Refresh()
            viewController.showPageProgress(false)
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class AllData : State<T> {
        override fun restart() {
            currentState = EmptyProgress()
            viewController.showData(false)
            viewController.showEmptyProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun refresh() {
            currentState = Refresh()
            viewController.showRefreshProgress(true)
            loadPage(FIRST_PAGE)
        }

        override fun release() {
            currentState = Release()
            disposable?.dispose()
        }
    }

    private inner class Release : State<T>
}

