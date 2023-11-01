package com.example.newspetapp.presentation.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.databinding.FragmentProfileBinding
import com.example.newspetapp.presentation.home.HomeFragmentDirections
import com.example.newspetapp.presentation.home.HomeViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.helloUser.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToSavedFragment()
            findNavController().navigate(action)
        }
    }

}