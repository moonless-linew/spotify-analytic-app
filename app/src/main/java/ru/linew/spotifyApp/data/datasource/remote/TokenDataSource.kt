package ru.linew.spotifyApp.data.datasource.remote

import io.reactivex.rxjava3.core.Single
import ru.linew.retrofit.data.api.AuthService
import ru.linew.spotifyApp.data.models.auth.Token
import ru.linew.spotifyApp.data.repository.datasource.remote.IAuthDataSource
import javax.inject.Inject

class TokenDataSource @Inject constructor(private val authService: AuthService): IAuthDataSource {
    override fun requestToken(): Single<Token> {
        return authService.getToken()
    }

}