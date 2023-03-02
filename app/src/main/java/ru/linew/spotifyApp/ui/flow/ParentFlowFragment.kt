package ru.linew.spotifyApp.ui.flow

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

abstract class ParentFlowFragment(
    @LayoutRes layout: Int,
    @IdRes private val navHostId: Int
): Fragment(layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(navHostId) as NavHostFragment
        val navController = navHostFragment.navController
        setupNavigation(navController)
    }
    protected open fun setupNavigation(navController: NavController){

    }
    }
