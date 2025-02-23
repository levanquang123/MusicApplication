package com.example.musicapplication.data.repository

import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.data.source.Result

interface AlbumRepository {
    interface Local {
        // todo
    }

    interface Remote {
        suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>)
    }
}