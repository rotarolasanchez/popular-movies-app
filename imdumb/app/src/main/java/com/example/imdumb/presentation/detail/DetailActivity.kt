package com.example.imdumb.presentation.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdumb.data.local.LocalConfig
import com.example.imdumb.databinding.ActivityDetailBinding
import com.example.imdumb.domain.model.ActorModel
import com.example.imdumb.domain.model.MovieModel
import com.example.imdumb.presentation.adapter.ActorAdapter
import com.example.imdumb.presentation.adapter.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), DetailContract.View {

    private lateinit var binding: ActivityDetailBinding

    @Inject
    lateinit var presenter: DetailPresenter

    @Inject
    lateinit var localConfig: LocalConfig

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

        presenter.attachView(this)
        movie?.let { 
            setupUI(it)
            presenter.loadActors(it.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupUI(movie: MovieModel) {
        binding.tvTitle.text = movie.title
        binding.tvRating.text = "Calificación: ${movie.voteAverage}/10"
        
        // HTML description
        val htmlDescription = "<b>Resumen:</b><br/>${movie.overview}"
        binding.tvSummary.text = Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_COMPACT)

        // Image carousel
        val images = listOfNotNull(movie.posterPath, movie.backdropPath)
        binding.viewPagerImages.adapter = ImageAdapter(images)

        val isRecommendationEnabled = localConfig.getBoolean("enable_recommendation", true)
        binding.btnRecommend.visibility = if (isRecommendationEnabled) View.VISIBLE else View.GONE

        binding.btnRecommend.setOnClickListener {
            RecommendBottomSheet.newInstance(movie).show(supportFragmentManager, "recommend")
        }
    }

    override fun showActors(actors: List<ActorModel>) {
        binding.rvActors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvActors.adapter = ActorAdapter(actors)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
