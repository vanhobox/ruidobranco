package com.babydream.whitenoise

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AudioPlaybackService : Service() {

    private val binder = AudioBinder()
    // Two players are kept alive and chained so the transition is seamless (gapless looping).
    private var playerA: MediaPlayer? = null
    private var playerB: MediaPlayer? = null
    private var wakeLock: PowerManager.WakeLock? = null
    private var currentSoundId: Int? = null
    private var currentRawRes: Int? = null
    private var mediaSession: MediaSessionCompat? = null

    companion object {
        const val CHANNEL_ID = "baby_sounds"
        const val NOTIFICATION_ID = 1
        const val ACTION_STOP = "com.babydream.whitenoise.STOP"
        const val EXTRA_SOUND_NAME = "sound_name"
    }

    inner class AudioBinder : Binder() {
        fun getService(): AudioPlaybackService = this@AudioPlaybackService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaSession = MediaSessionCompat(this, "ThaLemesMediaSession")
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP -> {
                stopPlayback()
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notification_channel_description)
            setShowBadge(false)
            enableVibration(false)
            setSound(null, null)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    /**
     * Creates a fresh, ready-to-use MediaPlayer for [rawRes].
     * Returns null if creation fails.
     */
    private fun buildPlayer(rawRes: Int): MediaPlayer? =
        MediaPlayer.create(this, rawRes)?.also { mp ->
            mp.setOnErrorListener { p, _, _ ->
                p.release()
                true
            }
        }

    fun playSound(soundItem: SoundItem, soundName: String) {
        stopPlayback()

        val rawRes = soundItem.rawRes
        currentRawRes = rawRes

        // Player A starts immediately; Player B is pre-loaded and set as next.
        val pA = buildPlayer(rawRes) ?: return
        val pB = buildPlayer(rawRes) ?: run { pA.release(); return }

        // When A finishes it seamlessly hands off to B, then B loads a fresh A, and so on.
        chainPlayers(pA, pB)

        playerA = pA
        playerB = pB

        pA.start()

        currentSoundId = soundItem.id
        acquireWakeLock()
        startForeground(NOTIFICATION_ID, createNotification(soundName))
    }

    /**
     * Sets up the gapless chain: when [current] completes it hands off to [next],
     * then [next] prepares a new player and chains itself again.
     */
    private fun chainPlayers(current: MediaPlayer, next: MediaPlayer) {
        current.setNextMediaPlayer(next)
        current.setOnCompletionListener { finished ->
            // Release the just-finished player and build a replacement for next's handoff.
            finished.setOnCompletionListener(null)
            finished.release()

            val rawRes = currentRawRes ?: return@setOnCompletionListener
            val replacement = buildPlayer(rawRes) ?: return@setOnCompletionListener

            // Chain next -> replacement so the loop continues forever.
            chainPlayers(next, replacement)

            // Update our references so stopPlayback() can still clean up correctly.
            playerA = next
            playerB = replacement
        }
    }

    fun stopPlayback() {
        playerA?.apply {
            setOnCompletionListener(null)
            if (isPlaying) stop()
            release()
        }
        playerA = null

        playerB?.apply {
            setOnCompletionListener(null)
            if (isPlaying) stop()
            release()
        }
        playerB = null

        currentSoundId = null
        currentRawRes = null
        releaseWakeLock()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    fun getCurrentSoundId(): Int? = currentSoundId

    private fun createNotification(soundName: String): Notification {
        val stopIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val contentIntent = packageManager.getLaunchIntentForPackage(packageName)
        val contentPendingIntent = PendingIntent.getActivity(
            this,
            0,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.now_playing, soundName))
            .setSmallIcon(R.drawable.ic_moon_star)
            .setOngoing(true)
            .setContentIntent(contentPendingIntent)
            .addAction(R.drawable.ic_moon_star, getString(R.string.stop_sound), stopPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun acquireWakeLock() {
        if (wakeLock == null) {
            val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "ThaLemes::AudioWakeLock"
            )
        }
        wakeLock?.acquire(4 * 60 * 60 * 1000L) // 4 hours max
    }

    private fun releaseWakeLock() {
        wakeLock?.let {
            if (it.isHeld) {
                it.release()
            }
        }
        wakeLock = null
    }

    override fun onDestroy() {
        stopPlayback()
        mediaSession?.release()
        super.onDestroy()
    }
}