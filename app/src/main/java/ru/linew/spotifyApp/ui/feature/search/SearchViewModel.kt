package ru.linew.spotifyApp.ui.feature.search

import androidx.lifecycle.*
import androidx.paging.rxjava3.cachedIn
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.linew.spotifyApp.ui.models.core.SearchQuery
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.state.SearchPageState
import ru.linew.spotifyApp.ui.models.state.SearchQueryState
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

    private val _searchPageState = MutableLiveData<SearchPageState>(SearchPageState.Null)
    val searchPageState: LiveData<SearchPageState>
        get() = _searchPageState

    private val _queriesHistoryState = MutableLiveData<SearchQueryState>(SearchQueryState.Null)
    val queriesHistoryState: LiveData<SearchQueryState>
        get() = _queriesHistoryState


    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchTracks(searchString: String) {
        _searchPageState.postValue(SearchPageState.Loading)
        disposeBag.add(spotifyRepository
            .getSearchPagesByString(searchString)
            .cachedIn(viewModelScope)
            .subscribe({
                _searchPageState.postValue(SearchPageState.Success(it))
            },
                {
                    _searchPageState.postValue(SearchPageState.Error(it))
                }
            ))

    }

    fun likeTrack(track: Track) {
        disposeBag.add(
            spotifyRepository.saveTrackToLocalStorage(track)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun unLikeTrack(track: Track) {
        disposeBag.add(
            spotifyRepository.deleteTrackFromLocalStorage(track)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    fun loadQueryHistory() {
        _queriesHistoryState.postValue(SearchQueryState.Loading)
        disposeBag.add(
            spotifyRepository
                .loadHistoryQueriesFromLocalStorage()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _queriesHistoryState.postValue(SearchQueryState.Success(it))
                }, {
                    _queriesHistoryState.postValue(SearchQueryState.Error(it))
                })
        )
    }

    fun deleteQueryItem(query: SearchQuery) {
        _queriesHistoryState.postValue(SearchQueryState.Loading)
        disposeBag.add(
            spotifyRepository
                .deleteHistoryQueryFromLocalStorage(query)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadQueryHistory()
                }, {
                    _queriesHistoryState.postValue(SearchQueryState.Error(it))
                })
        )
    }

    fun addQueryItem(query: SearchQuery) {
        _queriesHistoryState.postValue(SearchQueryState.Loading)
        disposeBag.add(
            spotifyRepository
                .saveHistoryQueryToLocalStorage(query)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadQueryHistory()
                }, {
                    _queriesHistoryState.postValue(SearchQueryState.Error(it))
                })
        )
    }


    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }

}