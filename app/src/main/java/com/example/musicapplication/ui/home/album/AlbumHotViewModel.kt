package com.example.musicapplication.ui.home.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.data.repository.AlbumRepositoryImpl
import com.example.musicapplication.data.source.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumHotViewModel : ViewModel() {
    private val albumRepository = AlbumRepositoryImpl()
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>>
        get() = _albums

    init {
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
}