package com.example.musicapplication.utils

object MusicAppUtils {
    const val DEFAULT_MARGIN_END = 48
    const val EXTRA_CURRENT_FRACTION = "EXTRA_CURRENT_FRACTION"
    @JvmField
    var DENSITY: Float = 0f

    enum class DefaultPlaylistName(val value: String) {
        DEFAULT("Default"),
        FAVORITES("Favorites"),
        RECOMMENDED("Recommended"),
        RECENT("Recent"),
        SEARCH("Search"),
        MOST_HEARD("Most_Heard"),
        FOR_YOU("For_You"),
        CUSTOM("Custom")
    }
}