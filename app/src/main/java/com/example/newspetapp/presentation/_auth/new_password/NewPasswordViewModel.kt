package com.example.newspetapp.presentation._auth.new_password

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.PasswordChange
import com.example.newspetapp.data.module.UserRegister
import com.example.newspetapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class NewPasswordViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val successMessage = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    fun changePassword(
        accessToken: String,
        password: String,
        passwordConfirm: String
    ){


        try {

            viewModelScope.launch{

                val passwordChange = PasswordChange(password, passwordConfirm)

                val response = authRepository.changePassword(accessToken, passwordChange)

                if(response.isSuccessful){

                    successMessage.postValue("YAAAAAAAAAS ")
                } else {

                    errorMessage.postValue("Ошибка")
                }
            }

        } catch (e: Exception){
            errorMessage.postValue("Что-то пошло не так")
        }
    }
}