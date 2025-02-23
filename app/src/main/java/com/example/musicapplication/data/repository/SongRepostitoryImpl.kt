package com.example.musicapplication.data.repository

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.source.Result
import com.example.musicapplication.data.source.remote.RemoteSongDataSource

class SongRepositoryImpl : SongRepository.Remote, SongRepository.Local {
    private val remoteSongDataSource = RemoteSongDataSource()
    override suspend fun loadSongs(callback: ResultCallback<Result<SongList>>) {
        remoteSongDataSource.loadSongs(callback)
    }
}