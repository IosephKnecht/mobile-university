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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ScheduleSyncService : IntentService(TAG) {
    companion object {
        val TAG = ScheduleSyncService::class.java.simpleName
        private const val FOREGROUND_ID = 79128
        private const val notificationChannelId = BuildConfig.APPLICATION_ID
        private const val notificationChannelName = "schedule_sync_service"

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
    private val SYNC_INTERVAL = 60 * 1000

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

            if (difference.isNotEmpty()) {
                // TODO: send notification
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
        val channelId = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                createNotificationChannel(notificationChannelId, notificationChannelName)
            }
            else -> {
                ""
            }
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.drawable.ic_schedule)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(FOREGROUND_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}