package com.project.mobile_university.presentation.login.view

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.ArrayAdapter
import com.afollestad.materialdialogs.MaterialDialog
import com.project.mobile_university.R
import com.project.mobile_university.presentation.common.WrapContentListView

class ChooseServerDialog : DialogFragment() {
    companion object {
        const val TAG = "choose_server_dialog"
        fun newInstance() = ChooseServerDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val materialDialog = MaterialDialog.Builder(activity!!)
            .title("Select server")
            .positiveText(android.R.string.ok)
            .negativeText(android.R.string.cancel)
            .customView(R.layout.fragment_choose_server_dialog, false)

        val root = materialDialog.build()

        initViews(root)

        return root
    }

    private fun initViews(materialDialog: MaterialDialog) {
        val servers = listOf("1", "2", "3", "4")
        val arrayAdapter = ArrayAdapter<String>(context!!, R.layout.simple_list_item, servers)

        materialDialog.customView?.apply {
            val urlList = findViewById<WrapContentListView>(R.id.url_list)
            urlList?.adapter = arrayAdapter
        }
    }
}