package ru.linew.spotifyApp.ui

import android.content.Context
import ru.linew.spotifyApp.di.component.AppComponent
import ru.linew.spotifyApp.ui.app.SpotifyApp

val Context.appComponent: AppComponent
get() = when(this){
    is SpotifyApp -> appComponent
    else -> this.applicationContext.appComponent
}