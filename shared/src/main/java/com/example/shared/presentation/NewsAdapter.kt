package com.example.shared.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.core.R
import com.example.core.databinding.ItemNewsBinding
import com.example.core.domain.model.News

class NewsAdapter(private val onBookmarkClick: (News) -> Unit) : ListAdapter<News, NewsAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        Log.d("item at position adapter fav", "Item at $position: $news")
        holder.bind(news)
        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmarked_white
                )
            )
        } else {
            ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(
                    ivBookmark.context,
                    R.drawable.ic_bookmark_white
                )
            )
        }
        ivBookmark.setOnClickListener {

            onBookmarkClick(news)

            notifyItemChanged(position)
        }
    }

    class MyViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: News) {
            binding.tvItemTitle.text = news.name
            binding.tvSummary.text = news.summary
            Glide.with(itemView.context)
                .load(news.imageLogo)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
                .diskCacheStrategy(DiskCacheStrategy.ALL) 
                .into(binding.imgPoster)

            itemView.setOnClickListener {
                Log.d("data pengirim", news.toString())
                val context = itemView.context
                val intent = Intent(context, DetailEventActivity::class.java)
                intent.putExtra(DetailEventActivity.EXTRA_PERSON, news)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<News> =
            object : DiffUtil.ItemCallback<News>() {
                override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                    return oldItem.name == newItem.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
