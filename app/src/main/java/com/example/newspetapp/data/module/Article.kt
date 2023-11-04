package com.example.newspetapp.data.module

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val category: String,
    val title: String,
    val date: String,
    val text: String,
    val image: String
): Parcelable
