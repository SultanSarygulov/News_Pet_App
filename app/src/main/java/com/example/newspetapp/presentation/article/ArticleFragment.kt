package com.example.newspetapp.presentation.article

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.example.newspetapp.databinding.FragmentArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel by viewModel<ArticleViewModel>()

    private val args by navArgs<ArticleFragmentArgs>()
//    private val article by lazy { args.article }

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

    }

    private fun setObservers() {
        viewModel.successMessage.observe(viewLifecycleOwner){article ->

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
    }

}