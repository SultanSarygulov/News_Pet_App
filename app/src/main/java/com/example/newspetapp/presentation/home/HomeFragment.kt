package com.example.newspetapp.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newspetapp.R
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {

        val articlesList = listOf(
            Article(0, "Emergency","Hello", "1 ноябрь 2023", "ФАФВАФВАФАААЫ"),
            Article(1, "Nature","World", "2 ноябрь 2023", "ФАФВАФВАФАААЫ")
        )

        val articleAdapter = ArticleAdapter()
        binding.articlesList.adapter = articleAdapter
        articleAdapter.submitList(articlesList)
    }

}