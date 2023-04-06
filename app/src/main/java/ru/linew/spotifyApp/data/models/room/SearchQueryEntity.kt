package ru.linew.spotifyApp.data.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(indices = [Index(
    value = ["query"],
    unique = true
)], tableName = "search_history")
data class SearchQueryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo("query") val query: String,
    @ColumnInfo("created_in") val createdIn: Long
)