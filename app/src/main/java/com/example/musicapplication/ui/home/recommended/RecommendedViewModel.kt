package com.example.musicapplication.ui.home.recommended

import Song
import SongList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.source.Result
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.repository.SongRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecommendedViewModel : ViewModel() {
    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>>
        get() = _songs

    fun setSongs(songs: List<Song>) {
        _songs.postValue(songs)
    }
}