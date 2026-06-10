package com.example.workmanagertest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workmanagertest.data.SyncRepository
import com.example.workmanagertest.data.WorkStatus

@Composable
fun SyncScreen(syncRepository: SyncRepository, modifier: Modifier = Modifier) {
    val syncStatus by syncRepository.getSyncWorkStatus().collectAsState(WorkStatus.NotScheduled)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título
        Text(
            text = "WorkManager + SyncAdapter",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Card de Estado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (syncStatus) {
                    is WorkStatus.Running, is WorkStatus.Queued -> MaterialTheme.colorScheme.primaryContainer
                    is WorkStatus.Success -> MaterialTheme.colorScheme.tertiaryContainer
                    is WorkStatus.Failed, is WorkStatus.Cancelled -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceContainer
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Estado de Sincronización",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = syncStatus.getDisplayText(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        // Información
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Información",
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = "• WorkManager: Maneja trabajos en background de forma eficiente",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "• SyncAdapter: Sincronización compatible con Android antiguo",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "• Sincronización periódica: Cada 15 minutos",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "• Reintentos automáticos: Hasta 3 intentos",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botones de Control
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { syncRepository.scheduleSyncNow() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !syncStatus.isRunning()
            ) {
                Text("Sincronizar Ahora")
            }

            Button(
                onClick = { syncRepository.schedulePeriodicSync() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !syncStatus.isRunning()
            ) {
                Text("Programar Sincronización Periódica")
            }

            Button(
                onClick = { syncRepository.cancelAllSyncWork() },
                modifier = Modifier.fillMaxWidth(),
                enabled = syncStatus.isRunning()
            ) {
                Text("Cancelar Sincronización")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Footer
        Text(
            text = "Última sincronización: ${syncStatus.getDisplayText()}",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )
    }
}


