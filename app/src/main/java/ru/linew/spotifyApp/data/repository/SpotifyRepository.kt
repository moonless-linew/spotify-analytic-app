package ru.linew.spotifyApp.data.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.core.Flowable
import ru.linew.spotifyApp.data.datasource.remote.SearchPagingSource
import ru.linew.spotifyApp.data.mappers.TrackMapper
import ru.linew.spotifyApp.data.utils.PagingConfigValues
import ru.linew.spotifyApp.ui.models.Track
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Inject


class SpotifyRepository @Inject constructor(
    private val searchPagingSourceFactory: SearchPagingSource.SearchPagingSourceFactory,
    private val tokenRepository: ITokenRepository
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
            .getToken(true)
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


}
