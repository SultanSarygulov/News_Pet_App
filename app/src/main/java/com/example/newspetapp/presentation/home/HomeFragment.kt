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
import com.example.newspetapp.data.module.User
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

        binding.userProfile.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(USER_SULTAN)
            findNavController().navigate(action)
        }
    }

    private fun setAdapter() {

        val articlesList = listOf(
            Article(0, "Emergency","Hello", "1 ноябрь 2023", TEXT),
            Article(1, "Nature","World", "2 ноябрь 2023", TEXT)
        )

        val articleAdapter = ArticleAdapter()
        binding.articlesList.adapter = articleAdapter
        articleAdapter.submitList(articlesList)

        articleAdapter.onArticleClickListener = { article ->
            val action = HomeFragmentDirections.actionHomeFragmentToArticleFragment(article)
            findNavController().navigate(action)
        }


    }

    companion object{
        const val TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sapien erat, scelerisque a pharetra vitae, viverra et nisi. Integer ullamcorper erat sit amet venenatis condimentum. Curabitur nec gravida lectus, ut molestie nisi. Nunc purus velit, aliquam sed varius in, elementum eu nibh. Cras justo turpis, molestie ut nisi aliquet, feugiat placerat augue. Suspendisse potenti. Suspendisse tempus magna quis mi volutpat, non tincidunt metus gravida. Duis aliquam, sem eget finibus pulvinar, arcu ligula vehicula felis, eget laoreet erat nisl nec arcu. Pellentesque erat libero, posuere non tempus sit amet, finibus at augue. Vestibulum sed ornare enim. Aliquam tincidunt dictum turpis quis gravida. Phasellus interdum tristique tellus, eget pulvinar lectus hendrerit vitae. Nulla a viverra diam. Donec quis auctor lorem."
        val USER_SULTAN = User(0, "Sultan", "sultan@gmail.kg")
    }

}