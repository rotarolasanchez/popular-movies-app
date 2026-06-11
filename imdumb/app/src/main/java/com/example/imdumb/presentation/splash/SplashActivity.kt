package com.example.imdumb.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.imdumb.data.local.LocalConfig
import com.example.imdumb.databinding.ActivitySplashBinding
import com.example.imdumb.presentation.main.MainActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var remoteConfig: FirebaseRemoteConfig

    @Inject
    lateinit var localConfig: LocalConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostrar el texto guardado localmente de inmediato mientras se descarga el nuevo
        binding.tvWelcome.text = localConfig.getConfig("welcome_text") ?: "Cargando..."
        
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        // Obligar a que la descarga ocurra ahora
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                val welcomeText = remoteConfig.getString("welcome_text")
                val homeTitle = remoteConfig.getString("home_title")
                val enableRecommendation = remoteConfig.getBoolean("enable_recommendation")
                val appTheme = remoteConfig.getString("app_theme")

                // Guardar en persistencia local para cumplir el requerimiento de "guarde y cargue"
                localConfig.saveConfig("welcome_text", welcomeText)
                localConfig.saveConfig("home_title", homeTitle)
                localConfig.saveBoolean("enable_recommendation", enableRecommendation)
                localConfig.saveConfig("app_theme", appTheme)

                // Aplicar cambios en UI de inmediato
                applyTheme(appTheme)
                binding.tvWelcome.text = welcomeText
                
                // Pequeño delay para que el usuario vea el mensaje de bienvenida actualizado
                binding.root.postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 2000)
            }
    }

    private fun applyTheme(theme: String) {
        val mode = if (theme == "dark") {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        
        if (AppCompatDelegate.getDefaultNightMode() != mode) {
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }
}
