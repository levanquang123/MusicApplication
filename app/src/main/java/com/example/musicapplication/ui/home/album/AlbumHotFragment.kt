package com.example.musicapplication.ui.home.album

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.R
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.databinding.FragmentAlbumHotBinding
import com.example.musicapplication.ui.home.HomeViewModel
import com.example.musicapplication.ui.home.album.detail.DetailAlbumFragment
import com.example.musicapplication.ui.home.album.detail.DetailAlbumViewModel

class AlbumHotFragment : Fragment() {
    private lateinit var binding: FragmentAlbumHotBinding
    private lateinit var adapter: AlbumAdapter
    private val albumViewModel: AlbumHotViewModel by activityViewModels()
    private val detailAlbumViewModel: DetailAlbumViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumHotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        binding.progressBarAlbum.visibility = VISIBLE
        adapter = AlbumAdapter(object : AlbumAdapter.OnAlbumClickListener {
            override fun onAlbumClick(album: Album) {
                detailAlbumViewModel.setAlbum(album)
                val songs = homeViewModel.songs.value
                detailAlbumViewModel.extractSongs(album, songs)
                navigateToDetailAlbum()
            }
        })
        binding.rvAlbumHot.adapter = adapter
    }

    private fun observeData() {
        albumViewModel.albums.observe(viewLifecycleOwner) { albums ->
            adapter.updateAlbums(albums.sortedBy {
                -it.size
            }.subList(0, 10))
            binding.progressBarAlbum.visibility = GONE
        }
    }

    private fun navigateToDetailAlbum() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, DetailAlbumFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }
}