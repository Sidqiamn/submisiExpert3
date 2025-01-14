package com.example.submisiawal2.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.domain.Result
import com.example.core.domain.model.News
import com.example.shared.presentation.NewsAdapter
import com.example.shared.presentation.NewsViewModel
import com.example.submisiawal2.databinding.ActivityMainBinding
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsAdapter = NewsAdapter { news: News ->
            if (news.isBookmarked) {
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.headlineNews.collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@MainActivity,
                                "Terjadi kesalahan: ${result.error}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }

        binding.tombolFavorite.setOnClickListener {
            try {
                installChatModule()
            } catch (e: Exception) {
                Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            }
        }

        binding.settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun installChatModule() {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val moduleChat = "favorite"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            moveToChatActivity()
            Toast.makeText(this, "Open module", Toast.LENGTH_SHORT).show()
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success installing module", Toast.LENGTH_SHORT).show()
                    moveToChatActivity()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun moveToChatActivity() {
        startActivity(Intent(this, Class.forName("com.example.favorite.FavoriteDynamicActivity")))
    }
}
