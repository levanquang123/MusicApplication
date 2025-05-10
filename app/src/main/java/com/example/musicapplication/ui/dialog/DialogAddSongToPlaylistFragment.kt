package com.example.musicapplication.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.MusicApplication
import com.example.musicapplication.R
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.ui.library.playlist.PlaylistAdapter
import com.example.musicapplication.ui.library.playlist.PlaylistViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class DialogAddSongToPlaylistFragment(
    private val listener: OnPlaylistSelectedListener
) : DialogFragment() {
    private lateinit var adapter: PlaylistAdapter
    private val viewModel: PlaylistViewModel by activityViewModels {
        val application = requireActivity().application as MusicApplication
        PlaylistViewModel.Factory(application.getPlaylistRepository())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setupComponents()
        val builder = AlertDialog.Builder(requireActivity())
        val layoutInflater = requireActivity().layoutInflater
        val rootView = layoutInflater.inflate(R.layout.fragment_dialog_add_song_to_playlist, null)
        val recycleViewPlaylist = rootView.findViewById<RecyclerView>(R.id.rv_dialog_playlist)
        recycleViewPlaylist.adapter = adapter
        val btnCancel = rootView.findViewById<MaterialButton>(R.id.btn_cancel)
        val btnCreate = rootView.findViewById<MaterialButton>(R.id.btn_create)
        val editPlaylistName = rootView.findViewById<TextInputEditText>(R.id.edit_playlist_name)
        btnCreate.setOnClickListener {
            // todo
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
        builder.setView(rootView)
        loadPlaylistList()
        return builder.create()
    }

    private fun setupComponents() {
        adapter = PlaylistAdapter(object : PlaylistAdapter.OnPlaylistClickListener {
            override fun onPlaylistClick(playlist: Playlist) {
                // todo
            }

            override fun onPlaylistMenuOptionClick(playlist: Playlist) {
                // todo
            }
        })
    }

    private fun loadPlaylistList() {
        viewModel.playlists.observe(requireActivity()) { playlists ->
            adapter.updatePlaylists(playlists)
        }
    }

    interface OnPlaylistSelectedListener {
        fun onPlaylistSelected(playlist: Playlist)
    }

    companion object {
        const val TAG = "DialogAddSongToPlaylistFragment"
    }
}