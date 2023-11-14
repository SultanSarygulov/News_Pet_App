package com.example.newspetapp.presentation.article

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.AddToFavourites
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.repository.NewsRepository
import com.example.newspetapp.di.Constants
import kotlinx.coroutines.launch

class ArticleViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val successMessage = MutableLiveData<Article>()
    val errorMessage = MutableLiveData<String>()

    fun readArticle(token: String, id: Int){

        viewModelScope.launch {

            val response = newsRepository.readArticle(token, id)

            if(response.isSuccessful){

                successMessage.postValue(response.body())
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

    fun saveArticle(token: String, articleId: Int){

        viewModelScope.launch {

            val articleId = AddToFavourites(articleId)

            val response = newsRepository.saveArticle(token, articleId)

            if(response.isSuccessful){

                Log.d(Constants.TAG, "saveArticle: Success")
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

    fun removeArticle(token: String, articleId: Int){

        viewModelScope.launch {

            val response = newsRepository.removeArticle(token, articleId)

            if(response.isSuccessful){

                Log.d(Constants.TAG, "removeArticle: Success")
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }
}