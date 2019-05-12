package com.project.mobile_university.presentation.check_list

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.project.mobile_university.data.presentation.CheckListStatus
import com.project.mobile_university.presentation.check_list.view.adapter.CheckListAdapter

@BindingAdapter("lesson_status_adapter")
fun Spinner.setCheckListStatusAdapter(viewState: CheckListAdapter.RecordViewState) {
    val statusList = CheckListStatus.values().map { status ->
        context.getString(status.description)
    }

    adapter = ArrayAdapter<String>(
        context,
        android.R.layout.simple_spinner_item,
        statusList
    ).apply {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        setSelection(viewState.record.status.value)
    }
}