package com.example.newspetapp.presentation.article

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
import com.example.newspetapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var viewModel: ArticleViewModel

    private val args by navArgs<ArticleFragmentArgs>()
    private val article by lazy { args.article }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()

        binding.backButton.setOnClickListener {

            findNavController().navigateUp()
        }

    }

    private fun setLayout() {
        binding.thisArticleCategory.text = article.category
        binding.thisArticleTitle.text = article.title
        binding.thisArticleDate.text = "Добавлено ${article.date}"
        binding.thisArticleText.text = article.text
        Glide
            .with(requireContext())
            .load(article.image)
            .into(binding.thisArticleImage)
    }

}