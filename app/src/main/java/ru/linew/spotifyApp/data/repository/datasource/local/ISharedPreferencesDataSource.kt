package ru.linew.spotifyApp.data.repository.datasource.local

import ru.linew.spotifyApp.data.models.retrofit.auth.Token

interface ISharedPreferencesDataSource {
    fun getToken(): Token
    fun saveToken(token: Token)
}