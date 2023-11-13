package com.example.newspetapp.presentation.profile

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newspetapp.R
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.data.module.User
import com.example.newspetapp.data.module.UserProfile
import com.example.newspetapp.databinding.DialogLogoutBinding
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.databinding.FragmentProfileBinding
import com.example.newspetapp.databinding.SnackbarSavedBinding
import com.example.newspetapp.presentation.MainActivity
import com.example.newspetapp.presentation.MainActivity.Companion.IMAGE
import com.example.newspetapp.presentation.MainActivity.Companion.SAVED_MODE
import com.example.newspetapp.presentation.MainActivity.Companion.TEXT
import com.example.newspetapp.presentation._auth.AuthActivity
import com.example.newspetapp.presentation.home.ArticleAdapter
import com.example.newspetapp.presentation.home.HomeFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModel<ProfileViewModel>()
    private val preferences by inject<CustomPreferences>()

//    private lateinit var userProfile: UserProfile


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSavedArticles("Bearer ${preferences.fetchToken()}")

        setObservers()


        setAdapter()

        binding.showAllSaved.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToSavedFragment()
            findNavController().navigate(action)
        }

        binding.leaveProfile.setOnClickListener {

            findNavController().navigateUp()
        }

        binding.editProfile.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToEditFragment()
            findNavController().navigate(action)
        }

        binding.logoutButton.setOnClickListener {

            logout()
        }
    }

    private fun setAdapter() {
        val savedAdapter = ArticleAdapter()
        binding.savedList.adapter = savedAdapter
        viewModel.savedArticlesList.observe(viewLifecycleOwner){ articlesList ->

            savedAdapter.submitList(articlesList)
        }

        savedAdapter.onArticleClickListener = {article ->
            val action = ProfileFragmentDirections.actionProfileFragmentToArticleFragment(article.id)
            findNavController().navigate(action)
        }
        savedAdapter.onSavedClickListener = {article, isSaved ->
            val token = "Bearer ${preferences.fetchToken()}"
            if(isSaved){
                viewModel.removeArticle(token, article.id)
            } else {
                viewModel.saveArticle(token, article.id)
            }

            showSnackbar(isSaved)
        }
    }


    private fun setObservers() {
        val token = "Bearer ${preferences.fetchToken()}"
        viewModel.getUserInfo(token)

        viewModel.userInfo.observe(viewLifecycleOwner){user ->
            setLayout(user)
        }
    }

    private fun logout() {

        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val binding = DialogLogoutBinding.bind(inflater.inflate(R.layout.dialog_logout, null))
        dialogBuilder.setView(binding.root)

        binding.dialogLogoutButton.setOnClickListener {

            val logoutIntent = Intent(requireContext(), AuthActivity::class.java)
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(logoutIntent)
        }

        val logoutDialog = dialogBuilder.create()
        logoutDialog.show()
    }

    private fun setLayout(user: UserProfile) {
        binding.userName.text = user.name
        binding.userEmail.text = user.email
        Glide
            .with(requireContext())
            .load(user.pictures ?: R.drawable.ic_null_pfp)
            .into(binding.userProfilePicture)
    }

    private fun showSnackbar(saved: Boolean) {
        val snackbar = Snackbar.make(this.requireView(), "", Snackbar.LENGTH_SHORT)

        val customSnackbarView = layoutInflater.inflate(com.example.newspetapp.R.layout.snackbar_saved, null)
        val snackbarBinding = SnackbarSavedBinding.bind(customSnackbarView)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbar.view.setPadding(0, 0, 0, 0)
        snackbarLayout.addView(customSnackbarView, 0)

        snackbarBinding.snackbarDesc
        if (!saved){
            val backgroundColor = Color.parseColor("#6D8DFF")
            snackbarBinding.snackbarLayout.backgroundTintList = ColorStateList.valueOf(backgroundColor)
            snackbarBinding.snackbarDesc.text = "Новость добавлена в избранные"
        } else {
            val backgroundColor = Color.parseColor("#F34545")
            snackbarBinding.snackbarLayout.backgroundTintList = ColorStateList.valueOf(backgroundColor)
            snackbarBinding.snackbarDesc.text = "Новость удалена из избранных"
        }


        snackbar.show();

    }

}