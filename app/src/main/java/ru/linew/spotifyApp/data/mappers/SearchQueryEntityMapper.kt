package ru.linew.spotifyApp.data.mappers

import ru.linew.spotifyApp.data.models.room.SearchQueryEntity
import ru.linew.spotifyApp.ui.models.core.SearchQuery

fun SearchQueryEntity.toUiLayer(): SearchQuery{
    return SearchQuery(query = query)
}