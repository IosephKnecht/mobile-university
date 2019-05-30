package com.project.mobile_university.presentation.user_info.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.AdditionalModel
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class AdditionalAdapter : ViewModelAdapter() {
    init {
        cell<AdditionalModel>(R.layout.item_additional_info, BR.additionalModel)
    }
}