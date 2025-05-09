package com.example.musicapplication.data.source.local.playlist

import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.source.PlaylistDataSource
import kotlinx.coroutines.flow.Flow

class LocalPlaylistDataSource(
    private val playlistDao: PlaylistDao
) : PlaylistDataSource.Local {
    override val playlists: Flow<List<Playlist>>
        get() = playlistDao.playlists

    override suspend fun findPlaylistByName(name: String): Playlist? {
        return playlistDao.findPlaylistByName(name)
    }
    override suspend fun createPlaylist(playlist: Playlist) {
        playlistDao.insert(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.delete(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        playlistDao.update(playlist)
    }
}