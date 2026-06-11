package com.example.imdumb.presentation.main

import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdumb.data.local.LocalConfig
import com.example.imdumb.databinding.ActivityMainBinding
import com.example.imdumb.domain.model.CategoryModel
import com.example.imdumb.presentation.adapter.CategoryAdapter
import com.example.imdumb.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    
    @Inject lateinit var presenter: MainPresenter
    @Inject lateinit var localConfig: LocalConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val title = localConfig.getConfig("home_title") ?: "Trending Movies"
        supportActionBar?.title = title

        presenter.attachView(this)
        setupRecyclerView()
        presenter.loadMovies()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun setupRecyclerView() {
        binding.rvCategories.layoutManager = LinearLayoutManager(this)
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showMovies(categories: List<CategoryModel>) {
        binding.rvCategories.adapter = CategoryAdapter(categories) { movie ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("movie", movie)
            }
            startActivity(intent)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
