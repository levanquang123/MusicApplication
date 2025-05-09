package com.example.musicapplication.data.repository.playlist

import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.source.PlaylistDataSource
import kotlinx.coroutines.flow.Flow

class PlaylistRepositoryImpl(
    private val localDataSource: PlaylistDataSource.Local
) : PlaylistRepository.Local, PlaylistRepository.Remote {
    override val playlists: Flow<List<Playlist>>
        get() = localDataSource.playlists

    override suspend fun findPlaylistByName(name: String): Playlist? {
        return localDataSource.findPlaylistByName(name)
    }

    override suspend fun createPlaylist(playlist: Playlist) {
        localDataSource.createPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        localDataSource.deletePlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: Playlist) {
        localDataSource.updatePlaylist(playlist)
    }
}