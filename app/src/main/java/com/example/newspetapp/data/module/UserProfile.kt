package com.example.newspetapp.data.module

import com.google.gson.annotations.SerializedName

data class UserProfile(
    val email: String,
    @SerializedName("first_name")
    val name: String,
    val pictures: String
)
