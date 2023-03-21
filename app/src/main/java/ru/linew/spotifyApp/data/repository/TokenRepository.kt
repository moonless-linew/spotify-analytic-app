package ru.linew.spotifyApp.data.repository


import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.retrofit.auth.Token
import ru.linew.spotifyApp.data.repository.datasource.local.ISharedPreferencesDataSource
import ru.linew.spotifyApp.data.repository.datasource.remote.IAuthDataSource
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val authApi: IAuthDataSource,
    private val sharedPreferencesDataSource: ISharedPreferencesDataSource
) : ITokenRepository {

    override fun getToken(forceRefresh: Boolean): Single<Token> {
        val currentToken = sharedPreferencesDataSource.getToken()
        return if (isTokenNull(currentToken) || isTokenExpired(currentToken) || forceRefresh) {
            authApi.requestToken().map {
                it.created_in = System.currentTimeMillis()
                sharedPreferencesDataSource.saveToken(it)
                it
            }
        } else {
            Single.just(sharedPreferencesDataSource.getToken())
        }
    }

    private fun isTokenExpired(
        token: Token,
    ): Boolean {
        return if (token.access_token != "") {
            System.currentTimeMillis() - (token.created_in ?: 0) > token.expires_in.toInt() * 1000
        } else {
            true
        }
    }

    private fun isTokenNull(token: Token): Boolean {
        return (token.access_token == "")
    }

}


