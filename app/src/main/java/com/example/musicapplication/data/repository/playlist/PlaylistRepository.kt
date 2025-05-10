package com.example.musicapplication.data.repository.playlist

import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapplication.data.model.playlist.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    interface Local {
        val playlists: Flow<List<Playlist>>
        val allPlaylists: Flow<List<Playlist>>
        fun getAllPlaylistsWithSongs(): Flow<List<PlaylistWithSongs>>
        fun getPlaylistsWithSongsByPlaylistId(playlistId: Int): Flow<PlaylistWithSongs>
        suspend fun findPlaylistByName(name: String): Playlist?
        suspend fun createPlaylist(playlist: Playlist)
        suspend fun createPlaylistSongCrossRef(playlistSongCrossRef: PlaylistSongCrossRef): Long
        suspend fun deletePlaylist(playlist: Playlist)
        suspend fun updatePlaylist(playlist: Playlist)
    }

    interface Remote {
        // todo
    }
}