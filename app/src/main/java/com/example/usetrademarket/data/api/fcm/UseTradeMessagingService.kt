package com.example.usetrademarket.data.api.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.usetrademarket.R
import com.example.usetrademarket.data.api.UseTradeApiService
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject

class UseTradeMessagingService : FirebaseMessagingService() {

    private val appPreferenceManager : AppPreferenceManager by inject()
    private val useTradeApiService : UseTradeApiService by inject()
    private val notificationId : NotificationId by inject()

    override fun onNewToken(fcmToken: String) {
        appPreferenceManager.setFcmToken(fcmToken)
        if (!appPreferenceManager.getToken().isNullOrEmpty() && fcmToken != null) {
            runBlocking {
                try {
                    val response = useTradeApiService.updateFcmToken(fcmToken)
                    if (!response.success) {
                        println("------------------------------------------")
                        println("${response.message} 토큰 업데이트 실패")
                        println("------------------------------------------")
                    }
                }catch (e:Exception) {
                    println(e.message ?: "토큰 업데이트 실패")
                }
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.data?.let { data ->
            createNotificationChannelIfNeeded()

            val builder = NotificationCompat
                .Builder(this, "channel.parayo.default")
                .setContentTitle(data["title"])
                .setContentText(data["content"])
                .setSmallIcon(R.drawable.ic_lock)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId.generate(), builder.build())
            }
        }
    }

    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel.usetrade.default",
                "기본 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "기본 알림"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}