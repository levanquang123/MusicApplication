package com.example.musicapplication.data.source.local.playlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.musicapplication.data.model.playlist.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @get:Query("SELECT * FROM playlists LIMIT 10")
    val playlists: Flow<List<Playlist>>

    @Query("SELECT * FROM playlists WHERE name = :name")
    suspend fun findPlaylistByName(name: String): Playlist?

    @get:Query("SELECT * FROM playlists")
    val allPlaylists: Flow<List<Playlist>>

    @Insert
    suspend fun insert(playlist: Playlist)

    @Delete
    suspend fun delete(playlist: Playlist)

    @Update
    suspend fun update(playlist: Playlist)
}