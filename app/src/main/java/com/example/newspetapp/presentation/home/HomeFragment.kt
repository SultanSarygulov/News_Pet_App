package com.example.newspetapp.presentation.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.databinding.FragmentHomeBinding
import com.example.newspetapp.databinding.SnackbarSavedBinding
import com.example.newspetapp.di.Constants.TAG
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


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
        viewModel.getCategories()

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

            if(articlesList.isEmpty()){

                binding.nothingFoundLayout.visibility = View.VISIBLE
            } else {
                binding.nothingFoundLayout.visibility = View.GONE
            }
        }

        articleAdapter.onArticleClickListener = { article ->
            val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article.id)
            findNavController().navigate(action)
        }

        articleAdapter.onSavedClickListener = {article, isSaved->
            val token = "Bearer ${preferences.fetchToken()}"
            Log.d(TAG, "setAdapter: ${article.is_favorite}")
            if(isSaved){
                viewModel.removeArticle(token, article.id)
            } else {
                viewModel.saveArticle(token, article.id)
            }

            showSnackbar(isSaved)
        }


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

    private fun setChips() {

        viewModel.categoriesList.observe(viewLifecycleOwner){ categoriesList ->

            binding.chipGroup.removeAllViews()

            categoriesList.forEach { category ->
                val chip = Chip(requireContext())
                chip.text = category.name

                binding.chipGroup.addView(chip)
            }

            binding.chipGroup.setOnCheckedStateChangeListener { cg, ids->



//                if (checkedChip != null) {
//                    val category = checkedChip.text.toString()
//                    viewModel.getArticlesByCategory(token, category)
//                } else {
//                    // No chip selected, you may want to handle this case
//                    viewModel.getArticles(token)
//                }
            }
        }

    }

    override fun onQueryTextSubmit(query: String?): Boolean {


        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (!query.isNullOrEmpty()){
            viewModel.searchArticles(token, query)
        } else {
            viewModel.getArticles(token)
        }
        return true
    }


}