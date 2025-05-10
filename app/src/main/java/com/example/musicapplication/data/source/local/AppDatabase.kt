package com.example.musicapplication.data.source.local

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.example.musicapplication.data.model.RecentSong
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.model.playlist.PlaylistSongCrossRef
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.source.local.playlist.PlaylistDao
import com.example.musicapplication.data.source.local.recent.RecentSongDao
import com.example.musicapplication.data.source.local.song.SongDao

@Database(
    entities = [
        Song::class,
        Playlist::class,
        Album::class,
        RecentSong::class,
        PlaylistSongCrossRef::class
    ],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = AppDatabase.MigrationSpec::class)
    ]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun albumDao(): AlbumDao
    abstract fun recentSongDao(): RecentSongDao

    internal class MigrationSpec : AutoMigrationSpec

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (_instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (_instance == null) {
                        _instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "music.db"
                        ).build()
                    }
                }
            }
            return _instance!!
        }
    }
}