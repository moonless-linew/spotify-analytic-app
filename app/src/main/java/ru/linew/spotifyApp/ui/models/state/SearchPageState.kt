package ru.linew.spotifyApp.ui.models.state

import androidx.paging.PagingData
import ru.linew.spotifyApp.ui.models.core.Track

sealed class SearchPageState {
    object Null: SearchPageState()
    object Loading: SearchPageState()
    class Success(val data: PagingData<Track>): SearchPageState()
    class Error(val throwable: Throwable): SearchPageState()

}