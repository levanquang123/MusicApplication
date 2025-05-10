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
    private lateinit var binding: FragmentHomeBinding
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isObserved) {
            setupObserver()
            isObserved = true
        }
        if(savedInstanceState != null) {
            val scrollPosition = savedInstanceState.getInt(SCROLL_POSITION)
            binding.scrollViewHome.post {
                binding.scrollViewHome.scrollTo(0, scrollPosition)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val scrollPosition = binding.scrollViewHome.scrollY
        outState.putInt(SCROLL_POSITION, scrollPosition)
    }

    private fun setupObserver() {
        homeViewModel.albums.observe(viewLifecycleOwner) {
            albumViewModel.setAlbums(it)
        }
        homeViewModel.songs.observe(viewLifecycleOwner) {
            homeViewModel.saveSongsToDB()
        }
        homeViewModel.localSongs.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                homeViewModel.songs.observe(viewLifecycleOwner) { remoteSongs ->
                    songViewModel.setSongs(remoteSongs)
                }
            } else {
                songViewModel.setSongs(it)
            }
        }
    }

    companion object {
        const val SCROLL_POSITION = "com.example.musicapplication.ui.home.SCROLL_POSITION"
    }
}