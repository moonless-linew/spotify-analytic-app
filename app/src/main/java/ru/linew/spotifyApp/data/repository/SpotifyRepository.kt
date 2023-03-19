package ru.linew.spotifyApp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.datasource.local.DataBaseDataSource
import ru.linew.spotifyApp.data.datasource.remote.SearchPagingSource
import ru.linew.spotifyApp.data.datasource.remote.SpotifyDataSource
import ru.linew.spotifyApp.data.mappers.TrackAnalysisMapper
import ru.linew.spotifyApp.data.mappers.TrackMapper
import ru.linew.spotifyApp.data.models.retrofit.auth.Token
import ru.linew.spotifyApp.data.utils.PagingConfigValues
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.core.TrackAnalysis
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Inject


class SpotifyRepository @Inject constructor(
    private val searchPagingSourceFactory: SearchPagingSource.SearchPagingSourceFactory,
    private val tokenRepository: ITokenRepository,
    private val spotifyDataSource: SpotifyDataSource.SpotifyDataSourceFactory,
    private val dataBaseDataSource: DataBaseDataSource
) :
    ISpotifyRepository {

    override fun searchTracks(searchString: String): Flowable<PagingData<Track>> {
        val pagingConfig = PagingConfig(
            pageSize = PagingConfigValues.LIMIT,
            enablePlaceholders = true,
            maxSize = 100,
            prefetchDistance = 5,
            initialLoadSize = PagingConfigValues.LIMIT * 2
        )

        return tokenRepository
            .getToken()
            .toFlowable()
            .flatMap { token ->
                Pager(
                    config = pagingConfig,
                    pagingSourceFactory = {
                        searchPagingSourceFactory.create(
                            apiToken = token, searchString = searchString
                        )
                    }).flowable
            }
            .map {
                it.map { track ->
                    TrackMapper.transform(track)
                }
            }
    }

    override fun analysisTrack(track: Track): Single<TrackAnalysis> {
        return tokenRepository
            .getToken()
            .flatMap {
                spotifyDataSource.create(it).analysisTrack(track.id)
            }
            .map {
                TrackAnalysisMapper.transform(it)
            }
    }

    override fun saveTrackToLocalStorage(track: Track): Completable {
        return dataBaseDataSource.insertTrack(
            ru.linew.spotifyApp.data.models.room.TrackEntity(
                track.id,
                track.name,
                track.artist,
                track.imageUrl
            )
        )
    }

    override fun loadTracksFromLocalStorage(): Single<List<Track>> {
        return dataBaseDataSource.getAllTracks()
            .map {
                it.map { item -> Track(item.id, item.name, item.artist, item.imageUrl) }
            }
    }


}
