package ru.linew.spotifyApp.ui.repository

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.ui.models.core.Track


interface ISpotifyRepository {
    fun searchTracks(searchString: String): Flowable<PagingData<Track>>
    fun saveTrack(track: Track): Completable
    fun loadTracks(): Single<List<Track>>
}