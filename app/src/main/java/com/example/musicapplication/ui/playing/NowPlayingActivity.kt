package com.example.musicapplication.ui.playing

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.databinding.ActivityNowPlayingBinding
import com.example.musicapplication.ui.viewmodel.MediaPlayerViewModel
import com.example.musicapplication.ui.viewmodel.SharedViewModel

class NowPlayingActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNowPlayingBinding
    private var mediaController: MediaController? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var pressedAnimator: Animator
    private val nowPlayingViewModel: NowPlayingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupMediaController()
        setupAnimator()
    }

    override fun onClick(v: View) {
        pressedAnimator.setTarget(v)
        pressedAnimator.start()
        when (v) {
            binding.btnPlayPauseNowPlaying -> setupPlayPauseAction()
            else -> {}
        }
    }

    private fun setupPlayPauseAction() {
        mediaController?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.play()
            }
        }
    }

    private fun setupView() {
        binding.btnPlayPauseNowPlaying.setOnClickListener(this)
        binding.btnShuffle.setOnClickListener(this)
        binding.btnSkipPrevNowPlaying.setOnClickListener(this)
        binding.btnSkipNextNowPlaying.setOnClickListener(this)
        binding.btnRepeat.setOnClickListener(this)
        binding.btnShareNowPlaying.setOnClickListener(this)
        binding.btnFavoriteNowPlaying.setOnClickListener(this)
    }

    private fun setupViewModel() {
        sharedViewModel = SharedViewModel.instance
        nowPlayingViewModel.isPlaying.observe(this) { isPlaying ->
            val iconId = if (isPlaying) {
                R.drawable.ic_pause_circle
            } else {
                R.drawable.ic_play_circle
            }
            binding.btnPlayPauseNowPlaying.setImageResource(iconId)
        }
    }

    private fun setupMediaController() {
        MediaPlayerViewModel.instance.mediaController.observe(this) { controller ->
            mediaController = controller
            sharedViewModel.playingSong.observe(this) { playingSong ->
                showSongInfo(playingSong.song)
            }
            setupMediaListener()
        }
    }

    private fun showSongInfo(song: Song?) {
        if (song != null) {
            binding.textAlbumNowPlaying.text = song.album
            binding.textSongTitleNowPlaying.text = song.title
            binding.textSongArtistNowPlaying.text = song.artist
            Glide.with(this)
                .load(song.image)
                .error(R.drawable.ic_album)
                .circleCrop()
                .into(binding.imageArtworkNowPlaying)
        }
    }

    private fun setupMediaListener() {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                nowPlayingViewModel.setIsPlaying(isPlaying)
            }
        }
        mediaController?.addListener(listener)
    }

    private fun setupAnimator() {
        pressedAnimator = AnimatorInflater.loadAnimator(this, R.animator.button_pressed)
    }
}