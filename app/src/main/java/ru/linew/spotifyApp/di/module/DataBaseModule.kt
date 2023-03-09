package ru.linew.spotifyApp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.linew.spotifyApp.data.room.AppDataBase
import ru.linew.spotifyApp.data.room.TracksDao
import javax.inject.Singleton

@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideRoomDataBase(context: Context): TracksDao{
        return Room.databaseBuilder(context,
        AppDataBase::class.java,
        "tracks").build().tracksDao()
    }
}