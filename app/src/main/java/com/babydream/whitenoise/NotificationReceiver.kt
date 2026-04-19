package com.babydream.whitenoise

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AudioPlaybackService.ACTION_STOP) {
            val serviceIntent = Intent(context, AudioPlaybackService::class.java).apply {
                action = AudioPlaybackService.ACTION_STOP
            }
            context.startService(serviceIntent)
        }
    }
}