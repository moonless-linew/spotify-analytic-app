package ru.linew.spotifyApp.ui.feature.search

import androidx.recyclerview.widget.DiffUtil
import ru.linew.spotifyApp.ui.models.Track

class TrackItemCallback: DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

}