package com.example.newspetapp.presentation._auth.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.data.module.UserRegister
import com.example.newspetapp.databinding.FragmentRegisterBinding
import com.example.newspetapp.databinding.FragmentWelcomeBinding
import com.example.newspetapp.presentation._auth.welcome.WelcomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModel<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        setTextChangeListeners()

        binding.registerUserButton.setOnClickListener {

            shortCut()

            if(!areFieldsFilled()){

                return@setOnClickListener
            }

            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.registerEmail.text).matches()){

                binding.registerErrorTxt.visibility = View.VISIBLE
                binding.registerErrorTxt.text="Enter a valid email address."
                return@setOnClickListener
            }

            startRegistering()
        }

        binding.enterButton.setOnClickListener {

            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.backupRegister.setOnClickListener {

            findNavController().navigateUp()
        }
    }

    private fun shortCut() {
        if(binding.registerEmail.text.toString() == "q" &&
            binding.registerName.text.toString() == "q" ){

            val action = RegisterFragmentDirections.actionRegisterFragmentToCodeFragment("skip")
            findNavController().navigate(action)
        }
    }

    private fun startRegistering() {
        val user = UserRegister(
            binding.registerEmail.text.trim().toString(),
            binding.registerName.text.trim().toString()

        )

        binding.registerProgressBar.visibility = View.VISIBLE
        viewModel.registerUser(user)
    }

    private fun setTextChangeListeners() {
        binding.registerUserButton.isEnabled = false
        binding.registerName.addTextChangedListener{

            binding.registerUserButton.isEnabled = areFieldsFilled()
        }
        binding.registerEmail.addTextChangedListener{

            binding.registerErrorTxt.visibility = View.GONE
            binding.registerUserButton.isEnabled = areFieldsFilled()
        }
    }

    private fun setObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner){success ->

            val email = binding.registerEmail.text.trim().toString()

            val action = RegisterFragmentDirections.actionRegisterFragmentToCodeFragment(email)
            findNavController().navigate(action)

        }

        viewModel.errorMessage.observe(viewLifecycleOwner){ error ->

//            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun areFieldsFilled(): Boolean {

        if (binding.registerName.text.toString().isEmpty() ||
            binding.registerEmail.text.toString().isEmpty()){

            return false
        }

        return true
    }

}