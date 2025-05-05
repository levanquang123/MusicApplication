package com.example.musicapplication.data.source

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.song.Song
import kotlinx.coroutines.flow.Flow

interface SongDataSource {
    interface Local {
        val songs: List<Song>

        val favoriteSongs: Flow<List<Song>>

        suspend fun insert(vararg songs: Song)

        suspend fun delete(song: Song)

        suspend fun update(song: Song)

        suspend fun updateFavorite(id: String, favorite: Boolean)

        interface Remote {
            suspend fun loadSongs(callback: ResultCallback<Result<SongList>>)
        }
    }
}