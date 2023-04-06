package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.room.SearchQueryEntity
import ru.linew.spotifyApp.ui.models.core.SearchQuery

fun SearchQuery.toSearchQueryEntity(): SearchQueryEntity{
    return SearchQueryEntity(
        query = query,
        id = null,
        createdIn = System.currentTimeMillis()
    )
}