package com.example.newspetapp.presentation._auth.new_password

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newspetapp.R
import com.example.newspetapp.databinding.FragmentNewPasswordBinding
import com.example.newspetapp.databinding.FragmentWelcomeBinding
import com.example.newspetapp.presentation._auth.register.RegisterFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding
    private val viewModel by viewModel<NewPasswordViewModel>()
    private val args by navArgs<NewPasswordFragmentArgs>()
    private val accessToken by lazy { args.accessToken }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFieldChangeListeners()

        setObservers()

        binding.createNewPasswordButton.setOnClickListener {

            if(!areFieldsFilled()){

                return@setOnClickListener
            }

            if(passwordCorrect()){

                return@setOnClickListener
            }

            viewModel.changePassword(
                "Bearer $accessToken",
                binding.enterNewPasswordEt.text.toString(),
                binding.repeatNewPasswordEt.text.toString()
            )
        }
    }

    private fun passwordCorrect(): Boolean {

        val newPasswordText = binding.enterNewPasswordEt.text.toString().trim()
        val repeatNewPasswordText = binding.repeatNewPasswordEt.text.toString().trim()

        if (repeatNewPasswordText.isNullOrEmpty()){
            binding.repeatNewPassword.error = "Поля пустые!"
            return false
        } else if (isPasswordInvalid(repeatNewPasswordText.toString())){
            binding.repeatNewPassword.error = "Неправильный формат пароля!"
            return false
        } else if (repeatNewPasswordText.length < 8){
            binding.repeatNewPassword.error = "Пароль слишком короткий!"
            return false
        } else if ( binding.repeatNewPasswordEt.text.toString().trim() !=
            binding.enterNewPasswordEt.text.toString().trim()){
            binding.repeatNewPassword.error = "Пароли не совпадают!"
            return false
        }

        if (newPasswordText.isNullOrEmpty()){
            binding.enterNewPassword.error = "Поля пустые!"
            return false
        } else if (isPasswordInvalid(newPasswordText)){
            binding.enterNewPassword.error = "Неправильный формат пароля!"
            return false
        } else if (newPasswordText.length < 8){
            binding.enterNewPassword.error = "Пароль слишком короткий!"
            return false
        } else if (!newPasswordText.any { it.isUpperCase() }){
            binding.enterNewPassword.error = "Пароль должен иметь 1 заглавную букву!"
            return false
        } else if ( binding.repeatNewPasswordEt.text.toString().trim() !=
            binding.enterNewPasswordEt.text.toString().trim()){
            binding.enterNewPassword.error = "Пароли не совпадают!"
            return false
        }

        binding.enterNewPassword.error = null
        binding.repeatNewPassword.error = null

        return true

    }

    private fun isPasswordInvalid(password: String): Boolean{
        val regex = Regex("[^a-zA-Z0-9]")
        return regex.containsMatchIn(password)
    }

    private fun setFieldChangeListeners() {
        binding.createNewPasswordButton.isEnabled = false
        binding.enterNewPasswordEt.addTextChangedListener {

            binding.createNewPasswordButton.isEnabled = areFieldsFilled()
        }
        binding.repeatNewPasswordEt.addTextChangedListener {

            binding.createNewPasswordButton.isEnabled = areFieldsFilled()
        }
    }

    private fun setObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner){

            Toast.makeText(requireContext(), "Успешеая регистрация", Toast.LENGTH_SHORT).show()

            val action = NewPasswordFragmentDirections.actionNewPasswordFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){ error ->

            var errorText = "ERROR"



            if(binding.enterNewPasswordEt.text?.trim().toString() !=
                binding.enterNewPasswordEt.text?.trim().toString()){

                errorText = "Пароли не совпадают"
            }


            binding.newPasswordDescription.visibility = View.INVISIBLE
            binding.newPasswordErrorTxt.visibility = View.VISIBLE
            binding.newPasswordErrorTxt.text = errorText

            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
        }
    }

    private fun areFieldsFilled(): Boolean {

        if (binding.enterNewPasswordEt.text.toString().isEmpty() ||
            binding.repeatNewPasswordEt.text.toString().isEmpty()){

            return false
        }

        return true
    }

}