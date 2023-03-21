package ru.linew.spotifyApp.ui.feature.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.FragmentSearchBinding
import ru.linew.spotifyApp.databinding.FragmentTrackInfoDialogBinding
import ru.linew.spotifyApp.ui.appComponent
import ru.linew.spotifyApp.ui.models.core.Track
import ru.linew.spotifyApp.ui.models.state.SearchPageState
import ru.linew.spotifyApp.ui.showErrorToast
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val disposeBag = CompositeDisposable()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(
            requireActivity().appComponent.viewModelSearch()
        )
    }
    private lateinit var adapter: PagingSearchAdapter
    private val itemClickCallback: (Track) -> Unit = {
        viewModel.addTrack(it)
    }
    private val trackInfoCallback: (Track) -> Unit = { track ->
        BottomSheetDialog(requireContext()).apply {
            val dialogBinding: FragmentTrackInfoDialogBinding =
                FragmentTrackInfoDialogBinding.inflate(layoutInflater)
            with(dialogBinding) {
                trackTitle.text = track.name
                trackArtist.text = track.artist
                Glide.with(requireContext())
                    .load(track.imageUrl)
                    .into(imageView)
            }
            setContentView(dialogBinding.root)
            show()
        }
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
        adapter = PagingSearchAdapter(itemClickCallback, trackInfoCallback)
        binding.searchResultList.adapter = adapter
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                if ((it.refresh as LoadState.Error).error is UnknownHostException) {
                    showErrorToast("Internet error")
                }
            }
        }
        binding.searchResultList.setHasFixedSize(true)
    }

    private fun setupSearchEditText() {
        disposeBag.add(
            binding.searchInput.editText!!.textChanges()
                .skipInitialValue()
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter { it.isNotEmpty() }
                .subscribe {
                    viewModel.searchTracks(it.toString())
                })

    }

    private fun setupPageStateObserver() {
        viewModel.searchPageState.observe(viewLifecycleOwner) {
            when (it) {
                is SearchPageState.Error -> {
                    showErrorToast("Internet error")
                }
                is SearchPageState.Success -> {
                    adapter.submitData(lifecycle = lifecycle, it.data)
                }
                else -> {} //nothing
            }
        }
    }


}




