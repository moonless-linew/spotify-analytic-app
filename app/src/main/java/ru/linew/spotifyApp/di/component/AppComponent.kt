package ru.linew.spotifyApp.di.component
import dagger.Component
import ru.linew.spotifyApp.di.module.ApiModule
import ru.linew.spotifyApp.di.module.ApplicationModule
import ru.linew.spotifyApp.di.module.ClientModule
import ru.linew.spotifyApp.di.module.RepositoryModule
import ru.linew.spotifyApp.ui.feature.activity.MainActivity
import ru.linew.spotifyApp.ui.feature.search.SearchFragment
import ru.linew.spotifyApp.ui.feature.search.SearchViewModel
import ru.linew.spotifyApp.ui.feature.settings.SettingsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, ClientModule::class, RepositoryModule::class, ApplicationModule::class])
interface AppComponent {
    fun inject(context: MainActivity)
    fun inject(context: SearchFragment)

    fun inject(context: SettingsFragment)

    fun viewModelSearch(): SearchViewModel.SearchViewModelFactory
}
