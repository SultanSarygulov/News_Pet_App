package com.example.newspetapp.data.module

data class UserTokens (
    val accessToken: String,
    val refreshToken: String,
    val message: String,
    val errors: List<String>
)
