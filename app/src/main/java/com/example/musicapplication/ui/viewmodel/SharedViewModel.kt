package com.example.musicapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.model.PlayingSong
import com.example.musicapplication.data.model.RecentSong
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.repository.recent.RecentSongRepositoryImpl
import com.example.musicapplication.utils.MusicAppUtils.DefaultPlaylistName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel private constructor(
    private val recentSongRepository: RecentSongRepositoryImpl
) : ViewModel() {
    private val _playingSong = PlayingSong()
    private val _playingSongLiveData = MutableLiveData<PlayingSong>()
    private val _currentPlaylist = MutableLiveData<Playlist>()
    private val _playlists: MutableMap<String, Playlist> = HashMap()
    private val _indexToPlay = MutableLiveData<Int>()

    val playingSong: LiveData<PlayingSong> = _playingSongLiveData
    val currentPlaylist: LiveData<Playlist> = _currentPlaylist
    val indexToPlay: LiveData<Int> = _indexToPlay

    init {
        if (instance == null) {
            synchronized(SharedViewModel::class.java) {
                if (instance == null) {
                    instance = this
                }
            }
        }
    }

    fun initPlaylist() {
        for (playlistName in DefaultPlaylistName.entries.toTypedArray()) {
            val playlist = Playlist(_id = -1, name = playlistName.value)
            _playlists[playlistName.value] = playlist
        }
    }

    fun insertRecentSongToDB(song: Song) {
        val recentSong = createRecentSong(song)
        viewModelScope.launch(Dispatchers.IO) {
            recentSongRepository.insert(recentSong)
        }
    }

    private fun createRecentSong(song: Song): RecentSong {
        return RecentSong.Builder(song).build()
    }

    fun setupPlaylist(
        songs: List<
                Song>?, playlistName: String
    ) {
        val playlist = _playlists.getOrDefault(playlistName, null)
        playlist?.let {
            it.updateSongList(songs)
            updatePlaylist(it)
        }
    }

    private fun updatePlaylist(playlist: Playlist) {
        val playlistName = playlist.name
        _playlists[playlistName] = playlist
    }

    fun setPlayingSong(index: Int) {
        val currentPlaylistSize = _playingSong.playlist?.songs?.size
        if (index >= 0 && currentPlaylistSize != null && currentPlaylistSize > index) {
            val song = _playingSong.playlist?.songs?.get(index)!!
            _playingSong.song = song
            _playingSong.currentIndex = index
            updatePlayingSong()
        }
    }

    private fun updatePlayingSong() {
        _playingSongLiveData.value = _playingSong
    }

    fun setCurrentPlaylist(playlistName: String) {
        val playlist = _playlists.getOrDefault(playlistName, null)
        playlist?.let {
            _playingSong.playlist = it
            _currentPlaylist.value = it
        }
    }

    fun setIndexToPlay(index: Int) {
        _indexToPlay.value = index
    }

    class Factory(
        private val recentSongRepository: RecentSongRepositoryImpl
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                return SharedViewModel(recentSongRepository) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    companion object {
        var instance: SharedViewModel? = null
            private set
    }
}