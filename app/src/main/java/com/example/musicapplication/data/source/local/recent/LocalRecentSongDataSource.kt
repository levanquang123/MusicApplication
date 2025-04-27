package com.example.musicapplication.data.source.local.recent

import com.example.musicapplication.data.model.RecentSong
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.source.RecentSongDataSource
import kotlinx.coroutines.flow.Flow

class LocalRecentSongDataSource(
    private val recentSongDao: RecentSongDao
) : RecentSongDataSource.Local {
    override val recentSongs: Flow<List<Song>>
        get() = recentSongDao.recentSongs

    override suspend fun insert(vararg recentSongs: RecentSong) {
        recentSongDao.insert(*recentSongs)
    }
}