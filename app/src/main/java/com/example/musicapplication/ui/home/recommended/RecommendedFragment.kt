package com.example.musicapplication.ui.home.recommended


import Song
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicapplication.databinding.FragmentRecommendedBinding

class RecommendedFragment : Fragment() {
    private lateinit var binding: FragmentRecommendedBinding
    private val recommendedViewModel: RecommendedViewModel by viewModels()
    private lateinit var adapter: SongAdapter

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
                    TODO("Not yet implemented")
                }
            },
            object : SongAdapter.OnSongOptionMenuClickListener {
                override fun onClick(song: Song) {
                    TODO("Not yet implemented")
                }
            }
        )
        binding.includeSongList.rvSongList.adapter = adapter
    }

    private fun setupViewModel() {
        recommendedViewModel.songs.observe(viewLifecycleOwner) { songs ->
            adapter.updateSongs(songs.subList(0, 16))
        }
    }
}
