package ru.linew.spotifyApp.data.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.TrackEntity
import ru.linew.spotifyApp.data.repository.datasource.local.IDataBaseDataSource
import ru.linew.spotifyApp.data.room.TracksDao
import javax.inject.Inject

class DataBaseDataSource @Inject constructor (
    private val room: TracksDao
        ): IDataBaseDataSource {

    override fun insertTrack(trackEntity: TrackEntity): Completable {
        return room.insertTrack(trackEntity)
    }

    override fun getAllTracks(): Single<List<TrackEntity>> {
        return room.getAllTracks()
    }

    override fun getCountOfRequestedTrack(trackEntity: TrackEntity): Single<Int>{
        return room.getCountOfRequestedTrackId(trackEntity.id)
    }
}