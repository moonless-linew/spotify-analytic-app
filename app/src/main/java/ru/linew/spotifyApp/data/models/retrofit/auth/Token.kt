package ru.linew.spotifyApp.data.models.retrofit.auth

data class Token(
    val access_token: String,
    val token_type: String,
    val expires_in: String
)