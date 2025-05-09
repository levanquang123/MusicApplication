package com.example.musicapplication.ui.library.playlist

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.R
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.databinding.FragmentPlaylistBinding

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlaylistAdapter
    private val playlistViewModel: PlaylistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        adapter = PlaylistAdapter(
            object : PlaylistAdapter.OnPlaylistClickListener {
                override fun onPlaylistClick(playlist: Playlist) {
                    // todo
                }

                override fun onPlaylistMenuOptionClick(playlist: Playlist) {
                    // todo
                }
            }
        )
        binding.rvPlaylist.adapter = adapter
    }

    private fun observeData() {
        playlistViewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.updatePlaylists(playlists)
        }
    }
}