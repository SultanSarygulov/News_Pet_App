package com.example.newspetapp.presentation.saved

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.AddToFavourites
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.repository.NewsRepository
import com.example.newspetapp.di.Constants
import kotlinx.coroutines.launch

class SavedViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val articlesList = MutableLiveData<List<Article>>()
    val errorMessage = MutableLiveData<String>()

    fun getSavedArticles(token: String){

        viewModelScope.launch {

            val response = newsRepository.getSavedArticles(token)

            if(response.isSuccessful){

                articlesList.postValue(response.body()?.results?.map { it.news })
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

    fun getArticlesByCategory(token: String, category: String){

        viewModelScope.launch {

            val response = newsRepository.getArticlesByCategory(token, category)

            if(response.isSuccessful){

                articlesList.postValue(response.body()?.results)
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

    fun searchArticles(token: String, query: String){

        viewModelScope.launch {

            val response = newsRepository.searchArticles(token, query)

            if(response.isSuccessful){

                articlesList.postValue(response.body()?.results)
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

//                articlesList.postValue(response.body())
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

//                articlesList.postValue(response.body())
                Log.d(Constants.TAG, "saveArticle: Success")
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }

}