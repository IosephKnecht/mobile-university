package com.project.mobile_university.presentation.common.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.LoadMoreViewState
import java.util.*

open class ViewModelAdapter : RecyclerView.Adapter<ViewHolder>() {

    interface ListUpdateCallback {
        fun onItemsInsertedOnTop(insertPosition: Int, itemCount: Int)
    }

    private val areEqualItems: (Any, Any) -> Boolean = Any::equals

    private var mCallback: ListUpdateCallback? = null

    protected val items = LinkedList<Any>()

    val cellMap = LinkedHashMap<Class<out Any>, CellInfo>()

    @JvmOverloads
    open fun reload(newItems: List<Any>, refreshLayout: SwipeRefreshLayout? = null) {
        val diffCallback = DiffCallback(
            items,
            newItems,
            checkAreItemsTheSame = { oldItem: Any, newItem: Any ->
                getCellInfo(oldItem).checkAreItemsTheSame.invoke(oldItem, newItem)
            },
            checkAreContentsTheSame = { oldItem: Any, newItem: Any ->
                getCellInfo(oldItem).checkAreContentsTheSame.invoke(oldItem, newItem)
            })
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(object : androidx.recyclerview.widget.ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                notifyItemRangeInserted(position, count)
                mCallback?.onItemsInsertedOnTop(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                notifyItemRangeRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                notifyItemRangeChanged(position, count, payload)
            }
        })
        refreshLayout?.isRefreshing = false
    }

    fun setListUpdateCallback(callback: ListUpdateCallback?) {
        mCallback = callback
    }

    @JvmOverloads
    inline fun <reified T : Any> cell(
        @LayoutRes layoutId: Int,
        bindingId: Int = BR.viewModel,
        noinline areItemsTheSame: (T, T) -> Boolean = { a: T, b: T -> a == b },
        noinline areContentsTheSame: (T, T) -> Boolean = { a: T, b: T -> a == b }
    ) {
        @Suppress("UNCHECKED_CAST")
        val cellInfo = CellInfo(
            layoutId,
            bindingId,
            areItemsTheSame as (Any, Any) -> Boolean,
            areContentsTheSame as (Any, Any) -> Boolean
        )
        cellMap[T::class.java] = cellInfo
    }

    protected fun getViewModel(position: Int) = items[position]

    open fun showLoadMoreProgress() {
        if (items.lastOrNull() !is LoadMoreViewState) {
            cell<LoadMoreViewState>(R.layout.item_load_more)
            items.add(LoadMoreViewState)
            notifyItemInserted(itemCount - 1)
        }
    }

    private fun getCellInfo(viewModel: Any): CellInfo {
        cellMap.entries
            .find { it.key == viewModel.javaClass }
            ?.apply { return value }

        cellMap.entries
            .find { it.key.isInstance(viewModel) }
            ?.apply {
                cellMap[viewModel.javaClass] = value
                return value
            }

        val message = "Cell info for class ${viewModel.javaClass.name} not found."
        throw Exception(message)
    }

    private fun onBind(
        binding: ViewDataBinding,
        cellInfo: CellInfo,
        position: Int
    ) {

        val viewModel = getViewModel(position)
        if (cellInfo.bindingId != 0) {
            binding.setVariable(cellInfo.bindingId, viewModel)
            binding.executePendingBindings()
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return getCellInfo(getViewModel(position)).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)

        return ViewHolder(view.rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellInfo = getCellInfo(getViewModel(position))
        onBind(holder.binding, cellInfo, position)
    }

    @Suppress("unused")
    fun getViewModelType(itemPosition: Int): Class<out Any> {
        return items[itemPosition]::class.java
    }
}

data class CellInfo(
    val layoutId: Int,
    val bindingId: Int,
    val checkAreItemsTheSame: (Any, Any) -> Boolean,
    val checkAreContentsTheSame: (Any, Any) -> Boolean
)

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding: ViewDataBinding = DataBindingUtil.bind(view)!!
}