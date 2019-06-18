package com.project.mobile_university.presentation.user_info.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.UserContactModel
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class ContactsAdapter : ViewModelAdapter() {
    init {
        cell<UserContactViewState>(
            layoutId = R.layout.item_user_contact,
            bindingId = BR.viewState,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.model.contactType == newItem.model.contactType
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem.model == newItem.model
            }
        )
    }
}

class UserContactViewState(
    val model: UserContactModel,
    val clickListener: (() -> Unit)?
)