package com.example.newspetapp.presentation.saved

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.databinding.FragmentSavedBinding
import com.example.newspetapp.databinding.SnackbarSavedBinding
import com.example.newspetapp.di.Constants
import com.example.newspetapp.presentation.home.ArticleAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class SavedFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentSavedBinding
    private val viewModel by viewModel<SavedViewModel>()
    private val preferences by inject<CustomPreferences>()
    val token by lazy {   "Bearer ${preferences.fetchToken()}"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = "Bearer ${preferences.fetchToken()}"
        viewModel.getSavedArticles(token)
        viewModel.getCategories()


        setChips()

//        binding.searchNews.setOnQueryTextListener(this)

        setAdapter()

        binding.userProfileSaved.setOnClickListener {

            val action = SavedFragmentDirections.actionSavedFragmentToProfileFragment()
            findNavController().navigate(action)
        }
    }

    private fun setAdapter() {

        val articleAdapter = ArticleAdapter()
        binding.savedArticlesList.adapter = articleAdapter
        viewModel.articlesList.observe(viewLifecycleOwner){articlesList ->

            articleAdapter.submitList(articlesList)
        }

        articleAdapter.onArticleClickListener = { article ->
            val action = SavedFragmentDirections.actionSavedFragmentToArticleFragment(article.id)
            findNavController().navigate(action)
        }

        articleAdapter.onSavedClickListener = {article, isSaved ->
            val token = "Bearer ${preferences.fetchToken()}"
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
            var selectedChip: Chip? = null

            categoriesList.forEach { category ->
                val chip = layoutInflater.inflate(R.layout.chip_layout, binding.chipGroup, false) as Chip
                chip.text = category.name
                chip.isClickable = true

                binding.chipGroup.addView(chip)

                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        // A chip is selected, perform any actions you need
                        // For example, you can store the selected category or trigger some functionality
                        val selectedCategory = category.name

                        Log.d(Constants.TAG, "setChips: $selectedCategory")
                        selectedChip?.isChecked = false

                        // Update the currently selected chip
                        selectedChip = chip

                        viewModel.getArticlesByCategory(token, selectedCategory)
                    } else {
                        // Handle the case where the chip is unchecked (optional)
                        viewModel.getSavedArticles(token)
                    }
                }
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