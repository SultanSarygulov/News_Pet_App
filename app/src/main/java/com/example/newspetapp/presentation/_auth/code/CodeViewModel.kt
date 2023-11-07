package com.example.newspetapp.presentation._auth.code

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspetapp.data.module.ConfirmationCode
import com.example.newspetapp.data.module.Message
import com.example.newspetapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class CodeViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val successMessage = MutableLiveData<Message>()
    val errorMessage = MutableLiveData<String>()

    fun confirmCode(code: String){

        viewModelScope.launch {

            val confirmationCode = ConfirmationCode(code)

            val response = authRepository.confirmCode(confirmationCode)
            if(response.isSuccessful){

                successMessage.postValue(response.body())
            } else {

                errorMessage.postValue("Ошибка")
            }
        }
    }
}