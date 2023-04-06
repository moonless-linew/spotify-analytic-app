package ru.linew.spotifyApp.ui.feature.search.recycler.query

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.linew.spotifyApp.databinding.QueryItemBinding
import ru.linew.spotifyApp.ui.models.core.SearchQuery

class SearchQueryHistoryAdapter(
    private val deleteQueryCallback: (SearchQuery) -> Unit,
    private val openQueryCallback: (SearchQuery) -> Unit,
) : ListAdapter<SearchQuery, SearchQueryHistoryAdapter.QueryViewHolder>(QueryItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        return QueryViewHolder(
            QueryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        holder.bind(
            query = getItem(position),
            deleteQueryCallback = deleteQueryCallback,
            openQueryCallback = openQueryCallback
        )
    }


class QueryViewHolder(private val binding: QueryItemBinding) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    fun bind(
        query: SearchQuery,
        deleteQueryCallback: (SearchQuery) -> Unit,
        openQueryCallback: (SearchQuery) -> Unit
        ) {
        with(binding){
            queryText.text = query.query
            root.setOnClickListener { openQueryCallback(query) }
            deleteButton.setOnClickListener { deleteQueryCallback(query) }
        }
    }
}}