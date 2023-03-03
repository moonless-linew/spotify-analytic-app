package ru.linew.spotifyApp.data.retrofit

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.linew.spotifyApp.data.models.core.SearchResponseContainer


interface SpotifyService {
    @GET("search?type=track&market=ES&limit=20")
    fun searchTracks(
        @Query("q") searchString: String,
        @Query("offset") offset: Int,
        @Header("Authorization") token: String
    ): Single<SearchResponseContainer>
}