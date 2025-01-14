package com.example.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favorite.databinding.ActivityFavoriteDynamicBinding
import com.example.shared.presentation.NewsAdapter
import com.example.shared.presentation.NewsViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class FavoriteDynamicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteDynamicBinding
    private val viewModel: NewsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteDynamicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsAdapter2 = NewsAdapter { news ->
            if (news.isBookmarked) {
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookmarkedNews.collect { bookmarkedNews ->
                    binding.progressBar.visibility = View.VISIBLE

                    if (bookmarkedNews.isEmpty()) {

                        binding.tvNoFavorites.visibility = View.VISIBLE
                        binding.rvFavoriteVertcal.visibility = View.GONE
                    } else {

                        binding.tvNoFavorites.visibility = View.GONE
                        binding.rvFavoriteVertcal.visibility = View.VISIBLE
                        newsAdapter2.submitList(bookmarkedNews)
                    }

                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        binding.rvFavoriteVertcal.apply {
            layoutManager = LinearLayoutManager(this@FavoriteDynamicActivity)
            setHasFixedSize(true)
            adapter = newsAdapter2
        }
    }
}

