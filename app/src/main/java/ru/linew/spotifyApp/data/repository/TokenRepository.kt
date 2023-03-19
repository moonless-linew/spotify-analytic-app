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
        return if (isTokenExpired() || forceRefresh) {
            authApi.requestToken().map {
                sharedPreferencesDataSource.saveToken(it)
                sharedPreferencesDataSource.setTokenLastModifiedTime(System.currentTimeMillis())
                it
            }
                .onErrorReturn {
                    sharedPreferencesDataSource.getToken()
                }
        } else {
            Single.just(sharedPreferencesDataSource.getToken())
        }
    }

    private fun isTokenExpired(
        token: Token = sharedPreferencesDataSource.getToken(),
        lastTimeModified: Long = sharedPreferencesDataSource.getTokenLastModifiedTime()
    ): Boolean {
        return if (token.access_token != "") {
            System.currentTimeMillis() - lastTimeModified > token.expires_in.toInt() * 1000
        } else {
            true
        }
    }

}


