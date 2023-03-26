package ru.linew.spotifyApp.ui.feature.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.TrackItemBinding
import ru.linew.spotifyApp.ui.models.core.Track

class TracksListAdapter :
    ListAdapter<Track, TracksListAdapter.TrackViewHolder>(TracksItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            TrackItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class TrackViewHolder(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            with(binding) {
                nameTextView.text = track.name
                artistTextView.text = track.artist
                Glide.with(binding.root.context)
                    .load(track.imageUrl)
                    .into(iconImageView)
                if (track.isLiked) {
                    likeButton.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.baseline_favorite_24,
                        0
                    )
                } else {
                    likeButton.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.baseline_favorite_border_24,
                        0
                    )
                }
            }

        }

    }

}
