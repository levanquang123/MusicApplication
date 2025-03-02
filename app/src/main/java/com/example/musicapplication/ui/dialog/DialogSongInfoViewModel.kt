package com.example.musicapplication.ui.dialog

import Song
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogSongInfoViewModel : ViewModel() {
    private val _song = MutableLiveData<Song>()

    val song: MutableLiveData<Song>
        get() = _song

    fun setSong(song: Song) {
        _song.value = song
    }
}