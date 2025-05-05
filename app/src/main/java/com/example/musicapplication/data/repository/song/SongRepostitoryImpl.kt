package com.example.musicapplication.data.repository.song

import SongList
import com.example.musicapplication.ResultCallback
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.data.source.Result
import com.example.musicapplication.data.source.SongDataSource
import com.example.musicapplication.data.source.remote.RemoteSongDataSource
import kotlinx.coroutines.flow.Flow

class SongRepositoryImpl(
    private val localSongDataSource: SongDataSource.Local
) : SongRepository.Remote, SongRepository.Local {
    private val remoteSongDataSource = RemoteSongDataSource()
    override suspend fun loadSongs(callback: ResultCallback<Result<SongList>>) {
        remoteSongDataSource.loadSongs(callback)
    }

    override val songs: List<Song>
        get() = localSongDataSource.songs

    override val favoriteSongs: Flow<List<Song>>
        get() = localSongDataSource.favoriteSongs

    override suspend fun insert(vararg songs: Song) {
        localSongDataSource.insert(*songs)
    }

    override suspend fun delete(song: Song) {
        localSongDataSource.delete(song)
    }

    override suspend fun update(song: Song) {
        localSongDataSource.update(song)
    }

    override suspend fun updateFavorite(id: String, favorite: Boolean) {
        localSongDataSource.updateFavorite(id, favorite)
    }
}