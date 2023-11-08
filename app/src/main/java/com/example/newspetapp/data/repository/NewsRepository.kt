package com.example.newspetapp.data.repository

import com.example.newspetapp.data.Api
import com.example.newspetapp.data.module.*
import retrofit2.Response

class NewsRepository(private val api: Api) {

    suspend fun getArticles(token: String): Response<ArticlesList>{

        return api.getArticles(token)
    }

    suspend fun readArticle(id: Int): Response<Article>{

        return api.readArticle(id)
    }

    suspend fun getSavedArticles(token: String): Response<FavouriteNews>{

        return api.getSavedArticles(token)
    }

    suspend fun saveArticle(token: String, articleId: AddToFavourites): Response<Message>{

        return api.saveArticle(token, articleId)
    }

    suspend fun getUserInfo(token: String): Response<UserProfile>{

        return api.getUserInfo(token)
    }

    suspend fun userProfile(token: String, user: User): Response<UserProfile>{

        return api.userProfile(token, user)
    }
}