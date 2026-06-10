package com.example.imdumb.presentation.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.imdumb.data.local.LocalConfig
import com.example.imdumb.databinding.ActivityDetailBinding
import com.example.imdumb.domain.model.MovieModel
import com.example.imdumb.presentation.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    @Inject lateinit var localConfig: LocalConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("movie", MovieModel::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("movie")
        }

        movie?.let { setupUI(it) }
    }

    private fun setupUI(movie: MovieModel) {
        binding.tvTitle.text = movie.title
        
        // HTML description
        val htmlDescription = "<b>Resumen:</b><br/>${movie.overview}"
        binding.tvSummary.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(htmlDescription)
        }

        // Image carousel (using poster twice for demo)
        val images = listOfNotNull(movie.posterPath, movie.posterPath)
        binding.viewPagerImages.adapter = ImageAdapter(images)

        val isRecommendationEnabled = localConfig.getBoolean("enable_recommendation", true)
        binding.btnRecommend.visibility = if (isRecommendationEnabled) View.VISIBLE else View.GONE

        binding.btnRecommend.setOnClickListener {
            RecommendBottomSheet.newInstance(movie.title).show(supportFragmentManager, "recommend")
        }
    }
}
