package com.example.musicapplication.data.source

import com.example.musicapplication.data.model.RecentSong
import com.example.musicapplication.data.model.song.Song
import kotlinx.coroutines.flow.Flow

interface RecentSongDataSource {
    interface Local {
        val recentSongs: Flow<List<Song>>
        suspend fun insert(vararg recentSongs: RecentSong)
    }

    interface Remote {
        // TODO
    }
}