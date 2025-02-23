package com.example.musicapplication.data.model.album

import com.google.gson.annotations.SerializedName

data class AlbumList(
    @SerializedName("playlists")
    val albums: List<Album> = emptyList()
)
