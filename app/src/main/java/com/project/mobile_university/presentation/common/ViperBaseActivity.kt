//package com.project.mobile_university.presentation.common
//
//import android.support.annotation.ColorRes
//import android.support.annotation.StringRes
//import android.support.design.widget.Snackbar
//import android.support.v4.content.ContextCompat
//import android.view.View
//import android.widget.TextView
//import com.project.iosephknecht.viper.presenter.MvpPresenter
//import com.project.iosephknecht.viper.view.AbstractActivity
//import com.project.mobile_university.R
//
//abstract class ViperBaseActivity<P : MvpPresenter> : AbstractActivity<P>() {
//    protected fun showSuccessMessage(message: String, anchorView: View) {
//        showMessage(message, anchorView, R.color.color_green)
//    }
//
//    protected fun showSuccessMessage(@StringRes message: Int, anchorView: View) {
//        val parseMessage = anchorView.context.getString(message)
//        showMessage(parseMessage, anchorView, R.color.color_green)
//    }
//
//    protected fun showErrorMessage(message: String, anchorView: View) {
//        showMessage(message, anchorView, R.color.color_red)
//    }
//
//    protected fun showErrorMessage(@StringRes message: Int, anchorView: View) {
//        val parseMessage = anchorView.context.getString(message)
//        showMessage(parseMessage, anchorView, R.color.color_red)
//    }
//
//    private fun showMessage(message: String, anchorView: View, @ColorRes color: Int = R.color.colorPrimaryText) {
//        val snackbar = Snackbar.make(anchorView, message, Snackbar.LENGTH_LONG)
//
//        snackbar.view.apply {
//            setBackgroundColor(ContextCompat.getColor(context, color))
//            setOnClickListener { snackbar.dismiss() }
//            val snackBarText = findViewById<TextView>(R.id.snackbar_text)
//            snackBarText.maxLines = 4
//        }
//
//        snackbar.show()
//    }
//}