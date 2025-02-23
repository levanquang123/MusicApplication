package com.example.musicapplication.data.source

import SongList
import com.example.musicapplication.ResultCallback

interface SongDataSource {
    interface Local {
        //  todo
    }

    interface Remote {
        suspend fun loadSongs(callback: ResultCallback<Result<SongList>>)
    }
}