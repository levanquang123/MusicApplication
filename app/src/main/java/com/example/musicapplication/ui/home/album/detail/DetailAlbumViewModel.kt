package com.example.musicapplication.ui.home.album.detail

import Song
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.model.album.Album

class DetailAlbumViewModel : ViewModel() {
    private val _album = MutableLiveData<Album>()
    private val _songs = MutableLiveData<List<Song>>()
    val album: LiveData<Album>
        get() = _album
    val songs: LiveData<List<Song>>
        get() = _songs

    fun setAlbum(album: Album) {
        _album.value = album
    }

    fun extractSongs(album: Album, songs: List<Song>?) {
        songs?.let{
            val songList = mutableListOf<Song>()
            for ( songId in album.songs) {
                val songIndex = songs.indexOfFirst { it.id == songId }
                if (songIndex != -1) {
                    songList.add(songs[songIndex])
                }
            }
            _songs.value = songList
        }
    }
}
