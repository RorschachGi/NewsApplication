package com.example.newsapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.models.Article

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    private var onItemClickListener: ((Article) -> Unit)? = null


    private val callback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    inner class NewsViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        val image = holder.itemView.findViewById<ImageView>(R.id.article_image)
        val articleTitle = holder.itemView.findViewById<TextView>(R.id.article_title)
        val articleDate = holder.itemView.findViewById<TextView>(R.id.article_date)
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(image)
            image.clipToOutline = true
            articleTitle.text = article.title
            articleDate.text = article.publishedAt

            setOnClickListener{
                onItemClickListener.let {
                    if (it != null) {
                        it(article)
                    }
                }
            }
        }

    }

    fun setOnClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }

}