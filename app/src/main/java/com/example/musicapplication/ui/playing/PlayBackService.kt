package com.example.musicapplication.ui.playing

import androidx.media3.common.AudioAttributes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlayBackService : MediaSessionService() {
    private lateinit var mediaSession: MediaSession
    private lateinit var listener: Player.Listener

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onCreate() {
        super.onCreate()
        initSeasonAndPlayer()
    }

    override fun onDestroy() {
        mediaSession.player.release()
        mediaSession.release()
        super.onDestroy()
    }

    private fun initSeasonAndPlayer() {
       val player = ExoPlayer.Builder(this)
           .setAudioAttributes(AudioAttributes.DEFAULT, true)
           .build()
        val builder = MediaSession.Builder(this,player)
        mediaSession = builder.build()
    }
}