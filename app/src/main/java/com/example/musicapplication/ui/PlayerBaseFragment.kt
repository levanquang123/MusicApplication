package com.example.musicapplication.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.ui.dialog.SongOptionMenuDialogFragment
import com.example.musicapplication.ui.dialog.SongOptionMenuViewModel
import com.example.musicapplication.ui.viewmodel.SharedViewModel

open class PlayerBaseFragment : Fragment() {
    protected fun playSong(song: Song, index: Int, playlistName: String) {
        SharedViewModel.instance?.setCurrentPlaylist(playlistName)
        SharedViewModel.instance?.setIndexToPlay(index)
    }
    protected fun showOptionMenu(song: Song) {
        val menuDialogFragment = SongOptionMenuDialogFragment.newInstance
        val menuDialogViewMode: SongOptionMenuViewModel by activityViewModels()
        menuDialogViewMode.setSong(song)
        menuDialogFragment.show(
            requireActivity().supportFragmentManager,
            SongOptionMenuDialogFragment.TAG
        )
    }
}