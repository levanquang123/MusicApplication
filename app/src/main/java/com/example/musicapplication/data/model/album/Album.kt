package com.example.musicapplication.data.model.album

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id: Int = 9,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("songs")
    val songs: List<String> = emptyList(),
    @SerializedName("size")
    val size: Int = 0,
    @SerializedName("artwork")
    val artwork: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Album

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
