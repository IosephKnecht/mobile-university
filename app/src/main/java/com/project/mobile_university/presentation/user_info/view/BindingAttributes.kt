package com.project.mobile_university.presentation.user_info.view

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.mobile_university.R
import com.project.mobile_university.data.presentation.Gender
import com.project.mobile_university.data.presentation.UserInformation
import com.project.mobile_university.presentation.common.ui.ViewModelAdapter

@BindingAdapter("setName")
fun TextView.setName(firstName: String?) {
    text = firstName.takeIf { it.isNullOrBlank() }
        ?.run {
            context.getString(R.string.user_info_blank_first_name)
        } ?: firstName
}

@BindingAdapter("setLastName")
fun TextView.setLastName(lastName: String?) {
    text = lastName.takeIf { it.isNullOrBlank() }
        ?.run {
            context.getString(R.string.user_info_blank_last_name)
        } ?: lastName
}

@BindingAdapter("setUserPhoto")
fun ImageView.setUserPhoto(userInformation: UserInformation?) {
    userInformation?.let {
        val icon = when {
            it.isStudent -> {
                when (userInformation.gender) {
                    Gender.MALE -> R.drawable.student_male
                    Gender.FEMALE -> R.drawable.student_female
                    else -> null
                }
            }
            it.isTeacher -> {
                when (userInformation.gender) {
                    Gender.MALE -> R.drawable.student_male
                    Gender.FEMALE  -> R.drawable.student_female
                    else -> null
                }
            }
            it.isUndefined -> {
                when (userInformation.gender) {
                    Gender.MALE -> R.drawable.student_male
                    Gender.FEMALE -> R.drawable.student_female
                    else -> null
                }
            }
            else -> null
        }

        icon?.let { setImageDrawable(ContextCompat.getDrawable(context, it)) }
    }
}

@BindingAdapter("setAdditionalInfo")
fun RecyclerView.setAdditionalInfo(userInformation: UserInformation?) {
    userInformation?.let { userInformation ->
        userInformation.additionalList.takeIf {
            it.isNotEmpty()
        }?.let { (adapter as? ViewModelAdapter)?.reload(it) }
    }
}

@BindingAdapter("contactsVisibility")
fun RelativeLayout.contactsBlockVisible(userInformation: UserInformation?) {
    val isVisible = userInformation?.userContacts?.isNotEmpty() ?: false
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("additionalVisibility")
fun RelativeLayout.additionalBlockVisible(userInformation: UserInformation?) {
    val isVisible = userInformation?.additionalList?.isNotEmpty() ?: false
    visibility = if (isVisible) View.VISIBLE else View.GONE
}