package ru.linew.spotifyApp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.retrofit.auth.Token


interface ITokenRepository {
    fun getToken(forceRefresh: Boolean = false): Single<Token>
}