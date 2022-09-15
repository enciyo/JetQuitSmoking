package com.enciyo.jetquitsmoking.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import com.enciyo.data.SessionAlarmManager
import com.enciyo.jetquitsmoking.R
import com.enciyo.shared.copy
import com.enciyo.shared.epochSeconds
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.LocalDateTime
import java.time.Instant
import java.util.*
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
        val date = Date.from(Instant.ofEpochSecond(time.copy(second = 0).epochSeconds))
        val intent = NotificationReceiver.getIntent(
            context,
            context.getString(R.string.reminder),
            context.getString(R.string.you_can_smoke, smokeCount)
        )
        val operation =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
            )

        alarmManager.cancel(operation)
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            date.time,
            operation
        )
    }

}

