package com.example.newspetapp.presentation.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newspetapp.R
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.databinding.FragmentProfileBinding
import com.example.newspetapp.presentation.MainActivity.Companion.IMAGE
import com.example.newspetapp.presentation.MainActivity.Companion.SAVED_MODE
import com.example.newspetapp.presentation.MainActivity.Companion.TEXT
import com.example.newspetapp.presentation.home.ArticleAdapter
import com.example.newspetapp.presentation.home.HomeFragment

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    private val args by navArgs<ProfileFragmentArgs>()
    private val user by lazy { args.user }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()

        val articlesList = listOf(
            Article(0, "Emergency","Hello", "1 ноябрь 2023", TEXT, IMAGE),
            Article(1, "Nature","World", "2 ноябрь 2023", TEXT, IMAGE)
        )

        val savedAdapter = ArticleAdapter()
        binding.savedList.adapter = savedAdapter
        savedAdapter.onArticleClickListener = {article ->
            val action = ProfileFragmentDirections.actionProfileFragmentToArticleFragment(article)
            findNavController().navigate(action)
        }
        savedAdapter.submitList(articlesList)

        binding.showAllSaved.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToHomeFragment(SAVED_MODE)
            findNavController().navigate(action)
        }

        binding.editProfile.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToEditFragment()
            findNavController().navigate(action)
        }
    }

    private fun setLayout() {
        binding.userName.text = user.name
        binding.userEmail.text = user.email
    }

}