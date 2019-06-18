package com.project.mobile_university.presentation.lessonInfo.student.router

import android.content.Intent
import android.net.Uri
import com.project.iosephknecht.viper.router.AbstractRouter
import com.project.iosephknecht.viper.view.AndroidComponent
import com.project.mobile_university.R
import com.project.mobile_university.presentation.lessonInfo.student.contract.LessonInfoStudentContract

class LessonInfoStudentRouter : AbstractRouter<LessonInfoStudentContract.RouterListener>(),
    LessonInfoStudentContract.Router {

    override fun showGeoTag(androidComponent: AndroidComponent, coordinates: List<String>, zoom: Int) {
        val joinCoordinates = coordinates.joinToString(separator = ",")

        val googleMapIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:0,0?q=$joinCoordinates()?z=$zoom")
        ).apply {
            `package` = "com.google.android.apps.maps"
        }

        val yandexMapIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("yandexmaps://maps.yandex.ru/?pt=$joinCoordinates&z=$zoom&l=map")
        ).apply {
            `package` = "ru.yandex.yandexmaps"
        }

        androidComponent.activityComponent?.apply {
            val chooserIntent = Intent.createChooser(googleMapIntent, getString(R.string.show_location_select))
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(yandexMapIntent))

            chooserIntent?.resolveActivity(packageManager)?.also {
                startActivity(chooserIntent)
            } ?: routerListener?.onShowGeoTag(Throwable(getString(R.string.show_location_error)))
        }
    }
}