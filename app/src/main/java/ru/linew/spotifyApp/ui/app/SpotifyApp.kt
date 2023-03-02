package ru.linew.spotifyApp.ui.app

import android.app.Application
import ru.linew.spotifyApp.di.component.AppComponent
import ru.linew.spotifyApp.di.component.DaggerAppComponent
import ru.linew.spotifyApp.di.module.ApplicationModule

class SpotifyApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(context = applicationContext)).build()

    }
}