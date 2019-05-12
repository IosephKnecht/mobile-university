package com.project.mobile_university.presentation.schedule.check_list.view.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.data.presentation.CheckListStatus
import com.project.mobile_university.presentation.common.ui.ViewHolder
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class CheckListAdapter(private val changeStatusListener: (viewState: RecordViewState, status: Int) -> Unit) :
    ViewModelAdapter() {

    init {
        cell<RecordViewState>(R.layout.item_student_check_list, BR.viewState)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val viewState = getViewModel(position) as RecordViewState

        holder.itemView.findViewById<Spinner>(R.id.check_list_status)?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    changeStatusListener.invoke(viewState, position)
                }
            }
    }

    data class RecordViewState(
        var changedStatus: CheckListStatus? = null,
        val record: CheckListRecord
    )
}