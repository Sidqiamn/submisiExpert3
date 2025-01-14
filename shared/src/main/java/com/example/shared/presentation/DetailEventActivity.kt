
package com.example.shared.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.bumptech.glide.Glide
import com.example.core.domain.model.News
import com.example.shared.R
import com.example.shared.databinding.ActivityDetailEventBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding
    private val detailEventViewModel: DetailEventViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()

        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_PERSON, News::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PERSON)
        }

        if (news != null) {
            updateFavoriteIcon(news.isBookmarked)

            binding.tvItemName.text = news.name
            binding.tvItemDescription.text = news.summary

            Glide.with(this)
                .load(news.imageLogo)
                .into(binding.imgItemPhoto)

            binding.tvPenyelenggara.text = "Penyelenggara: ${news.ownerName}"
            binding.tvWaktu.text = "Waktu: ${news.beginTime}"
            binding.tvQuota.text = "Kuota: ${news.quota}"
            binding.tvLink.text = "Link: ${news.link}"

            binding.tvDeskripsi.text = Html.fromHtml(news.description, Html.FROM_HTML_MODE_LEGACY)

            binding.btnRegister.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(news.link)
                }
                startActivity(intent)
            }

            binding.backIcon.setOnClickListener {
                finish()
            }

            binding.ivFavorite.setOnClickListener {
                val newBookmarkState = !news.isBookmarked
                updateFavoriteIcon(newBookmarkState)
                detailEventViewModel.updateBookmark(news, newBookmarkState)
            }
        }
    }

    private fun updateFavoriteIcon(isBookmarked: Boolean) {
        val iconResId = if (isBookmarked) {
            R.drawable.baseline_favorite_24
        } else {
            R.drawable.baseline_favorite_border_24
        }
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable(this, iconResId)
        )
    }

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }
}
