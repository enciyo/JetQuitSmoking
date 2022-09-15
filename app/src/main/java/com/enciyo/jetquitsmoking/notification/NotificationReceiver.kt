package com.enciyo.jetquitsmoking.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.R
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.core.os.bundleOf
import com.enciyo.domain.Repository
import com.enciyo.jetquitsmoking.util.intent
import com.enciyo.shared.ApplicationScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repopsitory: Repository

    @Inject
    @ApplicationScope
    lateinit var scope: CoroutineScope


    companion object {
        private const val NOTIFICATION_ID = 26
        private const val NOTIFICATION_CHANNEL_ID = "261"
        private const val NOTIFICATION_CHANNEL_DESC = "Description"
        private const val NOTIFICATION_CHANNEL_NAME = "Name"
        private const val EXTRA_CONTENT_TITLE = "Extra.Content.Title"
        private const val EXTRA_CONTENT_TEXT = "Extra.Content.Text"

        fun getIntent(context: Context, contentTitle: String, contentText: String): Intent {
            return context.intent<NotificationReceiver>().apply {
                bundleOf(
                    EXTRA_CONTENT_TITLE to contentTitle,
                    EXTRA_CONTENT_TEXT to contentText
                ).let(::putExtras)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val manager = context.getSystemService<NotificationManager>() ?: return
        val contentText = intent.extras?.getString(EXTRA_CONTENT_TEXT) ?: return
        val contentTitle = intent.extras?.getString(EXTRA_CONTENT_TITLE) ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = NOTIFICATION_CHANNEL_DESC
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.drawable.notification_bg)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        manager.notify(NOTIFICATION_ID, notification)
        scope.launch {
            repopsitory.setNextAlarm()
        }
    }
}