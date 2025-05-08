package com.example.musicapplication.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.repository.recent.RecentSongRepositoryImpl

class LibraryViewModel(
    private val recentSongRepository: RecentSongRepositoryImpl
) : ViewModel() {
    val recentSongs: LiveData<List<Song>> = recentSongRepository.recentSongs.asLiveData()

    class Factory(
        private val recentSongRepository: RecentSongRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LibraryViewModel(recentSongRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}