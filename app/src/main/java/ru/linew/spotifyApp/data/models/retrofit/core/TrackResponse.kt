package ru.linew.spotifyApp.data.models.retrofit.core

data class TrackResponse(
    val album: Album,
    val artists: List<ArtistX>,
    val disc_number: Int,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_ids: ExternalIds,
    val external_urls: ExternalUrlsXXX,
    val href: String,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val name: String,
    val popularity: Int,
    val preview_url: Any,
    val restrictions: RestrictionsX,
    val track_number: Int,
    val type: String,
    val uri: String
)

