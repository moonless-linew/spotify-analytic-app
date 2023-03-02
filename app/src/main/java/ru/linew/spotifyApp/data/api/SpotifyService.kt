package ru.linew.spotifyApp.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.linew.spotifyApp.data.models.core.SearchResponseContainer


interface SpotifyService {
    @GET("search?type=track&market=ES&limit=5")
    fun searchTracks(@Query("q") searchString: String, @Header("Authorization") token: String): Single<SearchResponseContainer>
}