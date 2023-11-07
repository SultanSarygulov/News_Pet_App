package com.example.newspetapp.presentation._auth.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.databinding.FragmentLoginBinding
import com.example.newspetapp.databinding.FragmentNewPasswordBinding
import com.example.newspetapp.presentation.MainActivity
import com.example.newspetapp.presentation._auth.welcome.WelcomeFragmentDirections

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginUserButton.isEnabled = false

        binding.loginEmail.addTextChangedListener{

            binding.loginUserButton.isEnabled = areFieldsFilled()
        }

        binding.loginPasswordEt.addTextChangedListener{

            binding.loginUserButton.isEnabled = areFieldsFilled()
        }

        binding.loginUserButton.setOnClickListener {

            if(!areFieldsFilled()){
                return@setOnClickListener
            }

            if(!isDataCorrect()) {
                binding.loginErrorMessage.visibility = View.VISIBLE
                binding.loginUserButton.isEnabled = false
                return@setOnClickListener
            }

            binding.loginErrorMessage.visibility = View.GONE

            val authIntent = Intent(requireContext(), MainActivity::class.java)
            authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(authIntent)
        }

        binding.createButton.setOnClickListener {


            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)

        }

        binding.backupLogin.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun areFieldsFilled(): Boolean {

        if (binding.loginEmail.text.toString().isEmpty() ||
            binding.loginPasswordEt.text.toString().isEmpty()){

            return false
        }

        return true
    }

    private fun isDataCorrect(): Boolean {


        if (binding.loginEmail.text.toString() == "ikses.projekto@gmail.com" &&
            binding.loginPasswordEt.text.toString() == "123456")

            return true

        return false
    }

}