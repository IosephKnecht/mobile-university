package com.project.mobile_university.presentation.common.helpers.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.functions.Function


class PaginationTool<T> constructor(
    private val recyclerView: RecyclerView,
    private val limit: Int,
    private val emptyListCount: Int,
    private val retryCount: Int,
    private val emptyListCountPlusToOffset: Boolean,
    private val pagingListener: (Int) -> Observable<T>
) {
    fun getPagingObservable(): Observable<T> {
        val startNumberOfRetryAttempt = 0

        return getScrollObservable(recyclerView, limit, emptyListCount)
            .subscribeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            .switchMap { offset ->
                getPagingObservable(
                    pagingListener,
                    pagingListener.invoke(offset),
                    startNumberOfRetryAttempt,
                    offset,
                    retryCount
                )
            }
    }

    private fun getScrollObservable(
        recyclerView: RecyclerView,
        limit: Int,
        emptyListCount: Int
    ): Observable<Int> {
        return Observable.create { emitter ->
            val scrollListener = object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!emitter.isDisposed) {
                        val position = recyclerView.getLastVisibleItemPosition()!!
                        val updatedPosition = recyclerView.itemCount() - 1 - (limit / 2)

                        if (position >= updatedPosition) {
                            val offset =
                                if (emptyListCountPlusToOffset) recyclerView.itemCount() else recyclerView.itemCount() - emptyListCount

                            emitter.onNext(offset)
                        }
                    }
                }
            }

            recyclerView.addOnScrollListener(scrollListener)

            emitter.setDisposable(Disposables.fromAction { recyclerView.removeOnScrollListener(scrollListener) })

            if (recyclerView.adapter!!.itemCount == emptyListCount) {
                val offset =
                    if (emptyListCountPlusToOffset) recyclerView.itemCount() else recyclerView.itemCount() - emptyListCount
                emitter.onNext(offset)
            }
        }
    }

    private fun getPagingObservable(
        pagingListener: (Int) -> Observable<T>,
        observable: Observable<T>,
        numberOfAttemptToRetry: Int,
        offset: Int,
        retryCount: Int
    ): Observable<T> {

        return observable.onErrorResumeNext(
            Function<Throwable, Observable<T>> {
                if (numberOfAttemptToRetry < retryCount) {
                    val attemptToRetryInc = numberOfAttemptToRetry + 1
                    return@Function getPagingObservable(
                        pagingListener,
                        pagingListener.invoke(offset),
                        attemptToRetryInc,
                        offset,
                        retryCount
                    )
                } else {
                    return@Function Observable.empty()
                }
            })
    }


    private fun RecyclerView.getLastVisibleItemPosition(): Int? {
        val recyclerViewLayoutManagerClass = layoutManager!!.javaClass

        return when {
            recyclerViewLayoutManagerClass == LinearLayoutManager::javaClass ||
                LinearLayoutManager::class.java.isAssignableFrom(recyclerViewLayoutManagerClass) -> {
                (layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
            }

            recyclerViewLayoutManagerClass == StaggeredGridLayoutManager::javaClass ||
                StaggeredGridLayoutManager::class.java.isAssignableFrom(recyclerViewLayoutManagerClass) -> {
                (layoutManager as? StaggeredGridLayoutManager)?.run {
                    this.findLastVisibleItemPositions(null)?.max()
                }
            }
            else -> null
        }
    }

    private fun RecyclerView.itemCount() = this.adapter!!.itemCount
}