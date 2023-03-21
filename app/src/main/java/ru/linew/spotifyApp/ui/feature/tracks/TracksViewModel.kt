package ru.linew.spotifyApp.ui.feature.tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.linew.spotifyApp.data.repository.SpotifyRepository
import ru.linew.spotifyApp.ui.models.state.TracksListState

class TracksViewModel @AssistedInject constructor(
    private val spotifyRepository: SpotifyRepository
): ViewModel() {
    @AssistedFactory
    interface TracksViewModelFactory{
        fun create(): TracksViewModel
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: TracksViewModelFactory): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private val disposeBag = CompositeDisposable()

    private val _tracksListState = MutableLiveData<TracksListState>(TracksListState.Null)
    val tracksListState: LiveData<TracksListState>
        get() = _tracksListState

    fun getTracks(){
        _tracksListState.postValue(TracksListState.Loading)
        disposeBag.add(
        spotifyRepository
            .loadTracksFromLocalStorage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tracksListState.postValue(TracksListState.Success(it))
            },{
                _tracksListState.postValue(TracksListState.Error(it))
            })
        )
    }
    fun clearTracksData(){
        _tracksListState.postValue(TracksListState.Null)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}