package com.example.musicapplication.ui

import Song
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.ui.dialog.SongOptionMenuDialogFragment
import com.example.musicapplication.ui.dialog.SongOptionMenuViewModel

open class PlayerBaseFragment : Fragment() {
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