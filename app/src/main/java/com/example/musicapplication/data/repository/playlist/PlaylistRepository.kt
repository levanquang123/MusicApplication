package com.example.musicapplication.data.repository.playlist

import com.example.musicapplication.data.model.playlist.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    interface Local {
        val playlists: Flow<List<Playlist>>
        val allPlaylists: Flow<List<Playlist>>
        suspend fun findPlaylistByName(name: String): Playlist?
        suspend fun createPlaylist(playlist: Playlist)
        suspend fun deletePlaylist(playlist: Playlist)
        suspend fun updatePlaylist(playlist: Playlist)
    }

    interface Remote {
        // todo
    }
}