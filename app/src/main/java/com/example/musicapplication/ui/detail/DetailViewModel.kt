package com.example.musicapplication.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.model.song.Song

class DetailViewModel : ViewModel() {
    private val _songs = MutableLiveData<List<Song>>()
    private val _playlistName = MutableLiveData<String>()
    private val _screenName = MutableLiveData<String>()

    val songs: MutableLiveData<List<Song>>
        get() = _songs
    val playlistName: MutableLiveData<String>
        get() = _playlistName
    val screenName: MutableLiveData<String>
        get() = _screenName

    fun setSongs(songs: List<Song>) {
        _songs.value = songs
    }

    fun setPlaylistName(name: String) {
        _playlistName.value = name
    }

    fun setScreenName(name: String) {
        _screenName.value = name
    }
}