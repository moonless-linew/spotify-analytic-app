package ru.linew.spotifyApp.ui.repository

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Flowable
import ru.linew.spotifyApp.ui.models.Track


interface ISpotifyRepository {
    fun searchTracks(searchString: String): Flowable<PagingData<Track>>
}