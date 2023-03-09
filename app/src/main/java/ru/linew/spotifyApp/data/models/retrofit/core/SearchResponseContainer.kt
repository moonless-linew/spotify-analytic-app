package ru.linew.spotifyApp.data.models.retrofit.core

import com.google.gson.annotations.SerializedName

data class SearchResponseContainer(
    @SerializedName("tracks")
    val searchResponse: SearchResponse
)


