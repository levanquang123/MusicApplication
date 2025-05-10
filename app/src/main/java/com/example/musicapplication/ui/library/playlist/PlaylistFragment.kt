package com.example.musicapplication.ui.library.playlist

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.MusicApplication
import com.example.musicapplication.R
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.databinding.FragmentPlaylistBinding
import com.example.musicapplication.ui.library.playlist.creation.DialogPlaylistCreationFragment
import com.example.musicapplication.ui.library.playlist.more.MorePlaylistFragment
import com.example.musicapplication.ui.library.playlist.more.MorePlaylistViewModel

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var adapter: PlaylistAdapter
    private val morePlaylistViewModel: MorePlaylistViewModel by activityViewModels()
    private val playlistViewModel: PlaylistViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }

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
        binding.includeButtonCreatePlaylist.btnItemCreatePlaylist.setOnClickListener {
            showDialogToCreatePlaylist()
        }
        binding.includeButtonCreatePlaylist.textItemCreatePlaylist.setOnClickListener {
            showDialogToCreatePlaylist()
        }
        binding.btnMorePlaylist.setOnClickListener {
            navigateToMorePlaylist()
        }
        binding.textTitlePlaylist.setOnClickListener {
            navigateToMorePlaylist()
        }
    }

    private fun showDialogToCreatePlaylist() {
        val listener = object : DialogPlaylistCreationFragment.OnClickListener {
            override fun onClick(playlistName: String) {
                playlistViewModel.createNewPlaylist(playlistName)
            }
        }
        val textChangeListener = object : DialogPlaylistCreationFragment.OnTextChangeListener {
            override fun onTextChange(playlistName: String) {
                playlistViewModel.findPlaylistByName(playlistName)
            }
        }
        val dialog = DialogPlaylistCreationFragment(listener, textChangeListener)
        val tag = DialogPlaylistCreationFragment.TAG
        dialog.show(requireActivity().supportFragmentManager, tag)
    }


    private fun navigateToMorePlaylist() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, MorePlaylistFragment::class.java, null)
            .addToBackStack(null)
            .commit()
    }

    private fun observeData() {
        playlistViewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.updatePlaylists(playlists)
        }
        playlistViewModel.allPlaylists.observe(viewLifecycleOwner) {
            morePlaylistViewModel.setPlaylists(it)
        }
    }
}