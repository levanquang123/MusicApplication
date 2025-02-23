package com.example.musicapplication.data.repository

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.source.Result

interface SongRepository {
    interface Local {
        // todo
    }

    interface Remote {
        suspend fun loadSongs(callback: ResultCallback<Result<SongList>>)
    }
}