package ru.linew.spotifyApp.data.repository


import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.auth.Token
import ru.linew.spotifyApp.data.repository.datasource.local.ISharedPreferencesDataSource
import ru.linew.spotifyApp.data.repository.datasource.remote.IAuthDataSource
import javax.inject.Inject

class TokenRepository @Inject constructor(
    private val authApi: IAuthDataSource,
    private val sharedPreferencesDataSource: ISharedPreferencesDataSource
) : ITokenRepository {

    override fun getToken(forceRefresh: Boolean): Single<Token> {
        return if(forceRefresh){
            authApi.requestToken().map {
                sharedPreferencesDataSource.saveToken(it)
                return@map it
            }
        } else{
           Single.just(sharedPreferencesDataSource.getToken())
        }
        }
    }
