package com.example.musicapplication.ui.library.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.musicapplication.R
import com.example.musicapplication.data.model.playlist.Playlist
import com.example.musicapplication.databinding.ItemPlaylistBinding

class PlaylistAdapter(
    private val listener: OnPlaylistClickListener
) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    private val _playlists = mutableListOf<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaylistBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return _playlists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(_playlists[position])
    }

    fun updatePlaylists(playlists: List<Playlist>) {
        val oldSize = _playlists.size
        _playlists.clear()
        _playlists.addAll(playlists)
        if (oldSize > _playlists.size) {
            notifyItemRangeRemoved(0, oldSize)
        }
        notifyItemRangeChanged(0, _playlists.size)
    }

    class ViewHolder(
        private val binding: ItemPlaylistBinding,
        private val listener: OnPlaylistClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(playlist: Playlist) {
            binding.textItemPlaylistName.text = playlist.name
            val playlistSize = binding.root
                .context
                .getString(R.string.text_playlist_num_of_song, playlist.songs.size)
            binding.textItemPlaylistSize.text = playlistSize
            Glide.with(binding.root)
                .load(playlist.artwork)
                .error(R.drawable.ic_album)
                .into(binding.imagePlaylistAvatar)
            binding.root.setOnClickListener {
                listener.onPlaylistClick(playlist)
            }
            binding.btnItemPlaylistOption.setOnClickListener {
                listener.onPlaylistMenuOptionClick(playlist)
            }
        }
    }

    interface OnPlaylistClickListener {
        fun onPlaylistClick(playlist: Playlist)
        fun onPlaylistMenuOptionClick(playlist: Playlist)
    }
}