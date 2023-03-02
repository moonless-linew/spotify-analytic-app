package ru.linew.spotifyApp.data.repository


import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.auth.Token
import ru.linew.spotifyApp.data.repository.datasource.local.ISharedPreferencesDataSource
import ru.linew.spotifyApp.data.repository.datasource.remote.IAuthDataSource
import ru.linew.spotifyApp.ui.repository.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: IAuthDataSource,
    private val sharedPreferencesDataSource: ISharedPreferencesDataSource
) : IAuthRepository {

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
