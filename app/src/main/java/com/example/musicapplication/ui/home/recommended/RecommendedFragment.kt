package com.example.musicapplication.ui.home.recommended

import Song
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.musicapplication.R
import com.example.musicapplication.databinding.FragmentRecommendedBinding
import com.example.musicapplication.ui.PlayerBaseFragment
import com.example.musicapplication.ui.home.recommended.more.MoreRecommendedFragment
import com.example.musicapplication.ui.home.recommended.more.MoreRecommendedViewModel
import com.example.musicapplication.ui.playing.MiniPlayerViewModel

class RecommendedFragment : PlayerBaseFragment() {
    private lateinit var binding: FragmentRecommendedBinding
    private val recommendedViewModel: RecommendedViewModel by activityViewModels()
    private lateinit var adapter: SongAdapter
    private val moreRecommendedViewModel: MoreRecommendedViewModel by activityViewModels()
    private val miniPlayerViewModel: MiniPlayerViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        adapter = SongAdapter(
            object : SongAdapter.OnSongClickListener {
                override fun onClick(song: Song, index: Int) {
                    miniPlayerViewModel.setSong(song)
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    showOptionMenu(song)
                }
            }
        )
        binding.includeSongList.rvSongList.adapter = adapter
        binding.btnMoreRecommended.setOnClickListener { navigateToMoreRecommended() }
        binding.textTitleRecommended.setOnClickListener { navigateToMoreRecommended() }
    }

    private fun setupViewModel() {
        recommendedViewModel.songs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs.subList(0, 16))
            moreRecommendedViewModel.setRecommendedSongs(songs)
        }
    }
    private fun navigateToMoreRecommended() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.nav_host_fragment_activity_main,
                MoreRecommendedFragment::class.java,
                null
            )
            .addToBackStack(null)
            .commit()
    }
}
