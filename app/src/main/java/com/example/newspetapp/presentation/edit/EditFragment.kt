package com.example.newspetapp.presentation.edit

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newspetapp.R
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.data.module.UserProfile
import com.example.newspetapp.databinding.FragmentEditBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val viewModel by viewModel<EditViewModel>()
    private val preferences by inject<CustomPreferences>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = "Bearer ${preferences.fetchToken()}"
        viewModel.getUserInfo(token)

        viewModel.userInfo.observe(viewLifecycleOwner){user ->
            setLayout(user)
        }

        setObservers()



        binding.cancelEditProfile.setOnClickListener {

            findNavController().navigateUp()
        }

        binding.confirmEditProfile.setOnClickListener {
            val token = "Bearer ${preferences.fetchToken()}"
            viewModel.editUserInfo(
                token,
                binding.editEmail.text.toString(),
                binding.editName.text.toString(),
                binding.editProfilePicture.drawToBitmap()
            )
        }

        binding.editUserImageTxt.setOnClickListener {

            getImageFromGallery()
        }

    }

    // Upload Image
    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    // Set Image to ImageButton
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK
                && data != null){



            }
    }

    private fun setObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner){

            findNavController().navigateUp()
            Toast.makeText(requireContext(), "Информация обновлена", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLayout(user: UserProfile) {

        Glide
            .with(requireContext())
            .load(user.pictures ?: R.drawable.ic_null_pfp)
            .into(binding.editProfilePicture )
        binding.editEmail.setText(user.email)
        binding.editName.setText(user.name)

    }

    companion object{
        const val IMAGE_REQUEST_CODE = 100
    }

}