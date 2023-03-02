package ru.linew.spotifyApp.data.datasource.local

import android.content.SharedPreferences
import ru.linew.spotifyApp.data.models.auth.Token
import ru.linew.spotifyApp.data.repository.datasource.local.ISharedPreferencesDataSource
import ru.linew.spotifyApp.data.utils.SharedPreferencesKeys
import javax.inject.Inject

class SharedPreferencesDataSource @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ISharedPreferencesDataSource {
    override fun getToken(): Token {
        return Token(
            sharedPreferences.getString(SharedPreferencesKeys.ACCESS_TOKEN, "") ?: "",
            sharedPreferences.getString(SharedPreferencesKeys.TOKEN_TYPE, "") ?: "",
            sharedPreferences.getString(SharedPreferencesKeys.EXPIRES_IN, "") ?: "",
        )
    }

    override fun saveToken(token: Token) {
        sharedPreferences.edit()
            .putString(SharedPreferencesKeys.ACCESS_TOKEN, token.access_token)
            .putString(SharedPreferencesKeys.TOKEN_TYPE, token.token_type)
            .putString(SharedPreferencesKeys.EXPIRES_IN, token.expires_in)
            .apply()
    }

}