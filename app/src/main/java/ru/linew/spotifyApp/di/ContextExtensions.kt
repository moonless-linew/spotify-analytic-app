package ru.linew.spotifyApp.di

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.linew.spotifyApp.di.component.AppComponent
import ru.linew.spotifyApp.ui.app.SpotifyApp

val Context.appComponent: AppComponent
get() = when(this){
    is SpotifyApp -> appComponent
    else -> this.applicationContext.appComponent
}
fun Fragment.showErrorToast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}