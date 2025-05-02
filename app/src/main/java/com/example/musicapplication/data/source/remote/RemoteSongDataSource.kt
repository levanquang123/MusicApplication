package com.example.musicapplication.data.source.remote

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.source.Result
import com.example.musicapplication.data.source.remote.SongDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteSongDataSource : SongDataSource.Remote {
    override suspend fun loadSongs(callback: ResultCallback<Result<SongList>>) {
        withContext(Dispatchers.IO) {
            val response = RetrofitHelper.instance.loadSongs()
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val songList = response.body()!!
                    callback.onResult(Result.Success(songList))
                } else {
                    callback.onResult(Result.Error(Exception("Empty response")))
                }
            } else {
                callback.onResult(Result.Error(Exception(response.message())))
            }
        }
    }
}