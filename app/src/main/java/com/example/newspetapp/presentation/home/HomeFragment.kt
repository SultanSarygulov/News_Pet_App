package com.example.newspetapp.presentation.home

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
import com.example.newspetapp.data.module.User
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.presentation.MainActivity.Companion.IMAGE
import com.example.newspetapp.presentation.MainActivity.Companion.SAVED_MODE
import com.example.newspetapp.presentation.MainActivity.Companion.TEXT
import com.example.newspetapp.presentation.MainActivity.Companion.USER_SULTAN

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

//    private val args by navArgs<HomeFragmentArgs>()
//    private val mode by lazy { args.mode }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (mode == SAVED_MODE && mode != null){
//
//            binding.userProfile.visibility == View.GONE
//        } else {
//
//            binding.userProfile.visibility == View.VISIBLE
//        }

        setAdapter()

        binding.userProfile.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(USER_SULTAN)
            findNavController().navigate(action)
        }
    }

    private fun setAdapter() {

        val articlesList = listOf(
            Article(0, "Emergency","Hello", "1 ноябрь 2023", TEXT, IMAGE),
            Article(1, "Nature","World", "2 ноябрь 2023", TEXT, IMAGE)
        )

        val articleAdapter = ArticleAdapter()
        binding.articlesList.adapter = articleAdapter
        articleAdapter.submitList(articlesList)

        articleAdapter.onArticleClickListener = { article ->
            val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
            findNavController().navigate(action)
        }


    }



}