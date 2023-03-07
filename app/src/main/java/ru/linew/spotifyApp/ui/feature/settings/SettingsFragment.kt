package ru.linew.spotifyApp.ui.feature.settings

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.di.appComponent
import javax.inject.Inject

class SettingsFragment: PreferenceFragmentCompat() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_screen, rootKey)

    }
}