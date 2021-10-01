package com.example.usetrademarket.data.api.fcm

import com.example.usetrademarket.data.preference.AppPreferenceManager
import java.util.concurrent.Semaphore

class NotificationId(
    private val appPreferenceManager: AppPreferenceManager
) {

    private val lock = Semaphore(1)
    private var id = appPreferenceManager.getNotificationId()

    fun generate() : Int {
        lock.acquire()
        val next = id + 1
        id = next
        appPreferenceManager.setNotificationId(next)
        lock.release()
        return next
    }
}