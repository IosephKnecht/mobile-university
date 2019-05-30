package com.project.mobile_university.presentation.user_info.view.adapter

import com.project.mobile_university.BR
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.UserContactModel
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

class ContactsAdapter : ViewModelAdapter() {
    init {
        cell<UserContactModel>(R.layout.item_user_contact, BR.userContactModel)
    }
}