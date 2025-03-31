package com.example.musicapplication.ui.playing

import Song
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem

class MiniPlayerViewModel : ViewModel() {
    private val _song = MutableLiveData<Song>()
    private val _mediaItem = MutableLiveData<MediaItem>()
    val song: LiveData<Song> = _song
    val mediaItem: LiveData<MediaItem> = _mediaItem

    fun setSong(song: Song) {
        _song.value = song
        song?.let {
            setMediaItem(MediaItem.fromUri(it.source))
        }
    }

    private fun setMediaItem(mediaItem: MediaItem) {
        _mediaItem.value = mediaItem
    }
}