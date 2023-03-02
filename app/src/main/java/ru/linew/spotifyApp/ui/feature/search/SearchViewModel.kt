package ru.linew.spotifyApp.ui.feature.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.ui.models.status.SearchResultStatus
import ru.linew.spotifyApp.ui.repository.IAuthRepository
import ru.linew.spotifyApp.ui.repository.ISpotifyRepository


class SearchViewModel @AssistedInject constructor(
    private val authRepository: IAuthRepository,
    private val spotifyRepository: ISpotifyRepository
) : ViewModel() {
    @AssistedFactory
    interface SearchViewModelFactory {
        fun create(): SearchViewModel
    }
    class Factory(private val factory: SearchViewModelFactory) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private var _searchResult = MutableLiveData<SearchResultStatus>()
    val searchResult: LiveData<SearchResultStatus>
    get() = _searchResult

    private val disposeBag = CompositeDisposable()



    fun searchTracks(searchString: String) {
        _searchResult.postValue(SearchResultStatus.Loading)
        disposeBag.add(
        spotifyRepository
            .searchTracks(searchString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _searchResult.postValue(SearchResultStatus.Success(it))
            }, {
               _searchResult.postValue(SearchResultStatus.Error(it))
            })
        )}


    override fun onCleared() {
        disposeBag.dispose()
        super.onCleared()
    }
}