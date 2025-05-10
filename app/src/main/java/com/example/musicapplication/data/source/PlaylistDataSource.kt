package com.example.musicapplication.data.source

import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapplication.data.model.playlist.PlaylistWithSongs
import kotlinx.coroutines.flow.Flow

interface PlaylistDataSource {
    interface Local {
        val playlists: Flow<List<Playlist>>
        val allPlaylists: Flow<List<Playlist>>
        fun getAllPlaylistsWithSongs(): Flow<List<PlaylistWithSongs>>
        fun getPlaylistsWithSongsByPlaylistId(playlistId: Int): Flow<PlaylistWithSongs>
        suspend fun findPlaylistByName(name: String): Playlist?
        suspend fun createPlaylist(playlist: Playlist)
        suspend fun insertPlaylistSongCrossRef(obj: PlaylistSongCrossRef): Long
        suspend fun deletePlaylist(playlist: Playlist)
        suspend fun updatePlaylist(playlist: Playlist)
    }

    interface Remote {
        // todo
    }
}