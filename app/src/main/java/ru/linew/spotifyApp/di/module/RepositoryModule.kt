package ru.linew.spotifyApp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.spotifyApp.data.datasource.local.SharedPreferencesDataSource
import ru.linew.spotifyApp.data.datasource.remote.AuthDataSource
import ru.linew.spotifyApp.data.repository.AuthRepository
import ru.linew.spotifyApp.data.repository.SpotifyRepository
import ru.linew.spotifyApp.data.repository.datasource.local.ISharedPreferencesDataSource
import ru.linew.spotifyApp.data.repository.datasource.remote.IAuthDataSource
import ru.linew.spotifyApp.ui.repository.IAuthRepository
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSpotifyRepository(spotifyRepository: SpotifyRepository): ISpotifyRepository

    @Binds
    fun bindAuthRepository(authRepository: AuthRepository): IAuthRepository

    @Binds
    fun bindSharedPreferencesDataSource(sharedPreferencesDataSource: SharedPreferencesDataSource): ISharedPreferencesDataSource

//    @Binds
//    fun bindSpotifyApiDataSource(spotifyDataSource: SearchPagingSource): ISpotifyDataSource

    @Singleton
    @Binds
    fun bindAuthApiDataSource(authDataSource: AuthDataSource): IAuthDataSource
}