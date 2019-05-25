package com.project.mobile_university.presentation.check_list

import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.CheckListRecord
import com.project.mobile_university.data.presentation.CheckListStatus
import com.project.mobile_university.presentation.check_list.view.adapter.CheckListAdapter

@BindingAdapter("lesson_status_adapter")
fun Spinner.setCheckListStatusAdapter(viewState: CheckListAdapter.RecordViewState) {
    val statusList = CheckListStatus.values().map { status ->
        context.getString(status.description)
    }

    adapter = ArrayAdapter<String>(
        context,
        R.layout.simple_spinner_item,
        statusList
    ).apply {
        setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
    }

    val changedStatus = viewState.changedStatus
    val oldStatus = viewState.record.status

    setSelection(changedStatus?.value ?: oldStatus.value)
}

@BindingAdapter("student_info")
fun TextView.setStudentInfo(record: CheckListRecord) {
    text = "${record.studentFirstName} ${record.studentLastName}"
}