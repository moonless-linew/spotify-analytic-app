package ru.linew.spotifyApp.data.repository.datasource.remote

import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.auth.Token

interface IAuthDataSource {
    fun requestToken(): Single<Token>
}