package com.example.musicapplication

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicapplication.databinding.ActivityMainBinding
import com.example.musicapplication.ui.home.HomeViewModel
import com.example.musicapplication.ui.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var currentSongLoaded = false
    private val sharedViewModel: SharedViewModel by viewModels {
        val application = application as MusicApplication
        SharedViewModel.Factory(
            application.getSongRepository(),
            application.getRecentSongRepository()
        )
    }
    private val homeViewModel: HomeViewModel by viewModels {
        val application = application as MusicApplication
        HomeViewModel.Factory(application.getSongRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
        setupComponents()
        observeData()
    }

    override fun onStop() {
        super.onStop()
        saveCurrentSong()
    }

    private fun setupBottomNav() {
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment)
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_library, R.id.navigation_discovery
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun setupComponents() {
        sharedViewModel.initPlaylist()
        sharedPreferences = getSharedPreferences(
            "com.example.musicapplication.preff_file",
            MODE_PRIVATE
        )
    }

    private fun observeData() {
        sharedViewModel.isReady.observe(this) { ready ->
            if (ready && !currentSongLoaded) {
                loadPreviousSessionSong()
                currentSongLoaded = true
            }
        }
    }

    private fun saveCurrentSong() {
        val playingSong = sharedViewModel.playingSong.value
        playingSong?.let {
            val song = it.song
            song?.let { currentSong ->
                sharedPreferences.edit()
                    .putString(PREF_SONG_ID, currentSong.id)
                    .putString(PREF_PLAYLIST_NAME, it.playlist?.name)
                    .apply()
            }
        }
    }

    private fun loadPreviousSessionSong() {
        val songId = sharedPreferences.getString(PREF_SONG_ID, null)
        val playlistName = sharedPreferences.getString(PREF_PLAYLIST_NAME, null)
        sharedViewModel.loadPreviousSessionSong(songId, playlistName)
    }

    companion object {
        const val PREF_SONG_ID = "PREF_SONG_ID"
        const val PREF_PLAYLIST_NAME = "PREF_PLAYLIST_NAME"
    }
}