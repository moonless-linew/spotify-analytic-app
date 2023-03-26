package ru.linew.spotifyApp.di.component
import dagger.Component
import ru.linew.spotifyApp.di.module.*
import ru.linew.spotifyApp.ui.feature.activity.MainActivity
import ru.linew.spotifyApp.ui.feature.search.SearchFragment
import ru.linew.spotifyApp.ui.feature.search.SearchViewModel
import ru.linew.spotifyApp.ui.feature.settings.SettingsFragment
import ru.linew.spotifyApp.ui.feature.tracks.TracksViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, ClientModule::class, RepositoryModule::class, ApplicationModule::class, DataBaseModule::class])
interface AppComponent {
    fun inject(context: MainActivity)
    fun inject(context: SearchFragment)
    fun inject(context: SettingsFragment)

    fun viewModelSearch(): SearchViewModel.SearchViewModelFactory

    fun viewModelTracks(): TracksViewModel.TracksViewModelFactory
}
