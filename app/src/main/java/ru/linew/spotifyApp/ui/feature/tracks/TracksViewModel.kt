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
import ru.linew.spotifyApp.ui.models.status.TracksListStatus

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

    private val _tracksListStatus = MutableLiveData<TracksListStatus>(TracksListStatus.Null)
    val tracksListStatus: LiveData<TracksListStatus>
        get() = _tracksListStatus

    fun getTracks(){
        _tracksListStatus.postValue(TracksListStatus.Loading)
        disposeBag.add(
        spotifyRepository
            .loadTracksFromLocalStorage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _tracksListStatus.postValue(TracksListStatus.Success(it))
            },{
                _tracksListStatus.postValue(TracksListStatus.Error(it))
            })
        )
    }
    fun clearTracksData(){
        _tracksListStatus.postValue(TracksListStatus.Null)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}