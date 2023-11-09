package com.example.newspetapp.data.module

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val email: String,
    @SerializedName("first_name")
    val name: String,
    val pictures: String
): Parcelable
