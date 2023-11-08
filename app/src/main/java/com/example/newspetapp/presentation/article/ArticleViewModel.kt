package com.example.newspetapp.presentation.article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class ArticleViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val successMessage = MutableLiveData<Article>()
    val errorMessage = MutableLiveData<String>()

    fun readArticle(id: Int){

        viewModelScope.launch {

            val response = newsRepository.readArticle(id)

            if(response.isSuccessful){

                successMessage.postValue(response.body())
            } else {

                errorMessage.postValue("ERROR")
            }
        }
    }
}