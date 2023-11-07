package com.example.newspetapp.presentation._auth.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.databinding.FragmentLoginBinding
import com.example.newspetapp.databinding.FragmentNewPasswordBinding
import com.example.newspetapp.presentation.MainActivity
import com.example.newspetapp.presentation._auth.register.RegisterFragmentDirections
import com.example.newspetapp.presentation._auth.welcome.WelcomeFragmentDirections
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModel<LoginViewModel>()
    private val preferences by inject<CustomPreferences>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        setTextChangedListeners()

        binding.loginUserButton.setOnClickListener {

            if(!areFieldsFilled()){
                return@setOnClickListener
            }

            binding.loginErrorMessage.visibility = View.GONE

            viewModel.loginUser(
                binding.loginEmail.text.trim().toString(),
                binding.loginPasswordEt.text?.trim().toString(),
            )
        }

        binding.createButton.setOnClickListener {


            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)

        }

        binding.backupLogin.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner){

            preferences.saveToken(it.access)

            Toast.makeText(requireContext(), "Успешеая авторизация", Toast.LENGTH_SHORT).show()

            val authIntent = Intent(requireContext(), MainActivity::class.java)
            authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(authIntent)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner){ error ->

            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
            binding.loginErrorMessage.visibility = View.VISIBLE
            binding.loginUserButton.isEnabled = false
        }
    }

    private fun setTextChangedListeners() {
        binding.loginUserButton.isEnabled = false
        binding.loginEmail.addTextChangedListener{

            binding.loginUserButton.isEnabled = areFieldsFilled()
        }
        binding.loginPasswordEt.addTextChangedListener{

            binding.loginUserButton.isEnabled = areFieldsFilled()
        }
    }

    private fun areFieldsFilled(): Boolean {

        if (binding.loginEmail.text.toString().isEmpty() ||
            binding.loginPasswordEt.text.toString().isEmpty()){

            return false
        }

        return true
    }

}