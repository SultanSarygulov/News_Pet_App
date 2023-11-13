package com.example.newspetapp.presentation._auth.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.Message
import com.example.newspetapp.data.module.UserLogin
import com.example.newspetapp.data.module.UserRegister
import com.example.newspetapp.data.module.UserTokens
import com.example.newspetapp.data.repository.AuthRepository
import com.example.newspetapp.di.Constants.TAG
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val successMessage = MutableLiveData<Message>()
    val errorMessage = MutableLiveData<String>()

    fun loginUser(email:String, password:String ){

        try{

            viewModelScope.launch{

                val user= UserLogin(
                    email,
                    password
                )

                val response = authRepository.loginUser(user)

                if(response.isSuccessful){

                    successMessage.postValue(response.body())
                } else {

                    errorMessage.postValue("Ошибка")
                }
            }
        } catch (e: IOException){
            Log.d(TAG, "loginUser: IOException")
            errorMessage.postValue("Что-то пошло не так")
        }

    }
}