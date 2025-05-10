package com.example.musicapplication.ui.library.playlist.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.databinding.FragmentMorePlaylistBinding
import com.example.musicapplication.ui.library.playlist.PlaylistAdapter

class MorePlaylistFragment : Fragment() {
    private lateinit var binding: FragmentMorePlaylistBinding
    private lateinit var adapter: PlaylistAdapter
    private val morePlaylistViewModel: MorePlaylistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMorePlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        binding.toolbarMorePlaylist.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
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
        binding.rvMorePlaylist.adapter = adapter
    }

    private fun observeData() {
        morePlaylistViewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.updatePlaylists(playlists)
        }
    }
}