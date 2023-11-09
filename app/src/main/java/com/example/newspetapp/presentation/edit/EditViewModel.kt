package com.example.newspetapp.presentation.edit

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.module.FavouriteNews
import com.example.newspetapp.data.module.User
import com.example.newspetapp.data.module.UserProfile
import com.example.newspetapp.data.repository.NewsRepository
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

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
        image: Bitmap
    ){

        viewModelScope.launch {

            val userProfile = User(
                email,
                name,
                "https://www.lancerto.com/media/wysiwyg/files-blog/it-girl-1.jpeg"
            )

            val response = newsRepository.editUserInfo(token, userProfile)

            if(response.isSuccessful){

                successMessage.postValue(response.body()?.message)
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

    fun fromBitmap(bitmap: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }
}