package ru.linew.spotifyApp.ui.flow

import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.FragmentMainBinding

class MainFlowFragment: ParentFlowFragment(R.layout.fragment_main, R.id.fragmentContainerView) {
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun setupNavigation(navController: NavController) {
        binding.bottomSheet.setupWithNavController(navController)

    }
}