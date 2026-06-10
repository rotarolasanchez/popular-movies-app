package com.example.workmanagertest.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.workmanagertest.api.ApiService
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = try {
        Log.d(TAG, "Starting sync work...")

        // Simular trabajo de sincronización
        performSync()

        Log.d(TAG, "Sync work completed successfully")
        Result.success()
    } catch (e: Exception) {
        Log.e(TAG, "Sync work failed", e)
        if (runAttemptCount < 3) {
            // Reintentar si no hemos excedido el máximo de intentos
            Result.retry()
        } else {
            Result.failure()
        }
    }

    private suspend fun performSync() {
        // Crear cliente Retrofit
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Simular llamada a API
        try {
            // Aquí iría tu llamada real a la API
            // val response = apiService.syncData(syncData)

            // Simulación de trabajo
            delay(2000)
            Log.d(TAG, "Sync data processed successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error calling API", e)
            throw e
        }
    }

    companion object {
        private const val TAG = "SyncWorker"
        const val WORK_NAME = "sync_work"
    }
}

