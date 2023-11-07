package com.example.newspetapp.data.module

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
    @PUT("users/change-password/")
    suspend fun changePassword(
        @Header("Authorization") accessToken: String,
        @Body data: PasswordChange
    ): Response<Message>



}