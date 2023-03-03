package ru.linew.spotifyApp.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.linew.retrofit.data.api.AuthService
import ru.linew.spotifyApp.data.retrofit.SpotifyService
import javax.inject.Named
import javax.inject.Singleton


@Module
object ApiModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }
    @Singleton
    @Provides
    @Named("auth")
    fun provideAuthRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://accounts.spotify.com/api/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideSpotifyService(retrofit: Retrofit): SpotifyService {
        return retrofit.create(SpotifyService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(@Named("auth")  retrofit: Retrofit): AuthService{
        return retrofit.create(AuthService::class.java)
    }
}