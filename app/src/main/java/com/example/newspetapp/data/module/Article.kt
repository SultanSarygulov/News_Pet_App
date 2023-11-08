package com.example.newspetapp.data.module

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val category: Category,
    val title: String,
    @SerializedName("created_date")
    val date: String,
    @SerializedName("content")
    val text: String,
    val image: String,
    val is_favorite: Boolean
): Parcelable
