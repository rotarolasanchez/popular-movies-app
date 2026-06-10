package com.example.imdumb.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.imdumb.presentation.main.MainActivity
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Simulating loading from Firebase
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("config")
        
        // For this demo, we'll just wait a bit and move to MainActivity
        // In a real app, we'd wait for the Firebase callback
        
        window.decorView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
