package com.example.workmanagertest.api

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

@Serializable
data class SyncData(
    val id: String,
    val timestamp: Long,
    val data: String
)

@Serializable
data class SyncResponse(
    val status: String,
    val message: String,
    val data: List<SyncData>? = null
)

interface ApiService {
    @GET("sync/status")
    suspend fun getStatus(): SyncResponse

    @POST("sync/upload")
    suspend fun uploadData(@Body data: SyncData): SyncResponse

    @GET("sync/download")
    suspend fun downloadData(): SyncResponse
}

