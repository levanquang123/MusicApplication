package com.example.musicapplication.ui.detail

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.R
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.databinding.FragmentDetailBinding
import com.example.musicapplication.ui.home.recommended.SongAdapter

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: SongAdapter
    private val detailViewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        binding.toolbarDetailSongList.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    // todo
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    // todo
                }
            }
        )
        binding.includeSongList.rvSongList.adapter = adapter
    }

    private fun observeData() {
        detailViewModel.songs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs)
        }
        detailViewModel.screenName.observe(viewLifecycleOwner) { screenName ->
            binding.textTitleDetailSongList.text = screenName
        }
        detailViewModel.playlistName.observe(viewLifecycleOwner) { playlistName ->
            // todo
        }
    }
}