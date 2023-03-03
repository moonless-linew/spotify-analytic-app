package ru.linew.retrofit.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import ru.linew.spotifyApp.data.models.auth.Token

interface AuthService {
    @POST("token")
    @FormUrlEncoded
    @Headers("Authorization: Basic ZGYxOTYyOTIyY2UzNDNjYWFlZjJkMWFkMjc5ZGMyNTM6MmU1NzI4OTc3ZTUzNDNiMWE4NmQyZDFmNzFjNTQzMTc=")
    fun getToken(@Field(("grant_type")) grantType: String = "client_credentials"): Single<Token>
}