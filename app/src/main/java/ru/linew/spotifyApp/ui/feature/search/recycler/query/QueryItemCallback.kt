package ru.linew.spotifyApp.ui.feature.search.recycler.query

import androidx.recyclerview.widget.DiffUtil
import ru.linew.spotifyApp.ui.models.core.SearchQuery

class QueryItemCallback: DiffUtil.ItemCallback<SearchQuery>() {
    override fun areItemsTheSame(oldItem: SearchQuery, newItem: SearchQuery): Boolean {
        return oldItem.query == newItem.query
    }

    override fun areContentsTheSame(oldItem: SearchQuery, newItem: SearchQuery): Boolean {
        return oldItem == newItem
    }
}