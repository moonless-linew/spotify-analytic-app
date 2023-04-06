package ru.linew.spotifyApp.data.repository.datasource.local

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.linew.spotifyApp.data.models.room.SearchQueryEntity

interface ISearchHistoryDataSource {
    fun insertSearchQuery(query: SearchQueryEntity): Completable
    fun getHistory(): Single<List<SearchQueryEntity>>
    fun deleteSearchQuery(query: SearchQueryEntity): Completable
}