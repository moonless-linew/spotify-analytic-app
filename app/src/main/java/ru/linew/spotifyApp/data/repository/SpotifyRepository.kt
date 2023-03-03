package ru.linew.spotifyApp.data.repository
import androidx.paging.*
import androidx.paging.rxjava3.flowable
import io.reactivex.rxjava3.core.Flowable
import ru.linew.spotifyApp.data.datasource.remote.SearchPagingSource
import ru.linew.spotifyApp.data.mappers.TrackMapper
import ru.linew.spotifyApp.ui.models.Track
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Inject


class SpotifyRepository @Inject constructor(
    private val searchPagingSourceFactory: SearchPagingSource.SearchPagingSourceFactory,
    private val authRepository: AuthRepository
) :
    ISpotifyRepository {

    override fun searchTracks(searchString: String): Flowable<PagingData<Track>> {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 100,
            prefetchDistance = 5,
            initialLoadSize = 40
        )

        return authRepository
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
