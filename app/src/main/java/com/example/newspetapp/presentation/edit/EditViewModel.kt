package com.example.newspetapp.presentation.edit

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.UserProfile
import com.example.newspetapp.data.repository.NewsRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*


class EditViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val userInfo = MutableLiveData<UserProfile>()
    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun getUserInfo(token: String){

        viewModelScope.launch {

            val response = newsRepository.getUserInfo(token)

            if(response.isSuccessful){

                userInfo.postValue(response.body())
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

    fun editUserInfo(
        token: String,
        email: String,
        name: String,
        image: MultipartBody.Part
    ){

        viewModelScope.launch {

            val userEmail: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
            val userName: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)

            val response = newsRepository.editUserInfo(
                token,
                userEmail,
                userName,
                image
            )

            if(response.isSuccessful){

                successMessage.postValue(response.body()?.message)
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }
}
