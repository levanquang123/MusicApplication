package com.example.musicapplication.utils

import android.content.Context
import com.example.musicapplication.data.repository.recent.RecentSongRepositoryImpl
import com.example.musicapplication.data.source.local.AppDatabase
import com.example.musicapplication.data.source.local.recent.LocalRecentSongDataSource

object InjectionUtils {
    fun provideRecentSongDataSource(
        context: Context
    ): LocalRecentSongDataSource {
        val database = AppDatabase.getInstance(context)
        return LocalRecentSongDataSource(database.recentSongDao())
    }

    fun provideRecentSongRepository(
        dataSource: LocalRecentSongDataSource
    ): RecentSongRepositoryImpl {
        return RecentSongRepositoryImpl(dataSource)
    }
}