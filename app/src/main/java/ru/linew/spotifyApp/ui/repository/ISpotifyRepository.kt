package ru.linew.spotifyApp.ui.repository

import androidx.paging.PagingData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.core.TrackAnalysis


interface ISpotifyRepository {
    fun getSearchPagesByString(searchString: String): Flowable<PagingData<Track>>
    fun saveTrackToLocalStorage(track: Track): Completable
    fun loadTracksFromLocalStorage(): Single<List<Track>>
    fun deleteTrackFromLocalStorage(track: Track): Completable
    fun getTrackAnalysis(track: Track): Single<TrackAnalysis>
}