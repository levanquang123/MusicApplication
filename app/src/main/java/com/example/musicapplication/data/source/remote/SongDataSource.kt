package com.example.musicapplication.data.source.remote

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.source.Result

interface SongDataSource {
    interface Local {
        //  todo
    }

    interface Remote {
        suspend fun loadSongs(callback: ResultCallback<Result<SongList>>)
    }
}