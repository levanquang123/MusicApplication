package com.example.musicapplication.ui.home

import SongList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.source.Result
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.repository.AlbumRepositoryImpl
import com.example.musicapplication.data.repository.SongRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val albumRepository = AlbumRepositoryImpl()
    private val songRepository = SongRepositoryImpl()

    private val _albums = MutableLiveData<List<Album>>()
    private val _songs = MutableLiveData<List<Song>>()


    val albums: LiveData<List<Album>>
        get() = _albums
    val songs: LiveData<List<Song>>
        get() = _songs

    init {
        loadSongs()
        loadAlbums()
    }

    private fun loadAlbums() {
        viewModelScope.launch(Dispatchers.IO) {
            albumRepository.loadAlbums(object : ResultCallback<Result<List<Album>>> {
                override fun onResult(result: Result<List<Album>>) {
                    if (result is Result.Success) {
                        _albums.postValue(result.data)
                    } else {
                        _albums.postValue(emptyList())
                    }
                }
            })
        }
    }

    private fun loadSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            songRepository.loadSongs(object : ResultCallback<Result<SongList>> {
                override fun onResult(result: Result<SongList>) {
                    if (result is Result.Success) {
                        _songs.postValue(result.data.songs)
                    } else {
                        _songs.postValue(emptyList())
                    }
                }
            })
        }
    }
}