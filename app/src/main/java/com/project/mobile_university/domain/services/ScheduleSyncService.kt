package com.project.mobile_university.domain.services

import android.app.AlarmManager
import android.app.IntentService
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat
import com.project.mobile_university.BuildConfig

class ScheduleSyncService : IntentService(TAG) {
    companion object {
        val TAG = ScheduleSyncService::class.java.simpleName
        private const val FOREGROUND_ID = 79128
        private const val notificationChannel = BuildConfig.APPLICATION_ID

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
        if (Build.VERSION.SDK_INT > 25) {
            startForeground(FOREGROUND_ID, Notification.Builder(this, notificationChannel)
                .build())
        }

        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        // TODO: will be add functional for sync schedule
    }

    override fun onDestroy() {
        setAlarm()
        stopForeground(true)
        super.onDestroy()
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = createIntent(this)

        alarmManager.set(AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + SYNC_INTERVAL,
            PendingIntent.getService(this, 0, intent, 0))
    }
}