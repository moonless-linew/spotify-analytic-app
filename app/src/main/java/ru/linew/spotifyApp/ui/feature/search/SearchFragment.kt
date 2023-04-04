package ru.linew.spotifyApp.ui.feature.search

import android.content.Context
import android.os.*
import android.view.View
import android.view.inputmethod.EditorInfo

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.FragmentSearchBinding
import ru.linew.spotifyApp.ui.appComponent
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.state.SearchPageState
import ru.linew.spotifyApp.ui.showErrorToast
import java.net.UnknownHostException


class SearchFragment : Fragment(R.layout.fragment_search) {
    private val disposeBag = CompositeDisposable()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(
            requireActivity().appComponent.viewModelSearch()
        )
    }
    private lateinit var searchAdapter: PagingSearchAdapter


    private val likeTrackCallback: (Track) -> Unit = { track ->
        viewModel.likeTrack(track)
        vibrateForAction()
    }
    private val unlikeTrackCallback: (Track) -> Unit = { track ->
        viewModel.unLikeTrack(track)
        vibrateForAction()
    }


    private val trackInfoCallback: (Track) -> Unit = { track ->
        InfoDialog(requireContext())(track)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSearchRecyclerView()
        setupPageStateObserver()
        setupSearchEditText()

        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }

    private fun setupSearchRecyclerView() {
        searchAdapter =
            PagingSearchAdapter(likeTrackCallback, unlikeTrackCallback, trackInfoCallback)
        binding.searchResultList.adapter = searchAdapter
        searchAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                if ((it.refresh as LoadState.Error).error is UnknownHostException) {
                    showErrorToast("Internet error")
                }
            }
        }
        binding.searchResultList.setHasFixedSize(true)
    }

    private fun setupHistoryRecyclerView() {

    }

    private fun vibrateForAction() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val vibrator = requireActivity().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(10, 125)))
        }
    }

    private fun setupSearchEditText() {

        binding.searchInput.editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val a = v.text.toString()
                binding.searchInput.hide()
                binding.searchBar.text = a
                viewModel.searchTracks(v.text.toString())
            }
            return@setOnEditorActionListener true
        }


    }


    private fun setupPageStateObserver() {
        viewModel.searchPageState.observe(viewLifecycleOwner) {
            when (it) {
                is SearchPageState.Error -> {
                    showErrorToast("Internet error")
                }
                is SearchPageState.Success -> {
                    searchAdapter.submitData(lifecycle = lifecycle, it.data)
                }
                else -> {} //nothing
            }
        }
    }


}




