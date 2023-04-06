package ru.linew.spotifyApp.data.room

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.SearchQueryEntity


@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY created_in DESC")
    fun getHistory(): Single<List<SearchQueryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchQuery(trackEntity: SearchQueryEntity): Completable

//    @Query("UPDATE search_history SET created_in = :updateTime")
//    fun updateTime( ,newTime: Long)

    @Query("DELETE FROM search_history WHERE query=:searchQuery")
    fun deleteSearchQuery(searchQuery: String): Completable
}
