package ru.linew.spotifyApp.data.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.SearchQueryEntity
import ru.linew.spotifyApp.data.repository.datasource.local.ISearchHistoryDataSource
import ru.linew.spotifyApp.data.room.SearchHistoryDao
import javax.inject.Inject

class SearchHistoryDataSource @Inject constructor(
    private val room: SearchHistoryDao
): ISearchHistoryDataSource {
    override fun insertSearchQuery(query: SearchQueryEntity): Completable {
        return room.insertSearchQuery(query)
    }

    override fun getHistory(): Single<List<SearchQueryEntity>> {
        return room.getHistory()
    }

    override fun deleteSearchQuery(query: SearchQueryEntity): Completable {
        return room.deleteSearchQuery(query.query)
    }

}