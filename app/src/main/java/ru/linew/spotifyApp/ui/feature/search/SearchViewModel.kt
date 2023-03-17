package ru.linew.spotifyApp.ui.feature.search

import androidx.lifecycle.*
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.status.AnalysisTrackStatus
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

    private val _analysisTrackStatus = MutableLiveData<AnalysisTrackStatus>(AnalysisTrackStatus.Null)
    val analysisTrackStatus: LiveData<AnalysisTrackStatus>
        get() = _analysisTrackStatus

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
        _searchPageStatus.value = SearchPageStatus.Null
    }

    fun addTrack(track: Track){
        disposeBag.add(
        spotifyRepository.saveTrack(track)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }
    fun analysisTrack(track: Track){
        _analysisTrackStatus.postValue(AnalysisTrackStatus.Loading)
        disposeBag.add(
        spotifyRepository.analysisTrack(track = track)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _analysisTrackStatus.postValue(AnalysisTrackStatus.Success(it))
            },{
                _analysisTrackStatus.postValue(AnalysisTrackStatus.Error(it))
            }))
    }


    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }

}