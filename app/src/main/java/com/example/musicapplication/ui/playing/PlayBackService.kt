package com.example.musicapplication.ui.playing

import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.musicapplication.ui.viewmodel.SharedViewModel

class PlaybackService : MediaSessionService() {
    private lateinit var mediaSession: MediaSession
    private lateinit var listener: Player.Listener

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onCreate() {
        super.onCreate()
        initSessionAndPlayer()
        setupListener()
    }

    override fun onDestroy() {
        mediaSession.player.removeListener(listener)
        mediaSession.player.release()
        mediaSession.release()
        super.onDestroy()
    }

    private fun initSessionAndPlayer() {
        val player = ExoPlayer.Builder(this)
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .build()
        val builder = MediaSession.Builder(this, player)
        mediaSession = builder.build()
    }

    private fun setupListener() {
        val player = mediaSession.player
        listener = object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                SharedViewModel.instance.setPlayingSong(player.currentMediaItemIndex)
            }
        }
        player.addListener(listener)
    }
}