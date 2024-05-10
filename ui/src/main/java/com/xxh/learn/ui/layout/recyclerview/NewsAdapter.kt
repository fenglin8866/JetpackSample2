package com.xxh.learn.ui.layout.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xxh.learn.ui.databinding.ItemNewsLayoutBinding
import com.xxh.learn.ui.layout.data.News

class NewsAdapter(private val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemNewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val titleTV: TextView = binding.title
        private val descTV: TextView = binding.desc
        fun bind(news: News) {
            titleTV.text = news.title
            descTV.text = news.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

}