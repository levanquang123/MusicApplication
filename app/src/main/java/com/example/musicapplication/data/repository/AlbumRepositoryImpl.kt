package com.example.musicapplication.data.repository

import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.data.source.Result
import com.example.musicapplication.data.source.remote.RemoteAlbumDataSource

class AlbumRepositoryImpl : AlbumRepository.Local, AlbumRepository.Remote {
    private val remoteAlbumDataSource = RemoteAlbumDataSource()

    override suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>) {
        remoteAlbumDataSource.loadAlbums(callback)
    }
}