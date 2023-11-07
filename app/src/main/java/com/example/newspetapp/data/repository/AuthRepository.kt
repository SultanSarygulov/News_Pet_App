package com.example.newspetapp.data.repository

import com.example.newspetapp.data.module.Api
import com.example.newspetapp.data.module.ConfirmationCode
import com.example.newspetapp.data.module.Message
import com.example.newspetapp.data.module.UserRegister
import retrofit2.Response

class AuthRepository(private val api: Api) {

    suspend fun registerUser(user: UserRegister): Response<Message> {

        return api.registerUser(user)
    }

    suspend fun confirmCode(code: ConfirmationCode): Response<Message> {

        return api.confirmCode(code)
    }
}