package com.example.musicapplication.ui.home.recommended.more

import Song
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.databinding.FragmentMoreRecommendedBinding
import com.example.musicapplication.ui.home.recommended.SongAdapter

class MoreRecommendedFragment : Fragment() {
    private lateinit var binding: FragmentMoreRecommendedBinding
    private lateinit var adapter: SongAdapter
    private val moreRecommendedViewModel: MoreRecommendedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {

                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {

                }
            }
        )
        binding.includeMoreRecommended.rvSongList.adapter = adapter
        binding.toolbarMoreRecommended.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupObserver() {
        moreRecommendedViewModel.recommendedSongs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs)
        }
    }
}