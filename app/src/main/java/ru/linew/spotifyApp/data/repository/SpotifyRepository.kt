package ru.linew.spotifyApp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.mapAsync
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.data.datasource.local.LocalTracksDataSource
import ru.linew.spotifyApp.data.datasource.local.SearchHistoryDataSource
import ru.linew.spotifyApp.data.datasource.remote.SearchPagingSource
import ru.linew.spotifyApp.data.datasource.remote.SpotifyDataSource
import ru.linew.spotifyApp.data.mappers.toDataBaseEntity
import ru.linew.spotifyApp.data.mappers.toSearchQueryEntity
import ru.linew.spotifyApp.data.mappers.toTrackEntity
import ru.linew.spotifyApp.data.mappers.toUiLayer
import ru.linew.spotifyApp.data.models.retrofit.auth.Token
import ru.linew.spotifyApp.data.utils.PagingConfigValues
import ru.linew.spotifyApp.ui.models.core.SearchQuery
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.core.TrackAnalysis
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Inject


class SpotifyRepository @Inject constructor(
    private val searchPagingSourceFactory: SearchPagingSource.SearchPagingSourceFactory,
    private val tokenRepository: ITokenRepository,
    private val spotifyDataSource: SpotifyDataSource.SpotifyDataSourceFactory,
    private val localTracksDataSource: LocalTracksDataSource,
    private val searchHistoryDataSource: SearchHistoryDataSource
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
                    localTracksDataSource.getCountOfRequestedTrack(trackResponse.toDataBaseEntity())
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

    override fun saveHistoryQueryToLocalStorage(query: SearchQuery): Completable {
        return searchHistoryDataSource.insertSearchQuery(query = query.toSearchQueryEntity())
    }

    override fun loadHistoryQueriesFromLocalStorage(): Single<List<SearchQuery>> {
        return searchHistoryDataSource
            .getHistory()
            .map {
                it.map {item -> item.toUiLayer()}
            }
    }

    override fun deleteHistoryQueryFromLocalStorage(query: SearchQuery): Completable {
        return searchHistoryDataSource.deleteSearchQuery(query = query.toSearchQueryEntity())
    }

    override fun saveTrackToLocalStorage(track: Track): Completable {
        return localTracksDataSource.insertTrack(
            track.toTrackEntity()
        )
    }
    override fun deleteTrackFromLocalStorage(track: Track): Completable{
        return localTracksDataSource.deleteTrack(
            track.toTrackEntity()
        )
    }

    override fun loadTracksFromLocalStorage(): Single<List<Track>> {
        return localTracksDataSource.getAllTracks()
            .map {
                it.map { item -> item.toUiLayer().apply {
                    isLiked = true
                } }
            }
    }




}
