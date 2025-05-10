package com.example.musicapplication.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.data.model.song.Song
import com.example.musicapplication.databinding.DialogFragmentSongOptionMenuBinding
import com.example.musicapplication.databinding.ItemOptionMenuBinding
import com.example.musicapplication.utils.OptionMenuUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SongOptionMenuDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: DialogFragmentSongOptionMenuBinding
    private lateinit var adapter: MenuItemAdapter
    private val viewModel: SongOptionMenuViewModel by activityViewModels()
    private val songInfoViewModel: DialogSongInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentSongOptionMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        adapter = MenuItemAdapter(
            listener = object : MenuItemAdapter.OnOptionMenuItemClickListener {
                override fun onClick(item: MenuItem) {
                    onMenuItemClick(item)
                }
            }
        )
        binding.rvOptionMenu.adapter = adapter
    }
    private fun onMenuItemClick(item: MenuItem) {
        when(item.option) {
            OptionMenuUtils.OptionMenu.DOWNLOAD -> downloadSong()
            OptionMenuUtils.OptionMenu.VIEW_SONG_INFORMATION -> showDetailSongInfo()
            OptionMenuUtils.OptionMenu.ADD_TO_FAVOURITES -> addToFavorite()
            OptionMenuUtils.OptionMenu.ADD_TO_PLAYLIST -> addToPlaylist()
            OptionMenuUtils.OptionMenu.ADD_TO_QUEUE -> addToQueue()
            OptionMenuUtils.OptionMenu.VIEW_ARTIST -> viewArtist()
            OptionMenuUtils.OptionMenu.VIEW_ALBUM -> viewAlbum()
            OptionMenuUtils.OptionMenu.BLOCK -> block()
            OptionMenuUtils.OptionMenu.REPORT_ERROR -> report()
        }
    }
    private fun downloadSong() {
        // todo
    }

    private fun showDetailSongInfo() {
        songInfoViewModel.setSong(viewModel.song.value!!)
        DialogSongInfoFragment.newInstance().show(
            requireActivity().supportFragmentManager,
            DialogSongInfoFragment.TAG
        )
    }

    private fun addToFavorite() {
        // todo
    }

    private fun addToPlaylist() {
        val tag = DialogAddSongToPlaylistFragment.TAG
        val dialog = DialogAddSongToPlaylistFragment(
            object : DialogAddSongToPlaylistFragment.OnPlaylistSelectedListener {
                override fun onPlaylistSelected(playlist: Playlist) {
                    // todo
                }
            }
        )
        dialog.show(requireActivity().supportFragmentManager, tag)
    }

    private fun addToQueue() {
        // todo
    }

    private fun viewArtist() {
        // todo
    }

    private fun viewAlbum() {
        // todo
    }

    private fun block() {
        // todo
    }

    private fun report() {
        // todo
    }
    private fun setupObserver() {
        viewModel.optionMenuItem.observe(viewLifecycleOwner) { items ->
            adapter.updateMenuItems(items)
        }
        viewModel.song.observe(viewLifecycleOwner) {
            showSongInfo(it)
        }
    }

    private fun showSongInfo(song: Song) {
        binding.includeSongBottomSheet.textOptionItemSongTitle.text = song.title
        binding.includeSongBottomSheet.textOptionItemSongArtist.text = song.artist
        Glide.with(requireContext())
            .load(song.image)
            .error(R.drawable.ic_album_black)
            .into(binding.includeSongBottomSheet.imageOptionSongArtwork)
    }

    class MenuItemAdapter(
        private val menuItems: MutableList<MenuItem> = mutableListOf(),
        private val listener: OnOptionMenuItemClickListener
    ) : RecyclerView.Adapter<MenuItemAdapter.ViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemOptionMenuBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding, listener)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(menuItems[position])
        }

        override fun getItemCount(): Int {
            return menuItems.size
        }

        fun updateMenuItems(items: List<MenuItem>) {
            menuItems.addAll(items)
            notifyItemRangeChanged(0, menuItems.size)
        }

        class ViewHolder(
            private val binding: ItemOptionMenuBinding,
            private val listener: OnOptionMenuItemClickListener
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: MenuItem) {
                val title = binding.root.context.getString(item.menuItemTitle)
                binding.textItemMenuTitle.text = title
                Glide.with(binding.root)
                    .load(item.iconId)
                    .into(binding.imageItemMenuIcon)
                binding.root.setOnClickListener {
                    listener.onClick(item)
                }
            }
        }

        interface OnOptionMenuItemClickListener {
            fun onClick(item: MenuItem)
        }
    }

    companion object {
        val newInstance = SongOptionMenuDialogFragment()
        const val TAG = "SongOptionMenuDialogFragment"
    }
}
