package com.example.newspetapp.presentation.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.databinding.FragmentEditBinding
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.presentation.home.HomeViewModel

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private lateinit var viewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelEditProfile.setOnClickListener {

            findNavController().navigateUp()
        }

        binding.confirmEditProfile.setOnClickListener {

            findNavController().navigateUp()
            Toast.makeText(requireContext(), "Информация обновлена", Toast.LENGTH_SHORT).show()
        }

    }

}