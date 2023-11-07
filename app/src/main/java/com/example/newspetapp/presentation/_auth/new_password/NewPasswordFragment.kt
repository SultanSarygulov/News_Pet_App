package com.example.newspetapp.presentation._auth.new_password

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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

            viewModel.changePassword(
                "Bearer $accessToken",
                binding.enterNewPasswordEt.text.toString(),
                binding.repeatNewPasswordEt.text.toString()
            )
        }
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