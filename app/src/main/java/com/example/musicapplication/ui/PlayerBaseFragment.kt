package com.example.musicapplication.ui

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.R
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.ui.dialog.SongOptionMenuDialogFragment
import com.example.musicapplication.ui.dialog.SongOptionMenuViewModel
import com.example.musicapplication.ui.playing.NowPlayingActivity
import com.example.musicapplication.ui.viewmodel.PermissionViewModel
import com.example.musicapplication.ui.viewmodel.SharedViewModel
import com.example.musicapplication.utils.MusicAppUtils

open class PlayerBaseFragment : Fragment() {
    protected fun playSong(song: Song, index: Int, playlistName: String) {
        val isPermissionGranted = PermissionViewModel.instance.permissionGranted.value
        if (isPermissionGranted != null && isPermissionGranted) {
            doNavigate(index, playlistName)
        } else if (!PermissionViewModel.isRegistered) {
            PermissionViewModel.instance
                .permissionGranted
                .observe(requireActivity()) {
                    if (it) {
                        doNavigate(index, playlistName)
                    }
                }
            PermissionViewModel.isRegistered = true
        }
    }

    private fun doNavigate(index: Int, playlistName: String) {
        val sharedViewModel = SharedViewModel.instance
        sharedViewModel.setCurrentPlaylist(playlistName)
        sharedViewModel.setIndexToPlay(index)
        Intent(requireContext(), NowPlayingActivity::class.java).apply {
            val options = ActivityOptionsCompat
                .makeCustomAnimation(requireContext(), R.anim.slide_up, R.anim.fade_out)
            startActivity(this, options.toBundle())
        }
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