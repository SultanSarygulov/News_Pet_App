package com.example.newspetapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newspetapp.R
import com.example.newspetapp.data.module.Article
import com.example.newspetapp.databinding.ItemArticleBinding

class ArticleAdapter: ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    var onArticleClickListener: ((Article) -> Unit)? = null
    var onSavedClickListener: ((Article) -> Unit)? = null

    class ArticleViewHolder(
        private val binding:ItemArticleBinding,
        private val onArticleClickListener:((Article) -> Unit)?,
        private val onSavedClickListener:((Article) -> Unit)?
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article) = with(binding){

            articleCategory.text = article.category.name
            articleTitle.text = article.title
            articleDate.text = article.date
            Glide
                .with(root)
                .load(article.image)
                .into(articleImage)

            articleItem.setOnClickListener {

                onArticleClickListener?.invoke(article)
            }

            saveArticle.setOnClickListener {

                onSavedClickListener?.invoke(article)
            }

            if (article.is_favorite){

                saveArticle.setImageResource(R.drawable.ic_saved_true)
            } else {

                saveArticle.setImageResource(R.drawable.ic_saved_false)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding, onArticleClickListener, onSavedClickListener)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ArticleDiffCallback: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }


    }


}