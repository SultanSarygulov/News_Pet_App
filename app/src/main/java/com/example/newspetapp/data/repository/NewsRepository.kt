package com.example.newspetapp.data.repository

import com.example.newspetapp.data.api.Api
import com.example.newspetapp.data.module.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class NewsRepository(private val api: Api) {

    suspend fun getArticles(token: String): Response<ArticlesList>{

        return api.getArticles(token)
    }

    suspend fun getArticlesByCategory(token: String, category: String): Response<ArticlesList>{

        return api.getArticlesByCategory(token, category)
    }

    suspend fun searchArticles(token: String, query: String): Response<ArticlesList>{

        return api.searchArticles(token, query)
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

    suspend fun removeArticle(token: String, articleId: Int): Response<Message>{

        return api.removeArticle(token, articleId)
    }

    suspend fun getUserInfo(token: String): Response<UserProfile>{

        return api.getUserInfo(token)
    }

    suspend fun editUserInfo(
        token: String,
        email: RequestBody,
        name: RequestBody,
        pictures: MultipartBody.Part
    ): Response<Message>{

        return api.editUserInfo(token, email, name, pictures)
    }
}