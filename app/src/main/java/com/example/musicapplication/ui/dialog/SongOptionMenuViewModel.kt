package com.example.musicapplication.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.utils.OptionMenuUtils

class SongOptionMenuViewModel : ViewModel() {
    private val _song = MutableLiveData<Song>()
    private val _optionMenuItem = MutableLiveData<List<MenuItem>>()
    private val _playlistName = MutableLiveData<String>()

    val song: MutableLiveData<Song> = _song
    val optionMenuItem: MutableLiveData<List<MenuItem>> = _optionMenuItem
    val playlistName: MutableLiveData<String> = _playlistName

    fun setPlaylistName(name: String) {
        _playlistName.value = name
    }

    init {
        _optionMenuItem.value = OptionMenuUtils.songOptionMenuItems
    }

    fun setSong(song: Song) {
        _song.value = song
    }
}
