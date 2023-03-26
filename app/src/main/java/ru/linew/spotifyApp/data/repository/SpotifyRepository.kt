package ru.linew.spotifyApp.data.repository

import androidx.paging.*
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.mapAsync
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.data.datasource.local.DataBaseDataSource
import ru.linew.spotifyApp.data.datasource.remote.SearchPagingSource
import ru.linew.spotifyApp.data.datasource.remote.SpotifyDataSource
import ru.linew.spotifyApp.data.mappers.toDataBaseEntity
import ru.linew.spotifyApp.data.mappers.toUiLayer
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

    override fun getSearchPagesByString(searchString: String): Flowable<PagingData<Track>> {
        val pagingConfig = PagingConfig(
            pageSize = PagingConfigValues.LIMIT,
            enablePlaceholders = true,
            maxSize = 100,
            prefetchDistance = 5,
            initialLoadSize = PagingConfigValues.LIMIT * 2
        )

        return tokenRepository
            .getToken()
            .onErrorReturn {
                //костыль, поднимет ошибку в pager
                Token("", "", "", null)
            }
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
            .map { pagingData ->
                pagingData.mapAsync {trackResponse ->
                    dataBaseDataSource.getCountOfRequestedTrack(trackResponse.toDataBaseEntity())
                        .subscribeOn(Schedulers.io())
                        .map {
                            trackResponse.toUiLayer().apply {
                                isLiked = it >= 1
                            }
                        }
                }


            }
    }

    override fun getTrackAnalysis(track: Track): Single<TrackAnalysis> {
        return tokenRepository
            .getToken()
            .flatMap {
                spotifyDataSource.create(it).analysisTrack(track.id)
            }
            .map {
                it.toUiLayer()
            }

    }

    override fun saveTrackToLocalStorage(track: Track): Completable {
        return dataBaseDataSource.insertTrack(
            track.toDataBaseEntity()
        )
    }

    override fun loadTracksFromLocalStorage(): Single<List<Track>> {
        return dataBaseDataSource.getAllTracks()
            .map {
                it.map { item -> item.toUiLayer().apply {
                    isLiked = true
                } }
            }
    }


}
