package ru.linew.spotifyApp.ui.feature.search

import androidx.lifecycle.*
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.linew.spotifyApp.ui.models.status.SearchPageStatus
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
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private val disposeBag = CompositeDisposable()

    private val _searchPageStatus = MutableLiveData<SearchPageStatus>(SearchPageStatus.Null)
    val searchPageStatus: LiveData<SearchPageStatus>
        get() = _searchPageStatus

    fun searchTracks(searchString: String) {
        _searchPageStatus.postValue(SearchPageStatus.Loading)
        disposeBag.add(spotifyRepository
            .searchTracks(searchString)
            .subscribe({
                _searchPageStatus.postValue(SearchPageStatus.Success(it))
            },
                {
                _searchPageStatus.postValue(SearchPageStatus.Error(it))
                }
            ))
    }


    fun clearPagingData(){
        _searchPageStatus.postValue(SearchPageStatus.Null)
    }

    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }
}