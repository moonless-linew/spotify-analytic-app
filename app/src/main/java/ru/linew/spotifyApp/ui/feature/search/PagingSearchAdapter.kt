package ru.linew.spotifyApp.ui.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.SearchTrackItemBinding
import ru.linew.spotifyApp.ui.models.core.Track

class PagingSearchAdapter(
    private val addItemCallback: (Track) -> Unit,
    private val trackInfoCallback: (Track) -> Unit
) :
    PagingDataAdapter<Track, PagingSearchAdapter.TrackViewHolder>(SearchItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(
            SearchTrackItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, addItemCallback, trackInfoCallback)
        }
    }


    class TrackViewHolder(private val binding: SearchTrackItemBinding) : ViewHolder(binding.root) {
        fun bind(track: Track, addItemCallback: (Track) -> Unit, trackInfoCallback: (Track) -> Unit) {
            val progressIndicator = CircularProgressDrawable(binding.iconImageView.context).apply {
                setColorSchemeColors(binding.root.context.getColor(R.color.purple_200))
                start()

            }
            with(binding) {
                Glide.with(binding.root)
                    .load(track.imageUrl)
                    .placeholder(progressIndicator)
                    .into(binding.iconImageView)
                nameTextView.text = track.name
                artistTextView.text = track.artist
                addTrackButton.setOnClickListener {
                    addItemCallback(track)
                }
                itemLayout.setOnClickListener {
                    trackInfoCallback(track)
                }
            }
        }

    }
}