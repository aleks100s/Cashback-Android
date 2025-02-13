package com.alextos.cashback.core.data.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alextos.cashback.R

class MyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Создаем уведомление
        val notificationId = 1
        val builder = NotificationCompat.Builder(context, "monthly_channel")
            .setContentTitle(context.getString(R.string.monthly_notification_title))
            .setContentText(context.getString(R.string.monthly_notification_subtitle))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificationId, builder.build())

        // Планируем следующий запуск уведомления
        MonthlyNotificationScheduler.scheduleNextNotification(context)
    }
}