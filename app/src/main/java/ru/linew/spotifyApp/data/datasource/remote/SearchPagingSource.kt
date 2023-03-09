package ru.linew.spotifyApp.data.datasource.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.data.models.retrofit.auth.Token
import ru.linew.spotifyApp.data.models.retrofit.core.SearchResponse
import ru.linew.spotifyApp.data.models.retrofit.core.Track
import ru.linew.spotifyApp.data.retrofit.SpotifyService
import ru.linew.spotifyApp.data.utils.PagingConfigValues

class SearchPagingSource @AssistedInject constructor(
    private val spotifyService: SpotifyService,
    @Assisted private val apiToken: Token,
    @Assisted private val searchString: String
    ) :
    RxPagingSource<Int, Track>() {
    @AssistedFactory
    interface SearchPagingSourceFactory {
        fun create(apiToken: Token, searchString: String): SearchPagingSource
    }
    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        return 0
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Track>> {
        val offset = params.key
        return spotifyService.searchTracks(
            searchString,
            offset ?: 0,
            "Bearer " + apiToken.access_token,
        )
            .subscribeOn(Schedulers.io())
            .map {
            toLoadResult(it.searchResponse, offset ?: 0)
        }
            .onErrorReturn {
                LoadResult.Error(it)
            }

    }
    private fun toLoadResult(data: SearchResponse, offset: Int): LoadResult<Int, Track>{
        return LoadResult.Page(
            data = data.items,
            prevKey = if (offset < PagingConfigValues.LIMIT) null else offset - PagingConfigValues.LIMIT,
            nextKey = if (offset + PagingConfigValues.LIMIT >= data.total) null else offset + PagingConfigValues.LIMIT
        )
    }
}