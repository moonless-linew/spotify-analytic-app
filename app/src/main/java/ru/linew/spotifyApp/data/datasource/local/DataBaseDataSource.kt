package ru.linew.spotifyApp.data.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.Track
import ru.linew.spotifyApp.data.repository.datasource.local.IDataBaseDataSource
import ru.linew.spotifyApp.data.room.TracksDao
import javax.inject.Inject

class DataBaseDataSource @Inject constructor (
    private val room: TracksDao
        ): IDataBaseDataSource {

    override fun insertTrack(track: Track): Completable {
        return room.insertTrack(track)
    }

    override fun getAllTracks(): Single<List<Track>> {
        return room.getAllTracks()
    }
}