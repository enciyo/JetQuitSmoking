package com.enciyo.jetquitsmoking.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import com.enciyo.data.SessionAlarmManager
import com.enciyo.jetquitsmoking.R
import com.enciyo.shared.epochSeconds
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionAlarmManagerImp @Inject constructor(
    @ApplicationContext private val context: Context,
    private val alarmManager: AlarmManager,
) : SessionAlarmManager {

    override operator fun invoke(
        taskId: Int,
        time: LocalDateTime,
        smokeCount: Int,
    ) {
        val triggerAtMillis = time.epochSeconds

        val intent = NotificationReceiver.getIntent(
            context,
            context.getString(R.string.reminder),
            context.getString(R.string.you_can_smoke, smokeCount)
        )
        val operation =
            PendingIntent.getBroadcast(
                context,
                taskId,
                intent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
            )

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            operation
        )
    }
}

