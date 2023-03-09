package ru.linew.spotifyApp.data.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.linew.spotifyApp.data.models.retrofit.core.SearchResponseContainer
import ru.linew.spotifyApp.data.utils.PagingConfigValues


interface SpotifyService {
    @GET("search?type=track&market=ES")
    fun searchTracks(
        @Query("q") searchString: String,
        @Query("offset") offset: Int,
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = PagingConfigValues.LIMIT
    ): Single<SearchResponseContainer>
}