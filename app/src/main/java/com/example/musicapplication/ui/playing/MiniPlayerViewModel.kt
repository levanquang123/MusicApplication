package com.example.musicapplication.ui.playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem

class MiniPlayerViewModel : ViewModel() {
    private val _mediaItems = MutableLiveData<List<MediaItem>>()
    private val _isPlaying = MutableLiveData<Boolean>()
    val mediaItems: LiveData<List<MediaItem>> = _mediaItems
    val isPlaying: LiveData<Boolean> = _isPlaying

    fun setMediaItem(mediaItems: List<MediaItem>) {
        _mediaItems.value = mediaItems
    }

    fun setPlayingState(state: Boolean) {
        _isPlaying.value = state
    }
}