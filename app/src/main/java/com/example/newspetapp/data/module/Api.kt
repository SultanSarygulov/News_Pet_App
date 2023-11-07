package com.example.newspetapp.data.module

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

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

}