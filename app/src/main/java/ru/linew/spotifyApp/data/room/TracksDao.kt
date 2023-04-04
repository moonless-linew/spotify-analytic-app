package ru.linew.spotifyApp.data.room

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.TrackEntity


@Dao
interface TracksDao {
    @Query("SELECT * FROM tracks")
    fun getAllTracks(): Single<List<TrackEntity>>

    @Query("SELECT COUNT(*) FROM tracks WHERE id = :trackId")
    fun getCountOfRequestedTrackId(trackId: String): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(trackEntity: TrackEntity): Completable

    @Delete
    fun deleteTrack(trackEntity: TrackEntity): Completable
}