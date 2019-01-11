package com.project.mobile_university.presentation.login.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.ArrayAdapter
import android.widget.ListView
import com.afollestad.materialdialogs.MaterialDialog
import com.project.mobile_university.R
import com.project.mobile_university.presentation.common.WrapContentListView

class ChangeServerDialog : DialogFragment() {
    companion object {
        const val SERVER_CONFIG_KEY = "server_config_key"
        private val DELEGATE_KEY = "delegate_key"

        const val TAG = "choose_server_dialog"
        fun newInstance(delegate: OnChangeServerDialog.ChangeServerDialogProvider) =
            ChangeServerDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(DELEGATE_KEY, delegate)
                }
            }
    }

    private var currentPosition = -1
    private var serverList: ListView? = null
    private lateinit var delegate: OnChangeServerDialog.ChangeServerDialogProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate = arguments?.getSerializable(DELEGATE_KEY) as OnChangeServerDialog.ChangeServerDialogProvider
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val materialDialog = MaterialDialog.Builder(activity!!)
            .title("Select server")
            .positiveText(android.R.string.ok)
            .negativeText(android.R.string.cancel)
            .customView(R.layout.fragment_choose_server_dialog, false)
            .onPositive { _, _ ->
                delegate.getListener(this).onChangeServerDialog()
            }
            .onNegative { dialog, _ -> dialog.dismiss() }

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

        serverList = materialDialog.customView?.run {
            findViewById<ListView>(R.id.url_list)?.apply {
                choiceMode = ListView.CHOICE_MODE_SINGLE
            }
        }

        serverList?.setOnItemClickListener { parent, view, position, id ->
            view.isSelected = true
            if (currentPosition == position) {
                if (serverList!!.isItemChecked(currentPosition)) {
                    serverList!!.setItemChecked(position, false)
                } else {
                    serverList!!.setItemChecked(position, true)
                }
            } else {
                serverList!!.setItemChecked(position, true)
            }
            currentPosition = position
        }
    }
}