package com.example.musicapplication.ui.home.recommended

import Song
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.databinding.ItemSongBinding

class SongAdapter(
    private val onSongClickListener: OnSongClickListener,
    private val onSongOptionMenuClickListener: OnSongOptionMenuClickListener
) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    private val songs: MutableList<Song> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSongBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onSongClickListener, onSongOptionMenuClickListener)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(songs[position], position)
    }

    fun updateSongs(newSongs: List<Song>?) {
        newSongs?.let {
            val oldSize = songs.size
            songs.clear()
            songs.addAll(it)
            if (oldSize > songs.size) {
                notifyItemRangeRemoved(0, oldSize)
            }
            notifyItemRangeChanged(0, songs.size)
        }
    }

    class ViewHolder(
        private val binding: ItemSongBinding,
        private val onSongClickListener: OnSongClickListener,
        private val onSongOptionMenuClickListener: OnSongOptionMenuClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song, index: Int) {
            binding.textItemSongTitle.text = song.title
            binding.textItemSongArtist.text = song.artist
            Glide.with(binding.root)
                .load(song.image)
                .error(R.drawable.ic_album)
                .into(binding.imageItemSongArtwork)
            binding.root.setOnClickListener {
                onSongClickListener.onClick(song, index)
            }
            binding.btnItemSongOption.setOnClickListener {
                onSongOptionMenuClickListener.onClick(song)
            }
        }
    }

    interface OnSongClickListener {
        fun onClick(song: Song, index: Int)
    }

    interface OnSongOptionMenuClickListener {
        fun onClick(song: Song)
    }
}