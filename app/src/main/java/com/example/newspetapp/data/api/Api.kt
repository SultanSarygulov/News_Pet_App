package com.example.newspetapp.data.api

import com.example.newspetapp.data.module.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @Headers("Content-Type: application/json")
    @POST("users/login/")
    suspend fun loginUser(
        @Body data: UserLogin
    ): Response<Message>

    @Headers("Content-Type: application/json")
    @POST("users/register/")
    suspend fun registerUser(
        @Body data: UserRegister
    ): Response<Message>

    @Headers("Content-Type: application/json")
    @POST("users/confirm-code/")
    suspend fun confirmCode(
        @Body data: ConfirmationCode
    ): Response<Message>

    @Headers("Content-Type: application/json")
    @POST("users/resend-confirmation/")
    suspend fun resendCode(
        @Body data: UserEmail
    ): Response<UserEmail>

    @Headers("Content-Type: application/json")
    @PUT("users/change-password/")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body data: PasswordChange
    ): Response<Message>

    @GET("news/list/")
    suspend fun getArticles(
        @Header("Authorization") accessToken: String,
    ): Response<ArticlesList>

    @GET("news/list/")
    suspend fun searchArticles(
        @Header("Authorization") accessToken: String,
        @Query("search") searchQuery: String?
    ): Response<ArticlesList>

    @GET("news/list/")
    suspend fun getArticlesByCategory(
        @Header("Authorization") accessToken: String,
        @Query("category_name") category: String
    ): Response<ArticlesList>

    @GET("news/favorites-news-list/")
    suspend fun getSavedArticles(
        @Header("Authorization") accessToken: String,
    ): Response<FavouriteNews>

    @GET("news/detail/{id}/")
    suspend fun readArticle(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Response<Article>

    @Headers("Content-Type: application/json")
    @POST("news/add-to-favorites/")
    suspend fun saveArticle(
        @Header("Authorization") accessToken: String,
        @Body data: AddToFavourites
    ): Response<Message>

    @Headers("Content-Type: application/json")
    @DELETE("news/remove-from-favorites/{news_id}/")
    suspend fun removeArticle(
        @Header("Authorization") accessToken: String,
        @Path("news_id") articleId: Int
    ): Response<Message>

    @GET("users/me/")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String,
    ): Response<UserProfile>

    @Multipart
    @PUT("users/user-profile/")
    suspend fun editUserInfo(
        @Header("Authorization") accessToken: String,
        @Part("email") email: RequestBody,
        @Part("first_name") firstName: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<Message>

    @GET("news/category/")
    suspend fun getCategories(
    ): Response<List<Category>>


}