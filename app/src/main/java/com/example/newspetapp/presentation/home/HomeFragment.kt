package com.example.newspetapp.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newspetapp.R
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.data.module.User
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.di.Constants.TAG
import com.example.newspetapp.presentation.MainActivity.Companion.IMAGE
import com.example.newspetapp.presentation.MainActivity.Companion.SAVED_MODE
import com.example.newspetapp.presentation.MainActivity.Companion.TEXT
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val preferences by inject<CustomPreferences>()
    val token by lazy {   "Bearer ${preferences.fetchToken()}"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = "Bearer ${preferences.fetchToken()}"
        viewModel.getArticles(token)


        setChips()

        binding.searchNews.setOnQueryTextListener(this)

        setAdapter()

        binding.userProfile.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(action)
        }
    }

    private fun setAdapter() {


        val articleAdapter = ArticleAdapter()
        binding.articlesList.adapter = articleAdapter
        viewModel.articlesList.observe(viewLifecycleOwner){articlesList ->

            articleAdapter.submitList(articlesList)
        }

        articleAdapter.onArticleClickListener = { article ->
            val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article.id)
            findNavController().navigate(action)
        }

        articleAdapter.onSavedClickListener = {article ->
            val token = "Bearer ${preferences.fetchToken()}"
            if(article.is_favorite){
                viewModel.removeArticle(token, article.id)
            } else {
                viewModel.saveArticle(token, article.id)
            }
        }


    }

    private fun setChips() {

        binding.chipGroup.setOnCheckedStateChangeListener { cg, ids->


            val checkedId = try {
                ids[0]
            } catch (e: Exception){
                0
            }

            when(checkedId){
                binding.all.id -> viewModel.getArticles(token)
                binding.sport.id -> viewModel.getArticlesByCategory(token,"Спорт")
                binding.science.id -> viewModel.getArticlesByCategory(token,"Наука")
                binding.art.id -> viewModel.getArticlesByCategory(token,"Искусство")
                0 -> viewModel.getArticles(token)

            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {


        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            viewModel.searchArticles(token, query)
        }
        return true
    }


}