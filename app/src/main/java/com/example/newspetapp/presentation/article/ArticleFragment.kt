package com.example.newspetapp.presentation.article

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newspetapp.R
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.data.module.CustomPreferences
import com.example.newspetapp.databinding.FragmentArticleBinding
import com.example.newspetapp.databinding.SnackbarSavedBinding
import com.example.newspetapp.di.Constants
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel by viewModel<ArticleViewModel>()
    private val preferences by inject<CustomPreferences>()

    private val args by navArgs<ArticleFragmentArgs>()
    private var currentArticleSaved by Delegates.notNull<Boolean>()
    private lateinit var currentArticle: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.readArticle(args.articleId)

        setObservers()

        binding.backButton.setOnClickListener {

            findNavController().navigateUp()
        }

        binding.saveArticleButton.setOnClickListener{

            val token = "Bearer ${preferences.fetchToken()}"
            if(currentArticleSaved){
                viewModel.removeArticle(token, currentArticle.id)
            } else {
                viewModel.saveArticle(token, currentArticle.id)
            }

            showSnackbar(currentArticleSaved)

            currentArticleSaved = !currentArticleSaved

            if (currentArticleSaved){
                binding.saveArticleButton.setImageResource(R.drawable.ic_saved_true)
            } else {
                binding.saveArticleButton.setImageResource(R.drawable.ic_saved_false)
            }


        }

    }

    private fun setObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner){article ->

            currentArticle = article
            currentArticleSaved = article.is_favorite

            setLayout(article)
        }
    }

    private fun setLayout(article: Article) {

        binding.thisArticleCategory.text = article.category.name
        binding.thisArticleTitle.text = article.title
        binding.thisArticleDate.text = "Добавлено ${article.date}"
        binding.thisArticleText.text = article.text
        Glide
            .with(requireContext())
            .load(article.image)
            .into(binding.thisArticleImage)

        Log.d(Constants.TAG, "isSaved: ${article.is_favorite}")

        if (currentArticleSaved){
            binding.saveArticleButton.setImageResource(R.drawable.ic_saved_true)
        } else {
            binding.saveArticleButton.setImageResource(R.drawable.ic_saved_false)
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

}