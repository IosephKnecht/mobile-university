package com.project.mobile_university.domain.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.content.ContextCompat
import com.project.mobile_university.BuildConfig
import com.project.mobile_university.R
import com.project.mobile_university.application.AppDelegate
import com.project.mobile_university.data.presentation.ScheduleDay
import android.app.NotificationManager
import com.project.mobile_university.presentation.MainActivity


class ScheduleSyncService : IntentService(TAG) {
    companion object {
        val TAG = ScheduleSyncService::class.java.simpleName
        private const val FOREGROUND_ID = 79128
        private const val notificationChannelId = BuildConfig.APPLICATION_ID
        private const val notificationChannelName = "schedule_sync_service"
        private const val NOTIFICATION_DIFFERENCE_ID = 16513
        private const val NOTIFICATION_DIFFERENCE_MAIN_ACTIVITY_REQUEST_CODE = 5053

        fun startService(context: Context) {
            ContextCompat.startForegroundService(context, createIntent(context))
        }

        fun stopService(context: Context) {
            context.stopService(createIntent(context))
        }

        private fun createIntent(context: Context) =
            Intent(context, ScheduleSyncService::class.java)
    }

    // 10 minutes
    private val SYNC_INTERVAL = 60 * 1000 * 10

    override fun onCreate() {
        startForeground()

        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            val difference = AppDelegate.businessComponent
                .scheduleRepository()
                .syncSchedule()
                .blockingGet()

            val notificationManager = (getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)

            notificationManager?.cancel(NOTIFICATION_DIFFERENCE_ID)

            if (difference.isNotEmpty()) {
                notificationManager?.let { notifyOfDifference(it, difference) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        setAlarm()
        stopForeground(true)
        super.onDestroy()
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = createIntent(this)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + SYNC_INTERVAL,
            PendingIntent.getService(this, 0, intent, 0)
        )
    }

    private fun startForeground() {
        val channelId = buildNotificationChannelId()

        val notificationBuilder = NotificationCompat.Builder(this, channelId)

        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ic_schedule)
            .setColor(ContextCompat.getColor(this, R.color.primary_dark))
            .setColorized(true)
            .setPriority(PRIORITY_MIN)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setContentTitle(getString(R.string.schedule_sync_foreground_title))
            .setProgress(100, 0, true)
            .build()

        startForeground(FOREGROUND_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }

    private fun buildNotificationChannelId(): String {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                createNotificationChannel(notificationChannelId, notificationChannelName)
            }
            else -> {
                ""
            }
        }
    }

    private fun notifyOfDifference(notificationManager: NotificationManager, scheduleDays: List<ScheduleDay>) {
        val channelId = buildNotificationChannelId()

        val differenceString = scheduleDays.joinToString { scheduleDay -> "${scheduleDay.currentDate} " }

        val notification = NotificationCompat.Builder(this, channelId)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_schedule)
            .setColorized(true)
            .setColor(ContextCompat.getColor(this, R.color.accent))
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentTitle(getString(R.string.schedule_sync_service_title_difference))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    NOTIFICATION_DIFFERENCE_MAIN_ACTIVITY_REQUEST_CODE,
                    Intent(
                        this,
                        MainActivity::class.java
                    ),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    getString(
                        R.string.schedule_sync_service_content_difference,
                        differenceString
                    )
                )
            )
            .build()

        notificationManager.notify(NOTIFICATION_DIFFERENCE_ID, notification)
    }
}