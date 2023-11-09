package com.example.newspetapp.data.module

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val email: String,
    @SerializedName("first_name")
    val name: String,
    val pictures: String?
): Parcelable
