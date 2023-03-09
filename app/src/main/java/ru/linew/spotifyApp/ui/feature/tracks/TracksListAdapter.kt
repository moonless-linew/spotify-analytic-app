package ru.linew.spotifyApp.ui.feature.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.linew.spotifyApp.databinding.TracksTrackItemBinding
import ru.linew.spotifyApp.ui.models.core.Track

class TracksListAdapter():
    ListAdapter<Track, TracksListAdapter.TrackViewHolder>(TracksItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            TracksTrackItemBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class TrackViewHolder(private val binding: TracksTrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(track: Track){
                with(binding) {
                    nameTextView.text = track.name
                    artistTextView.text = track.artist
                }

            }

    }

}
