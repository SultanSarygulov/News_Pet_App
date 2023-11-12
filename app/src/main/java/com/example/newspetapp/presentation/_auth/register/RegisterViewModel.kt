package com.example.newspetapp.presentation._auth.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.UserRegister
import com.example.newspetapp.data.repository.AuthRepository
import com.example.newspetapp.di.Constants.TAG
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    var successMessage = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun registerUser(user: UserRegister){

        viewModelScope.launch{

            val response = authRepository.registerUser(user)

            if(response.isSuccessful){

                successMessage.postValue(true)
            } else {

                errorMessage.postValue(response.body()?.error?.email?.get(0) ?: "Неправильно введены данные")
            }
        }
    }
}