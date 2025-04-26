package com.example.musicapplication.data.model

import Song
import androidx.media3.common.MediaItem
import java.util.Date

data class Playlist(
    private var _id: Int = 10001,
    var name: String = "",
    var artwork: String? = null,
    var createdAt: Date? = null
) {
    private val _mediaItems: MutableList<MediaItem> = mutableListOf()
    var songs: List<Song>? = listOf()

    var id: Int
        get() = _id
        set(value) {
            _id = if (id > 0) {
                id
            } else {
                autoId++
            }
        }

    val mediaItems: List<MediaItem>
        get() = _mediaItems

    fun updateSongList(songs: List<Song>?) {
        this.songs = songs
        updateMediaItems()
    }

    private fun updateMediaItems() {
        _mediaItems.clear()
        songs?.forEach { song ->
            _mediaItems.add(MediaItem.fromUri(song.source))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Playlist) return false

        if (_id != other._id) return false

        return true
    }

    override fun hashCode(): Int {
        return _id
    }

    companion object {
        private var autoId = 10001
    }
}
