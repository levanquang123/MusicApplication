package com.example.musicapplication.data.source.remote

import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.data.source.AlbumDataSource
import com.example.musicapplication.data.source.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteAlbumDataSource : AlbumDataSource.Remote {
    override suspend fun loadAlbums(callback: ResultCallback<Result<List<Album>>>) {
        withContext(Dispatchers.IO) {
            val response = RetrofitHelper.instance.loadAlbums()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val albums = response.body()!!.albums
                    callback.onResult(Result.Success(albums))
                } else {
                    callback.onResult(Result.Error(Exception("Empty response")))
                }
            } else {
                callback.onResult(Result.Error(Exception(response.message())))
            }
        }
    }
}
