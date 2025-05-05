package com.example.musicapplication.data.repository.song

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.source.Result
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    interface Local {
        val songs: List<Song>

        val favoriteSongs: Flow<List<Song>>

        suspend fun insert(vararg songs: Song)

        suspend fun delete(song: Song)

        suspend fun update(song: Song)

        suspend fun updateFavorite(id: String, favorite: Boolean)
    }

    interface Remote {
        suspend fun loadSongs(callback: ResultCallback<Result<SongList>>)
    }
}