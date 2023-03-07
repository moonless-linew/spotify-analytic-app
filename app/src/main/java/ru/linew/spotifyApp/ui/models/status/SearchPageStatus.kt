package ru.linew.spotifyApp.ui.models.status

import androidx.paging.PagingData
import ru.linew.spotifyApp.ui.models.Track

sealed class SearchPageStatus {

    object Loading: SearchPageStatus()
    class Success(val data: PagingData<Track>): SearchPageStatus()
    class Error(val throwable: Throwable): SearchPageStatus()

}