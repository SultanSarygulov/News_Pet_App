package com.example.newspetapp.data.repository

import com.example.newspetapp.data.api.Api
import com.example.newspetapp.data.module.*
import retrofit2.Response

class AuthRepository(private val api: Api) {

    suspend fun loginUser(user: UserLogin): Response<Message> {

        return api.loginUser(user)
    }

    suspend fun registerUser(user: UserRegister): Response<Message> {

        return api.registerUser(user)
    }

    suspend fun confirmCode(code: ConfirmationCode): Response<Message> {

        return api.confirmCode(code)
    }

    suspend fun changePassword(accessToken: String,passwordChange: PasswordChange): Response<Message>{

        return api.changePassword(accessToken, passwordChange)
    }
}