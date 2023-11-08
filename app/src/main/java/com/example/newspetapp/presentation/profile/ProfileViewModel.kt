package com.example.newspetapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.module.User
import com.example.newspetapp.data.module.UserProfile
import com.example.newspetapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val savedArticlesList = MutableLiveData<List<Article>>()
    val userInfo = MutableLiveData<UserProfile>()
    val errorMessage = MutableLiveData<String>()

    fun getSavedArticles(token: String){

        viewModelScope.launch {

            val response = newsRepository.getSavedArticles(token)

            if(response.isSuccessful){

                savedArticlesList.postValue(response.body()?.results?.map { it.news })
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

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
}