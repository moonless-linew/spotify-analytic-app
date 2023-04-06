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
import ru.linew.spotifyApp.ui.feature.search.recycler.query.SearchQueryHistoryAdapter
import ru.linew.spotifyApp.ui.feature.search.recycler.tracks.PagingSearchAdapter
import ru.linew.spotifyApp.ui.models.core.SearchQuery
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.state.SearchPageState
import ru.linew.spotifyApp.ui.models.state.SearchQueryState
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
    private lateinit var queriesAdapter: SearchQueryHistoryAdapter

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

    private val openQuery: (SearchQuery) -> Unit = {query ->
        setTextToSearchBar(query.query)
    }

    private val deleteQuery: (SearchQuery) -> Unit = { query ->
        viewModel.deleteQueryItem(query)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSearchRecyclerView()
        setupQueriesHistoryRecyclerView()
        setupQueryHistoryObserver()
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

    private fun setupQueriesHistoryRecyclerView() {
        queriesAdapter = SearchQueryHistoryAdapter(deleteQueryCallback = deleteQuery, openQueryCallback = openQuery)
        binding.queryHistoryList.adapter = queriesAdapter
        viewModel.loadQueryHistory()
    }

    private fun vibrateForAction() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val vibrator = requireActivity().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(10, 125)))
        }
    }

    private fun setupSearchEditText() {

        binding.searchInput.editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && v.text.isNotEmpty()) {
                setTextToSearchBar(v.text.toString())
            }
            return@setOnEditorActionListener true
        }


    }
    private fun setTextToSearchBar(text: String){
        binding.searchInput.hide()
        binding.searchBar.text = text
        viewModel.searchTracks(text)
        viewModel.addQueryItem(SearchQuery(text))
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

    private fun setupQueryHistoryObserver(){
        viewModel.queriesHistoryState.observe(viewLifecycleOwner){
            when(it){
                is SearchQueryState.Error -> showErrorToast("Error")
                is SearchQueryState.Success -> queriesAdapter.submitList(it.data)
                else -> {}
            }
        }
    }


}




