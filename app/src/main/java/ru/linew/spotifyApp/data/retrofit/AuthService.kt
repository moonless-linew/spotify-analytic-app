package ru.linew.retrofit.data.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import ru.linew.spotifyApp.data.models.retrofit.auth.Token

interface AuthService {
    @POST("token")
    @FormUrlEncoded
    fun getToken(@Field(("grant_type")) grantType: String = "client_credentials", @Header("Authorization") base64Token: String): Single<Token>
}