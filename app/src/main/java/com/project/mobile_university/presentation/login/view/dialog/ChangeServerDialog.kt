package com.project.mobile_university.presentation.login.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import com.afollestad.materialdialogs.MaterialDialog
import com.project.mobile_university.R
import com.project.mobile_university.application.annotations.Refactor
import com.project.mobile_university.data.presentation.Protocol
import com.project.mobile_university.data.presentation.ServerConfig
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern

class ChangeServerDialog : DialogFragment() {
    companion object {
        private val DELEGATE_KEY = "delegate_key"

        const val TAG = "choose_server_dialog"
        fun newInstance(delegate: OnChangeServerDialog.ChangeServerDialogProvider) =
            ChangeServerDialog().apply {
                arguments = Bundle().apply {
                    putSerializable(DELEGATE_KEY, delegate)
                }
            }
    }

    private val DEFAULT_PORT_VALUE = 8000

    private var serverList: ListView? = null
    private var secureCheckBox: CheckBox? = null
    private var serverUrl: EditText? = null
    private var customServerLayout: View? = null

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
                delegate.getListener(parentFragment!!).onChangeServerDialog(buildServerConfig())
            }
            .onNegative { dialog, _ -> dialog.dismiss() }

        val root = materialDialog.build()

        initViews(root)

        return root
    }

    private fun initViews(materialDialog: MaterialDialog) {
        val servers = context!!.resources.getStringArray(R.array.server_url_array)
        val arrayAdapter = ArrayAdapter<String>(context!!, R.layout.simple_list_item, servers)

        with(materialDialog.customView) {
            serverList = this?.run {
                findViewById<ListView>(R.id.url_list)?.apply {
                    choiceMode = ListView.CHOICE_MODE_SINGLE
                    adapter = arrayAdapter
                }
            }
            secureCheckBox = this?.run {
                findViewById(R.id.chk_secure)
            }
            serverUrl = this?.run {
                findViewById(R.id.server_url)
            }
            customServerLayout = this?.run {
                findViewById(R.id.custom_server_layout)
            }
        }

        serverList?.setOnItemClickListener { _, view, position, _ ->
            view.isSelected = true
            serverList?.setItemChecked(position, true)
            if (position == arrayAdapter.count - 1) {
                secureCheckBox?.isChecked = false
                customServerLayout?.visibility = View.VISIBLE
            } else {
                customServerLayout?.visibility = View.GONE
            }
        }
    }

    private fun buildServerConfig(): ServerConfig {
        val protocol = if (secureCheckBox?.isChecked == true)
            Protocol.HTTPS else Protocol.HTTP

        val selectedItemPosition = serverList?.checkedItemPosition ?: 0
        val count = serverList!!.count - 1

        val serviceName = if (selectedItemPosition < count) {
            Pair(serverList!!.adapter.getItem(selectedItemPosition) as String, null)
        } else {
            val text = serverUrl?.text.toString()
            checkStringOnUrl(text)
        }

        return ServerConfig(protocol,
            serviceName?.first ?: "",
            serviceName?.second ?: DEFAULT_PORT_VALUE)
    }

    @Refactor("boilerplate code, think about another regex")
    private fun checkStringOnUrl(text: String?): Pair<String?, Int?>? {
        if (text == null || text.trim().isEmpty()) {
            return null
        }

        val httpsPattern = Pattern.compile("(https?://)*([^:^/]*)(:\\\\d*)*?(.*)?")
        val httpPattern = Pattern.compile("(http?://)*([^:^/]*)(:\\\\d*)*?(.*)?")

        val httpMatcher = httpPattern.matcher(text)
        val httpsMatcher = httpsPattern.matcher(text)

        if (httpMatcher.find()) {
            return parseServiceNameAndPort(httpMatcher)
        }

        if (httpsMatcher.find()) {
            return parseServiceNameAndPort(httpsMatcher)
        }

        return null
    }

    private fun parseServiceNameAndPort(matcher: Matcher): Pair<String?, Int?> {
        val serviceName = try {
            matcher.group(2)
        } catch (e: Exception) {
            null
        }
        val port = try {
            matcher.group(4)?.substring(1)
                ?.toInt()
        } catch (e: Exception) {
            null
        }

        return Pair(serviceName, port)
    }
}