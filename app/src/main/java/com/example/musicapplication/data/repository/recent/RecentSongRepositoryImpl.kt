package com.example.musicapplication.data.repository.recent

import com.example.musicapplication.data.model.RecentSong
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.source.local.recent.LocalRecentSongDataSource
import kotlinx.coroutines.flow.Flow

class RecentSongRepositoryImpl(
    private val localSongDataSource: LocalRecentSongDataSource,
): RecentSongRepository.Local, RecentSongRepository.Remote {
    override val recentSongs: Flow<List<Song>>
        get() = localSongDataSource.recentSongs

    override suspend fun insert(vararg recentSongs: RecentSong) {
        localSongDataSource.insert(*recentSongs)
    }
}