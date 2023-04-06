package ru.linew.spotifyApp.ui.models.state

import ru.linew.spotifyApp.ui.models.core.SearchQuery

sealed class SearchQueryState {
    object Null : SearchQueryState()
    object Loading : SearchQueryState()
    class Success(val data: List<SearchQuery>) : SearchQueryState()
    class Error(val throwable: Throwable) : SearchQueryState()
}