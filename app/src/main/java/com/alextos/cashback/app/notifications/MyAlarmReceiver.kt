package com.alextos.cashback.app.notifications

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.alextos.cashback.R
import com.alextos.cashback.app.MainActivity

class MyAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Создаем Intent для открытия MainActivity
        val intent = Intent(context, MainActivity::class.java).apply {
            // Флаги помогают обеспечить корректное поведение при открытии Activity
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Оборачиваем Intent в PendingIntent
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Создаем уведомление
        val notificationId = 1
        val builder = NotificationCompat.Builder(context, "monthly_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.monthly_notification_title))
            .setContentText(context.getString(R.string.monthly_notification_subtitle))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

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