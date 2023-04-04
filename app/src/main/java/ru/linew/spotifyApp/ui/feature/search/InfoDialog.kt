package ru.linew.spotifyApp.ui.feature.search

import android.content.Context
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.linew.spotifyApp.databinding.FragmentTrackInfoDialogBinding
import ru.linew.spotifyApp.ui.models.core.Track

class InfoDialog(context: Context): BottomSheetDialog(context) {
    operator fun invoke(track: Track){
        val dialogBinding: FragmentTrackInfoDialogBinding =
            FragmentTrackInfoDialogBinding.inflate(layoutInflater)
        with(dialogBinding) {
            trackTitle.text = track.name
            trackArtist.text = track.artist
            Glide.with(context).load(track.imageUrl).into(imageView)
        }
        setContentView(dialogBinding.root)
        show()
    }

}