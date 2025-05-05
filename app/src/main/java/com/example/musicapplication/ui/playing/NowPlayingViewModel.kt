package com.example.musicapplication.ui.playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NowPlayingViewModel : ViewModel() {
    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    fun setIsPlaying(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
    }
}