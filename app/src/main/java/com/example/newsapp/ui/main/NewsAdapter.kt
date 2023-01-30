package com.example.newsapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.models.NewsResponse.Article
import kotlinx.android.synthetic.main.news_item.view.*
import okhttp3.WebSocketListener

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

	inner class NewsHolder(private val binding: NewsItemBinding) :
		RecyclerView.ViewHolder(binding.root) {

	}

	private val callBack = object : DiffUtil.ItemCallback<Article>() {
		override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
			return oldItem.url == newItem.url
		}

		override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
			return oldItem == newItem
		}

	}

	val differ = AsyncListDiffer(this, callBack)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
		return NewsHolder(NewsItemBinding.bind(view))
	}

	override fun onBindViewHolder(holder: NewsHolder, position: Int) {
		val article = differ.currentList[position]
		holder.itemView.apply {
			Glide.with(this).load(article.urlToImage).into(article_image)
			article_image.clipToOutline = true
			article_title.text = article.title
			article_data.text = article.publishedAt
		}
	}

	override fun getItemCount() = differ.currentList.size

	private var onItemListener: ((Article) -> Unit)? = null

	fun setOnItemClickListener(listener: (Article) -> Unit){
		onItemListener = listener
	}
}