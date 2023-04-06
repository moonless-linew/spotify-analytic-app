package ru.linew.spotifyApp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.linew.spotifyApp.data.room.AppDataBase
import ru.linew.spotifyApp.data.room.SearchHistoryDao
import ru.linew.spotifyApp.data.room.TracksDao
import javax.inject.Singleton

@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideTracksDao(database: AppDataBase): TracksDao{
        return database.tracksDao()
    }

    @Singleton
    @Provides
    fun provideSearchHistoryDao(database: AppDataBase): SearchHistoryDao{
        return database.searchHistoryDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDataBase{
        return Room.databaseBuilder(context,
            AppDataBase::class.java,
            "app").build()
    }
}