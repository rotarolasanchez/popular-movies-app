package com.example.imdumb.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdumb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var binding: ActivityMainBinding
    
    // Injected presenter would go here
    // @Inject lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Setup nested recyclerview
    }

    override fun showLoading() {
        // Show loading
    }

    override fun hideLoading() {
        // Hide loading
    }

    override fun showMovies(categories: List<String>) { // Placeholder type
        // Update adapter
    }

    override fun showError(message: String) {
        // Show error
    }
}
