package ru.linew.spotifyApp.ui.feature.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.ui.models.Track
import ru.linew.spotifyApp.ui.models.status.SearchResultStatus
import ru.linew.spotifyApp.ui.repository.IAuthRepository
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository


class SearchViewModel @AssistedInject constructor(
    private val spotifyRepository: ISpotifyRepository
) : ViewModel() {
    @AssistedFactory
    interface SearchViewModelFactory {
        fun create(): SearchViewModel
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: SearchViewModelFactory) : ViewModelProvider.Factory {
        override fun < T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }
    private val disposeBag = CompositeDisposable()

    fun searchTracks(searchString: String): Flowable<PagingData<Track>> {
            return spotifyRepository.searchTracks(searchString)
        }


    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }
}