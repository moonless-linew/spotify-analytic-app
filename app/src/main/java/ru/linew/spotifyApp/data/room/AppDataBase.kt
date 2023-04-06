package ru.linew.spotifyApp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.linew.spotifyApp.data.models.room.SearchQueryEntity
import ru.linew.spotifyApp.data.models.room.TrackEntity

@Database(entities = [TrackEntity::class, SearchQueryEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun tracksDao(): TracksDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}