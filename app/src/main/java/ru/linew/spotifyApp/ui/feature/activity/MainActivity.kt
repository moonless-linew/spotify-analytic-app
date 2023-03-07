package ru.linew.spotifyApp.ui.feature.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.di.appComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.inject(this)

        setupFlowNavigation()}

    private fun setupFlowNavigation(){
        val navFragment = supportFragmentManager.findFragmentById(R.id.flowContainerView) as NavHostFragment
        navController = navFragment.navController
    }

}