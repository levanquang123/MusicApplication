package com.example.musicapplication.ui.playing

import Song
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.media3.session.MediaController
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.databinding.FragmentMiniPlayerBinding
import com.example.musicapplication.ui.viewmodel.MediaPlayerViewModel

class MiniPlayerFragment : Fragment() {
    private lateinit var binding: FragmentMiniPlayerBinding
    private val viewModel: MiniPlayerViewModel by activityViewModels()
    private var mediaController: MediaController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMiniPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMediaController()
        setupObserve()
    }

    private fun setupMediaController() {
        MediaPlayerViewModel.instance.mediaController.observe(viewLifecycleOwner) { controller ->
            controller?.let {
                mediaController = it
            }
        }
    }

    private fun setupObserve() {
        viewModel.song.observe(viewLifecycleOwner) {
            showSongInfo(it)
        }
        viewModel.mediaItem.observe(viewLifecycleOwner) { mediaItem ->
            mediaController?.let {
                it.setMediaItem(mediaItem)
                it.prepare()
                it.play()
            }
        }
    }

    private fun showSongInfo(song: Song) {
        binding.textMiniPlayerTitle.text = song.title
        binding.textMiniPlayerArtist.text = song.artist
        Glide.with(requireContext())
            .load(song.image)
            .error(R.drawable.ic_album_black)
            .circleCrop()
            .into(binding.imageMiniPlayerArtwork)
    }
}