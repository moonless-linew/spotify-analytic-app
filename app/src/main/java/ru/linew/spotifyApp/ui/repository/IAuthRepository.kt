package ru.linew.spotifyApp.ui.repository

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.auth.Token


interface IAuthRepository {
    fun getToken(forceRefresh: Boolean = false): Single<Token>
}