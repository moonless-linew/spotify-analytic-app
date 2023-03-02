package ru.linew.spotifyApp.ui.models.status

import ru.linew.spotifyApp.ui.models.Track

sealed class SearchResultStatus{
    object Loading: SearchResultStatus()
    class Success(val result: List<Track>): SearchResultStatus()
    class Error(val e: Throwable): SearchResultStatus()
}
