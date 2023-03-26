package ru.linew.spotifyApp.ui.feature.search

import androidx.lifecycle.*
import androidx.paging.rxjava3.cachedIn
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.state.AnalysisTrackState
import ru.linew.spotifyApp.ui.models.state.SearchPageState
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

    private val _analysisTrackState = MutableLiveData<AnalysisTrackState>(AnalysisTrackState.Null)
    val analysisTrackState: LiveData<AnalysisTrackState>
        get() = _analysisTrackState



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

    fun addTrack(track: Track){
        disposeBag.add(
        spotifyRepository.saveTrackToLocalStorage(track)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }
    fun analysisTrack(track: Track){
        _analysisTrackState.postValue(AnalysisTrackState.Loading)
        disposeBag.add(
        spotifyRepository.getTrackAnalysis(track = track)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _analysisTrackState.postValue(AnalysisTrackState.Success(it))
            },{
                _analysisTrackState.postValue(AnalysisTrackState.Error(it))
            }))
    }


    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }

}