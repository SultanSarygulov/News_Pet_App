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
import androidx.core.content.ContextCompat
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
import com.example.newspetapp.di.Constants.TAG
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

        val token = "Bearer ${preferences.fetchToken()}"

        viewModel.readArticle(token, args.articleId)
        
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

            Log.d(TAG, "setObservers: ${article.is_favorite}")
            currentArticle = article
            currentArticleSaved = article.is_favorite

            setLayout(article)

            binding.articleProgressBar.visibility = View.GONE
        }
    }

    private fun setLayout(article: Article) {

        setBottomSheet(article)

        binding.content.thisArticleCategory.text = article.category.name
        binding.content.thisArticleTitle.text = article.title
        binding.content.thisArticleDate.text = "Добавлено ${article.date}"
        Glide
            .with(requireContext())
            .load(article.image)
            .into(binding.content.thisArticleImage)

        Log.d(Constants.TAG, "isSaved: ${article.is_favorite}")

        if (currentArticleSaved){
            binding.saveArticleButton.setImageResource(R.drawable.ic_saved_true)
        } else {
            binding.saveArticleButton.setImageResource(R.drawable.ic_saved_false)
        }
    }

    private fun setBottomSheet(article: Article) {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        binding.bottomSheet.bottomSheetText.text = article.text

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.appbarTitle.visibility = View.VISIBLE
                        binding.appbarTitle.text = article.title
                        binding.appBarLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.appbarTitle.visibility = View.INVISIBLE
                        binding.appBarLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                android.R.color.transparent
                            )
                        )
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d(TAG, "onSlide: ")
            }
        })

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