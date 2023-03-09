package ru.linew.spotifyApp.ui.feature.tracks

import androidx.recyclerview.widget.DiffUtil
import ru.linew.spotifyApp.ui.models.core.Track

class TracksItemCallback: DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
        return oldItem == newItem
    }

}