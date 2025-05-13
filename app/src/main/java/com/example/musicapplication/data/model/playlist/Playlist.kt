package com.example.musicapplication.data.model.playlist

import androidx.media3.common.MediaItem
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.musicapplication.data.model.song.Song
import java.util.Date

@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "playlist_id")
    var id: Int = 1001, // ← để Room tự sinh, không dùng _id / setter phức tạp

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "artwork")
    var artwork: String? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: Date? = Date()
) {
    // Bỏ logic autoId thủ công
    @Ignore
    var songs: List<Song> = listOf()

    @Ignore
    private val _mediaItems: MutableList<MediaItem> = mutableListOf()

    val mediaItems: List<MediaItem>
        get() = _mediaItems

    fun updateSongList(songs: List<Song>) {
        this.songs = songs
        updateMediaItems()
    }

    private fun updateMediaItems() {
        _mediaItems.clear()
        songs.forEach { song ->
            _mediaItems.add(MediaItem.fromUri(song.source))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Playlist) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id
}
