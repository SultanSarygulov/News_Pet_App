package com.example.newspetapp.presentation._auth.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.databinding.FragmentRegisterBinding
import com.example.newspetapp.databinding.FragmentWelcomeBinding
import com.example.newspetapp.presentation._auth.welcome.WelcomeFragmentDirections

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerUserButton.setOnClickListener {

            val action = RegisterFragmentDirections.actionRegisterFragmentToCodeFragment()
            findNavController().navigate(action)
        }

        binding.switchLoginButton.setOnClickListener {

            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.backupRegister.setOnClickListener {

            findNavController().navigateUp()
        }
    }

}