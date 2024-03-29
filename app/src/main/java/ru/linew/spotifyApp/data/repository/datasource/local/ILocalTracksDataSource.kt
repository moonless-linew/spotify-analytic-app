package ru.linew.spotifyApp.data.repository.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.TrackEntity

interface ILocalTracksDataSource {
    fun insertTrack(trackEntity: TrackEntity): Completable
    fun getAllTracks(): Single<List<TrackEntity>>
    fun getCountOfRequestedTrack(trackEntity: TrackEntity): Single<Int>

    fun deleteTrack(trackEntity: TrackEntity): Completable
}