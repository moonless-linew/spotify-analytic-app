package ru.linew.spotifyApp.data.repository.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.Track

interface IDataBaseDataSource {
    fun insertTrack(track: Track): Completable
    fun getAllTracks(): Single<List<Track>>
}