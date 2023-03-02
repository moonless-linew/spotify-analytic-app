package ru.linew.spotifyApp.ui.feature.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.di.appComponent

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController


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