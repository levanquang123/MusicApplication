package com.example.musicapplication.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.musicapplication.MusicApplication
import com.example.musicapplication.databinding.FragmentLibraryBinding
import com.example.musicapplication.ui.library.favorite.FavoriteViewModel
import com.example.musicapplication.ui.library.playlist.PlaylistViewModel
import com.example.musicapplication.ui.library.recent.RecentViewModel

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private val libraryViewModel: LibraryViewModel by viewModels {
        val application = requireActivity().application as MusicApplication
        LibraryViewModel.Factory(
            application.getRecentSongRepository(),
            application.getSongRepository(),
            application.getPlaylistRepository()
        )
    }

    private val recentSongViewModel: RecentViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels  {
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        libraryViewModel.recentSongs.observe(viewLifecycleOwner) { recentSongs ->
            recentSongViewModel.setRecentSongs(recentSongs)
        }
        libraryViewModel.favoriteSongs.observe(viewLifecycleOwner) { favoriteSongs ->
            favoriteViewModel.setSongs(favoriteSongs)
        }
        libraryViewModel.playlists.observe(viewLifecycleOwner) { playlist ->
            playlistViewModel.setPlaylists(playlist)
        }
    }
}