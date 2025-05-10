package com.example.musicapplication.ui.library.playlist.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.databinding.FragmentPlaylistDetailBinding
import com.example.musicapplication.ui.home.recommended.SongAdapter


class PlaylistDetailFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistDetailBinding
    private lateinit var adapter: SongAdapter
    private val playlistDetailViewModel: PlaylistDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        binding.includePlaylistDetail
            .toolbarPlaylistDetail
            .setNavigationOnClickListener {
                requireActivity().supportFragmentManager
                    .popBackStack()
            }
        val title = getString(R.string.title_playlist_detail)
        binding.includePlaylistDetail
            .textPlaylistDetailToolbarTitle.text = title
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
        binding.includePlaylistDetail
            .includeSongList
            .rvSongList.adapter = adapter
    }

    private fun observeData() {
        playlistDetailViewModel.playlistWithSongs
            .observe(viewLifecycleOwner) { playlistWithSongs ->
                adapter.updateSongs(playlistWithSongs.songs)
                binding.includePlaylistDetail
                    .textPlaylistDetailTitle.text = playlistWithSongs.playlist?.name
                val numberOfSong =
                    getString(R.string.text_number_of_songs, playlistWithSongs.songs.size)
                binding.includePlaylistDetail
                    .textPlaylistDetailNumOfSong.text = numberOfSong
                val artworkId = playlistWithSongs.songs.firstOrNull()?.image
                Glide.with(this)
                    .load(artworkId)
                    .error(R.drawable.ic_album)
                    .into(binding.includePlaylistDetail.imagePlaylistArtwork)
            }
    }
}