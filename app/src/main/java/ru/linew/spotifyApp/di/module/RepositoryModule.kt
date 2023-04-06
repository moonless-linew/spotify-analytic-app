package ru.linew.spotifyApp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.spotifyApp.data.datasource.local.LocalTracksDataSource
import ru.linew.spotifyApp.data.datasource.local.SearchHistoryDataSource
import ru.linew.spotifyApp.data.datasource.local.SharedPreferencesSource
import ru.linew.spotifyApp.data.datasource.remote.TokenDataSource
import ru.linew.spotifyApp.data.repository.ITokenRepository
import ru.linew.spotifyApp.data.repository.SpotifyRepository
import ru.linew.spotifyApp.data.repository.TokenRepository
import ru.linew.spotifyApp.data.repository.datasource.local.ILocalTracksDataSource
import ru.linew.spotifyApp.data.repository.datasource.local.ISearchHistoryDataSource
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

    @Binds
    fun bindLocalTracksDataSource(localDataSource: LocalTracksDataSource): ILocalTracksDataSource

    @Binds
    fun bindSearchHistoryDataSource(searchHistoryDataSource: SearchHistoryDataSource): ISearchHistoryDataSource

    @Singleton
    @Binds
    fun bindAuthApiDataSource(tokenDataSource: TokenDataSource): IAuthDataSource
}