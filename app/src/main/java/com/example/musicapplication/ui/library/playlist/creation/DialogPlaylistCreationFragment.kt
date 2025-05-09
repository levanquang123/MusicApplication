package com.example.musicapplication.ui.library.playlist.creation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.musicapplication.R
import com.google.android.material.textfield.TextInputEditText

class DialogPlaylistCreationFragment(
    private val listener: OnClickListener
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val layoutInflater = requireActivity().layoutInflater
        val rootView = layoutInflater.inflate(R.layout.fragment_dialog_playlist_creation, null)
        val editPlaylistName = rootView.findViewById<TextInputEditText>(R.id.edit_playlist_name)
        builder.setView(rootView)
            .setTitle(getString(R.string.title_create_playlist))
            .setPositiveButton(getString(R.string.action_create)) { _, _ ->
                if (editPlaylistName != null && editPlaylistName.text != null) {
                    val playlistName = editPlaylistName.text.toString().trim()
                    if (playlistName.isNotEmpty()) {
                        listener.onClick(playlistName)
                    } else {
                        val message = getString(R.string.error_empty_playlist_name)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton(getString(R.string.action_cancel)) { _, _ ->
                dismiss()
            }
        editPlaylistName.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                requireActivity().window
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            }
        }
        return builder.create()
    }

    interface OnClickListener {
        fun onClick(playlistName: String)
    }

    companion object {
        const val TAG = "DialogPlaylistCreationFragment"
    }
}