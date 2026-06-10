package com.example.workmanagertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workmanagertest.data.SyncRepository
import com.example.workmanagertest.ui.SyncScreen
import com.example.workmanagertest.ui.theme.WorkManagerTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar repositorio de sincronización
        val syncRepository = SyncRepository.getInstance(this)
        // Programar sincronización periódica al iniciar la app
        syncRepository.schedulePeriodicSync()

        setContent {
            WorkManagerTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SyncScreen(
                        syncRepository = syncRepository,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkManagerTestTheme {
        // Preview dummy
    }
}