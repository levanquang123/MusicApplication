package com.example.musicapplication.ui.playing

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.ui.viewmodel.SharedViewModel

class PlaybackService : MediaSessionService() {
    private lateinit var mediaSession: MediaSession
    private lateinit var listener: Player.Listener
    private lateinit var sharedViewModel: SharedViewModel

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onCreate() {
        super.onCreate()
        initSessionAndPlayer()
        setupListener()
        sharedViewModel = SharedViewModel.instance
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
                val playlistChanged =
                    reason == Player.MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED
                val indexToPlay = sharedViewModel.indexToPlay.value ?: 0
                if (!playlistChanged || indexToPlay == 0) {
                    sharedViewModel.setPlayingSong(player.currentMediaItemIndex)
                    saveDataToDB()
                }
            }
        }
        player.addListener(listener)
    }

    private fun saveDataToDB() {
        val song = extractSong()
        if (song != null) {
            val handler = Looper.myLooper()?.let {
                Handler(it)
            }
            handler?.postDelayed({
                val player = mediaSession.player
                if (player.isPlaying) {
                    sharedViewModel.insertRecentSongToDB(song)
                    saveCounterAndReplay()
                }
            }, 5000)
        }
    }


    private fun saveCounterAndReplay() {
        val song = extractSong()
        song?.let {
            val handlerThread = HandlerThread(
                "UpdateCounterAndReplayThread",
                Process.THREAD_PRIORITY_BACKGROUND
            )
            handlerThread.start()
            val handler = Handler(handlerThread.looper)
            handler.post {
                sharedViewModel.updateSongInDB(song)
            }
        }
    }

    private fun extractSong(): Song? {
        return sharedViewModel.playingSong.value?.song
    }
}