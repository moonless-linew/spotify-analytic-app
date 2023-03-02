package ru.linew.spotifyApp.ui.feature.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.linew.spotifyApp.R
import ru.linew.spotifyApp.databinding.FragmentSearchBinding
import ru.linew.spotifyApp.di.appComponent
import ru.linew.spotifyApp.ui.models.status.SearchResultStatus
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val disposeBag = CompositeDisposable()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.Factory(
            requireActivity().appComponent.viewModelSearch()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.searchResult.observe(viewLifecycleOwner) { it ->
            when (it) {
                is SearchResultStatus.Error -> {}
                SearchResultStatus.Loading -> {}
                is SearchResultStatus.Success -> {
                    Toast.makeText(
                        requireContext(),
                        it.result[0].name,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        disposeBag.add(
            binding.searchInput.editText!!.textChanges()
                .skipInitialValue()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (it.isNotEmpty()) {
                        viewModel.searchTracks(it.toString())
                    }
                }
        )
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        disposeBag.dispose()
        super.onDestroy()
    }


}