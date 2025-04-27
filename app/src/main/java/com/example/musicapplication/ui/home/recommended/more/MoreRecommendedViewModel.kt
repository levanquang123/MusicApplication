package com.example.musicapplication.ui.home.recommended.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.model.song.Song

class MoreRecommendedViewModel : ViewModel() {
    private val _recommendedSongs = MutableLiveData<List<Song>>()
    val recommendedSongs: LiveData<List<Song>>
        get() = _recommendedSongs

    fun setRecommendedSongs(songs: List<Song>) {
        _recommendedSongs.postValue(songs)
    }
}