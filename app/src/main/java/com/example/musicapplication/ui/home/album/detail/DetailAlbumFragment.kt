package com.example.musicapplication.ui.home.album.detail

import Song
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.databinding.FragmentDetailAlbumBinding
import com.example.musicapplication.ui.home.recommended.SongAdapter

class DetailAlbumFragment : Fragment() {
    private lateinit var binding: FragmentDetailAlbumBinding
    private lateinit var adapter: SongAdapter
    private val detailAlbumViewModel: DetailAlbumViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.includeAlbumDetail.toolbarPlaylistDetail.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
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
        binding.includeAlbumDetail.includeSongList.rvSongList.adapter = adapter
    }

    private fun setupViewModel() {
        detailAlbumViewModel.album.observe(viewLifecycleOwner) { album ->
            binding.includeAlbumDetail.textPlaylistDetailTitle.text = album.name
            val text = getString(R.string.text_playlist_num_of_song, album.size)
            binding.includeAlbumDetail.textPlaylistDetailNumOfSong.text = text
            Glide.with(binding.root)
                .load(album.artwork)
                .error(R.drawable.ic_album)
                .into(binding.includeAlbumDetail.imagePlaylistArtwork)
        }
        detailAlbumViewModel.songs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs)
        }
    }
}