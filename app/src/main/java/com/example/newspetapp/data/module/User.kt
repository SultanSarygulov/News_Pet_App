package com.example.newspetapp.data.module

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long,
    val name: String,
    val email: String
): Parcelable
