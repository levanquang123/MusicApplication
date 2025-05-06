package com.example.musicapplication.ui.playing

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
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
    private lateinit var seekBarHandler: Handler
    private lateinit var seekbarCallback: Runnable
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

    override fun onDestroy() {
        super.onDestroy()
        seekBarHandler.removeCallbacks(seekbarCallback)
    }

    override fun onClick(v: View) {
        pressedAnimator.setTarget(v)
        pressedAnimator.start()
        when (v) {
            binding.btnPlayPauseNowPlaying -> setupPlayPauseAction()
            binding.btnShuffle -> {}
            binding.btnSkipPrevNowPlaying -> setupSkipPrevious()
            binding.btnSkipNextNowPlaying -> setupSkipNext()
            binding.btnRepeat -> {}
            binding.btnShareNowPlaying -> {}
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

    private fun setupSkipPrevious() {
        mediaController?.let {
            if (it.hasPreviousMediaItem()) {
                it.seekToPreviousMediaItem()
                // todo
            }
        }
    }

    private fun setupSkipNext() {
        mediaController?.let {
            if (it.hasNextMediaItem()) {
                it.seekToNextMediaItem()
                // todo
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
        binding.toolbarNowPlaying.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.seekBarNowPlaying.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaController?.seekTo(progress.toLong())
                }
                binding.textLabelCurrentDuration.text =
                    nowPlayingViewModel.getTimeLabel(progress.toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
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
                setupSeekBar()
                showSongInfo(playingSong.song)
            }
            setupMediaListener()
        }
    }

    private fun setupSeekBar() {
        seekBarHandler = Looper.myLooper()?.let { Handler(it) }!!
        seekbarCallback = object : Runnable {
            override fun run() {
                if (mediaController != null) {
                    val currentPosition = mediaController!!.currentPosition
                    binding.seekBarNowPlaying.progress = currentPosition.toInt()
                }
                seekBarHandler.postDelayed(this, 1000)
            }
        }
        seekBarHandler.post(seekbarCallback)
    }

    private fun showSongInfo(song: Song?) {
        if (song != null) {
            updateSeekBarMaxValue()
            updateDuration()
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

    private fun updateSeekBarMaxValue() {
        val currentPos = mediaController?.currentPosition ?: 0
        binding.seekBarNowPlaying.progress = currentPos.toInt()
        binding.seekBarNowPlaying.max = nowPlayingViewModel.getDuration(mediaController)
    }

    private fun updateDuration() {
        val durationLabel = nowPlayingViewModel.getTimeLabel(mediaController?.duration ?: 0)
        binding.textTotalDuration.text = durationLabel
    }

    private fun setupMediaListener() {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                nowPlayingViewModel.setIsPlaying(isPlaying)
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                if(mediaController!!.isPlaying) {
                    updateSeekBarMaxValue()
                    updateDuration()
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY) {
                    updateSeekBarMaxValue()
                    updateDuration()
                }
            }
        }
        mediaController?.addListener(listener)
    }

    private fun setupAnimator() {
        pressedAnimator = AnimatorInflater.loadAnimator(this, R.animator.button_pressed)
    }
}