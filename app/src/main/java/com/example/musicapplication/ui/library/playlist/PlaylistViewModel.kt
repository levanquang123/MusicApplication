package com.example.musicapplication.ui.library.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.repository.playlist.PlaylistRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class PlaylistViewModel(
    private val playlistRepository: PlaylistRepositoryImpl
) : ViewModel() {
    private val _playlist = MutableLiveData<Playlist?>()
    private val _playlists = MutableLiveData<List<Playlist>>()

    val playlists: LiveData<List<Playlist>>
        get() = _playlists

    val playlist: LiveData<Playlist?>
        get() = _playlist

    fun setPlaylists(playlists: List<Playlist>) {
        _playlists.value = playlists
    }

    fun createNewPlaylist(playlistName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val playlist = Playlist(_id = 0, name = playlistName, createdAt = Date())
            playlistRepository.createPlaylist(playlist)
        }
    }
    fun findPlaylistByName(playlistName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = playlistRepository.findPlaylistByName(playlistName)
            _playlist.postValue(result)
        }
    }

    class Factory(
        private val playlistRepository: PlaylistRepositoryImpl
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
                return PlaylistViewModel(playlistRepository) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}