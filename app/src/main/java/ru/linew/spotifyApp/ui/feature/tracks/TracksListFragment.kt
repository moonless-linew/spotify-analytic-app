package ru.linew.spotifyApp.ui.feature.tracks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.FragmentTracksBinding
import ru.linew.spotifyApp.ui.appComponent
import ru.linew.spotifyApp.ui.models.state.TracksListState
import ru.linew.spotifyApp.ui.showErrorToast

class TracksListFragment : Fragment(R.layout.fragment_tracks) {
    private val binding by viewBinding(FragmentTracksBinding::bind)
    private val viewModel: TracksViewModel by viewModels {
        TracksViewModel.Factory(
            requireActivity().appComponent.viewModelTracks()
        )
    }
    lateinit var adapter: TracksListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTracksList()
        setupObservers()
    }

    private fun setupTracksList() {
        adapter = TracksListAdapter()
        binding.tracksList.adapter = adapter
        binding.tracksList.setHasFixedSize(true)
    }

    private fun setupObservers() {
        viewModel.tracksListState.observe(viewLifecycleOwner) {
            when (it) {
                is TracksListState.Error -> showErrorToast("Unknown error")
                TracksListState.Loading -> {} //nothing
                TracksListState.Null -> viewModel.getTracks()
                is TracksListState.Success -> adapter.submitList(it.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearTracksData()
    }

}
