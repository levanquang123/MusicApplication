package com.example.musicapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.MusicApplication
import com.example.musicapplication.databinding.FragmentHomeBinding
import com.example.musicapplication.ui.home.album.AlbumHotViewModel
import com.example.musicapplication.ui.home.recommended.RecommendedViewModel

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        HomeViewModel.Factory(application.getSongRepository())
    }
    private val albumViewModel: AlbumHotViewModel by activityViewModels()
    private val songViewModel: RecommendedViewModel by activityViewModels()
    private var isObserved = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isObserved) {
            setupObserver()
            isObserved = true
        }
    }

    private fun setupObserver() {
        homeViewModel.albums.observe(viewLifecycleOwner) {
            albumViewModel.setAlbums(it)
            Log.d("HomeViewModel", "Abum đã được gọi.")
        }
        homeViewModel.songs.observe(viewLifecycleOwner) {
            homeViewModel.saveSongsToDB()
            Log.d("HomeViewModel", "Hàm saveSongsToDB() đã được gọi.")
        }
        homeViewModel.localSongs.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                homeViewModel.songs.observe(viewLifecycleOwner) { remoteSongs ->
                    songViewModel.setSongs(remoteSongs)
                    Log.d("HomeViewModel", "empty đã được gọi.")
                }
            } else {
                songViewModel.setSongs(it)
                Log.d("HomeViewModel","lấy trong local đã được gọi.")

            }
        }
    }
}