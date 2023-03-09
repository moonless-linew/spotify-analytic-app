package ru.linew.spotifyApp.ui.models.status

import ru.linew.spotifyApp.ui.models.core.Track

sealed class TracksListStatus {
    object Null: TracksListStatus()
    object Loading: TracksListStatus()
    class Success(val data: List<Track>): TracksListStatus()
    class Error(val throwable: Throwable): TracksListStatus()
}