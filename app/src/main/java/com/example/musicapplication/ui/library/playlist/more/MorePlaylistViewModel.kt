package com.example.musicapplication.ui.library.playlist.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.model.playlist.PlaylistWithSongs

class MorePlaylistViewModel : ViewModel() {
    private val _playlists = MutableLiveData<List<PlaylistWithSongs>>()

    val playlists: LiveData<List<PlaylistWithSongs>> = _playlists

    fun setPlaylists(playlists: List<PlaylistWithSongs>) {
        _playlists.value = playlists
    }
}