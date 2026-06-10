package com.example.workmanagertest.data

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanagertest.sync.SyncWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class SyncRepository(private val context: Context) {

    private val workManager = WorkManager.getInstance(context)

    /**
     * Programa una sincronización periódica cada 15 minutos
     */
    fun schedulePeriodicSync() {
        try {
            val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                15, TimeUnit.MINUTES
            ).build()

            workManager.enqueueUniquePeriodicWork(
                SyncWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )
            Log.d(TAG, "Periodic sync scheduled")
        } catch (e: Exception) {
            Log.e(TAG, "Error scheduling periodic sync", e)
        }
    }

    /**
     * Programa una sincronización inmediata (one-time)
     */
    fun scheduleSyncNow() {
        try {
            val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                .build()

            workManager.enqueueUniqueWork(
                "sync_now",
                ExistingWorkPolicy.KEEP,
                syncRequest
            )
            Log.d(TAG, "Immediate sync scheduled")
        } catch (e: Exception) {
            Log.e(TAG, "Error scheduling immediate sync", e)
        }
    }

    /**
     * Obtiene el estado del trabajo de sincronización como Flow
     */
    fun getSyncWorkStatus(): Flow<WorkStatus> {
        return workManager.getWorkInfosForUniqueWorkFlow(SyncWorker.WORK_NAME)
            .map { workInfoList ->
                when {
                    workInfoList.isEmpty() -> WorkStatus.NotScheduled
                    workInfoList[0].state == WorkInfo.State.ENQUEUED -> WorkStatus.Queued
                    workInfoList[0].state == WorkInfo.State.RUNNING -> WorkStatus.Running
                    workInfoList[0].state == WorkInfo.State.SUCCEEDED -> WorkStatus.Success
                    workInfoList[0].state == WorkInfo.State.FAILED -> WorkStatus.Failed
                    workInfoList[0].state == WorkInfo.State.BLOCKED -> WorkStatus.Blocked
                    workInfoList[0].state == WorkInfo.State.CANCELLED -> WorkStatus.Cancelled
                    else -> WorkStatus.Unknown
                }
            }
    }

    /**
     * Cancela todos los trabajos de sincronización
     */
    fun cancelAllSyncWork() {
        try {
            workManager.cancelUniqueWork(SyncWorker.WORK_NAME)
            Log.d(TAG, "All sync work cancelled")
        } catch (e: Exception) {
            Log.e(TAG, "Error cancelling sync work", e)
        }
    }

    companion object {
        private const val TAG = "SyncRepository"

        private var instance: SyncRepository? = null

        fun getInstance(context: Context): SyncRepository {
            return instance ?: SyncRepository(context.applicationContext).also {
                instance = it
            }
        }
    }
}

sealed class WorkStatus {
    object NotScheduled : WorkStatus()
    object Queued : WorkStatus()
    object Running : WorkStatus()
    object Success : WorkStatus()
    object Failed : WorkStatus()
    object Blocked : WorkStatus()
    object Cancelled : WorkStatus()
    object Unknown : WorkStatus()

    fun getDisplayText(): String = when (this) {
        NotScheduled -> "No programado"
        Queued -> "En cola"
        Running -> "Sincronizando..."
        Success -> "Sincronizado exitosamente"
        Failed -> "Error en sincronización"
        Blocked -> "Bloqueado"
        Cancelled -> "Cancelado"
        Unknown -> "Estado desconocido"
    }

    fun isRunning(): Boolean = this is Running || this is Queued
}

