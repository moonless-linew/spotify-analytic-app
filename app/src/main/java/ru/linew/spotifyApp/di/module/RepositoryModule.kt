package ru.linew.spotifyApp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.spotifyApp.data.datasource.local.SharedPreferencesSource
import ru.linew.spotifyApp.data.datasource.remote.TokenDataSource
import ru.linew.spotifyApp.data.repository.ITokenRepository
import ru.linew.spotifyApp.data.repository.SpotifyRepository
import ru.linew.spotifyApp.data.repository.TokenRepository
import ru.linew.spotifyApp.data.repository.datasource.local.ISharedPreferencesDataSource
import ru.linew.spotifyApp.data.repository.datasource.remote.IAuthDataSource
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSpotifyRepository(spotifyRepository: SpotifyRepository): ISpotifyRepository

    @Binds
    fun bindAuthRepository(tokenRepository: TokenRepository): ITokenRepository

    @Binds
    fun bindSharedPreferencesDataSource(sharedPreferencesSource: SharedPreferencesSource): ISharedPreferencesDataSource

//    @Binds
//    fun bindSpotifyApiDataSource(spotifyDataSource: SearchPagingSource): ISpotifyDataSource

    @Singleton
    @Binds
    fun bindAuthApiDataSource(tokenDataSource: TokenDataSource): IAuthDataSource
}