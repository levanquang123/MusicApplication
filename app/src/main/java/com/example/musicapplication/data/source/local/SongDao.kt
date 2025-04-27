package com.example.musicapplication.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.musicapplication.data.model.song.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM songs")
    fun getAll(): List<Song>

    @get:Query("SELECT * FROM songs WHERE favorite = 1")
    val favoriteSongs: Flow<List<Song>>

    @Insert
    suspend fun insert(vararg songs: Song)

    @Delete
    suspend fun delete(song: Song)

    @Update
    suspend fun update(song: Song)
}