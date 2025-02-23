package com.example.musicapplication.ui.home.album

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.musicapplication.data.model.album.Album
import com.example.musicapplication.databinding.FragmentAlbumHotBinding

class AlbumHotFragment : Fragment() {
    private lateinit var binding: FragmentAlbumHotBinding
    private lateinit var adapter: AlbumAdapter
    private val albumViewModel: AlbumHotViewModel by viewModels()

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
                // todo
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
}