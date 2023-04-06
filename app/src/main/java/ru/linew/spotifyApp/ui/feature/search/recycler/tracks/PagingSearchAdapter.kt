package ru.linew.spotifyApp.ui.feature.search.recycler.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.TrackItemBinding
import ru.linew.spotifyApp.ui.models.core.Track

class PagingSearchAdapter(
    private val likeItemCallback: (Track) -> Unit,
    private val unLikeItemCallback: (Track) -> Unit,
    private val trackInfoCallback: (Track) -> Unit
) :
    PagingDataAdapter<Track, PagingSearchAdapter.TrackViewHolder>(SearchItemCallback()) {

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
            holder.bind(it, likeItemCallback, unLikeItemCallback, trackInfoCallback)
        }
    }


    class TrackViewHolder(private val binding: TrackItemBinding) : ViewHolder(binding.root) {
        fun bind(
            track: Track,
            likeItemCallback: (Track) -> Unit,
            unLikeItemCallback: (Track) -> Unit,
            trackInfoCallback: (Track) -> Unit,
        ) {
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

                likeButton.setOnClickListener {
                    if (track.isLiked) {
                        track.isLiked = false
                        setDislikeIcon(likeButton)
                        unLikeItemCallback(track)
                    } else {
                        track.isLiked = true
                        setLikeIcon(likeButton)
                        likeItemCallback(track)
                    }

                }
                itemLayout.setOnClickListener {
                    trackInfoCallback(track)
                }
                if (track.isLiked) {
                    setLikeIcon(likeButton)
                } else {
                    setDislikeIcon(likeButton)
                }
            }
        }

        private fun setLikeIcon(likeButton: Button) {
            likeButton.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.baseline_favorite_24,
                0
            )
        }

        private fun setDislikeIcon(likeButton: Button) {
            likeButton.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.baseline_favorite_border_24,
                0
            )
        }
    }
}