package com.example.musicapplication.data.source

import com.example.musicapplication.data.model.playlist.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistDataSource {
    interface Local {
        val playlists: Flow<List<Playlist>>
        suspend fun createPlaylist(playlist: Playlist)
        suspend fun deletePlaylist(playlist: Playlist)
        suspend fun updatePlaylist(playlist: Playlist)
    }

    interface Remote {
        // todo
    }
}