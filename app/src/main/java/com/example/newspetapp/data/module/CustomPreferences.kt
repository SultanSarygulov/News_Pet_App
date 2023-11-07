package com.example.newspetapp.data.module

import android.content.Context
import android.content.SharedPreferences
import com.example.newspetapp.di.Constants

class CustomPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("App", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun saveToken(token: String?) {
        if (token != null) {
            editor.putString(Constants.TOKEN, token).apply()
        }
    }

    fun saveRefreshToken(refreshToken: String?) {
        if (refreshToken != null) {
            editor.putString(Constants.REFRESH_TOKEN, refreshToken).apply()
        }
    }

    fun fetchToken(): String? {
        return preferences.getString(Constants.TOKEN, "token")
    }

    fun fetchRefreshToken(): String? {
        return preferences.getString(Constants.REFRESH_TOKEN, "token")
    }
}