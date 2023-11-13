package com.example.newspetapp.presentation.edit

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
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
import com.example.newspetapp.data.module.UploadRequestBody
import com.example.newspetapp.data.module.UserProfile
import com.example.newspetapp.databinding.FragmentEditBinding
import com.example.newspetapp.di.Constants.TAG
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class EditFragment : Fragment(), UploadRequestBody.UploadCallback {

    private lateinit var binding: FragmentEditBinding
    private val viewModel by viewModel<EditViewModel>()
    private val preferences by inject<CustomPreferences>()

    private var selectedImageUri: Uri? = null


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

            uploadImage()
        }

        binding.editUserImageTxt.setOnClickListener {

            getImageFromGallery()
        }

    }

    private fun uploadImage() {

        if(selectedImageUri == null){
            return
        }

        val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(
            selectedImageUri!!, "r", null
        ) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(activity?.cacheDir, activity?.contentResolver!!.getFileName(selectedImageUri!!) )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val body = UploadRequestBody(file, "image", this)

        Log.d(TAG, "uploadImage: ${body}")

        val token = "Bearer ${preferences.fetchToken()}"
        viewModel.editUserInfo(
            token,
            binding.editEmail.text.toString(),
            binding.editName.text.toString(),
            MultipartBody.Part.createFormData(
                "image/jpeg",
                file.name,
                body
            )
        )

    }

    // Upload Image
    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).also {intent ->
            intent.type = "image/*"
            val mimeTypes = arrayOf("images/jpeg","images/png")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }


    }

    // Set Image to ImageButton
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){

//                val imagePath: Uri? = data?.data
//                binding.editProfilePicture.setImageURI(imagePath)
                when(requestCode){
                    IMAGE_REQUEST_CODE ->{
                        selectedImageUri = data?.data
                        binding.editProfilePicture.setImageURI(selectedImageUri)
                    }

                }

            }

    }
    override fun onProgressUpdate(percentage: Int) {
        Log.d(TAG, "onProgressUpdate: $percentage")
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

    private fun ContentResolver.getFileName(selectedImagUri: Uri): String{

        var name = ""
        val returnCursor = this.query(selectedImagUri, null, null, null, null)

        if(returnCursor != null){

            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }

        return name
    }

    companion object{
        const val IMAGE_REQUEST_CODE = 100
    }

}