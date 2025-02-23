package com.example.musicapplication.data.source

import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.album.Album

interface AlbumDataSource {
    interface Local {
        //  todo
    }

    interface Remote {
        suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>)
    }
}