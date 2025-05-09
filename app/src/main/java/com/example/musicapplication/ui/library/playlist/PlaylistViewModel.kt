package com.example.musicapplication.ui.library.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.model.playlist.Playlist

class PlaylistViewModel : ViewModel() {
    private val _playlists = MutableLiveData<List<Playlist>>()
    val playlists: LiveData<List<Playlist>>
        get() = _playlists

    fun setPlaylists(playlists: List<Playlist>) {
        _playlists.value = playlists
    }
}