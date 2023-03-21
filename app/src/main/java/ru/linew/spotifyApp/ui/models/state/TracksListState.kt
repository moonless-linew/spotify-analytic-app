package ru.linew.spotifyApp.ui.models.state

import ru.linew.spotifyApp.ui.models.core.Track

sealed class TracksListState {
    object Null: TracksListState()
    object Loading: TracksListState()
    class Success(val data: List<Track>): TracksListState()
    class Error(val throwable: Throwable): TracksListState()
}