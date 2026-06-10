package com.example.workmanagertest.sync

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProvider
import android.content.ContentProviderOperation
import android.content.ContentProviderClient
import android.content.ContentValues
import android.content.Context
import android.content.SyncResult
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

/**
 * SyncAdapter para sincronización compatible con Android < 12
 */
class SyncAdapter(
    context: Context,
    autoInitialize: Boolean
) : AbstractThreadedSyncAdapter(context, autoInitialize) {

    override fun onPerformSync(
        account: Account,
        extras: Bundle,
        authority: String,
        provider: ContentProviderClient,
        syncResult: SyncResult
    ) {
        Log.d(TAG, "onPerformSync called for account: ${account.name}")

        try {
            // Usar WorkManager para ejecutar la sincronización
            val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "sync_adapter_work",
                androidx.work.ExistingWorkPolicy.KEEP,
                syncRequest
            )

            Log.d(TAG, "Sync work enqueued from SyncAdapter")
        } catch (e: Exception) {
            Log.e(TAG, "Error performing sync", e)
            syncResult.stats.numIoExceptions++
        }
    }

    companion object {
        private const val TAG = "SyncAdapter"
    }
}

/**
 * Servicio que proporciona el SyncAdapter
 */
class SyncService : android.app.Service() {
    private var syncAdapter: SyncAdapter? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SyncService created")
        syncAdapter = SyncAdapter(this, true)
    }

    override fun onBind(intent: android.content.Intent?) = syncAdapter?.syncAdapterBinder

    companion object {
        private const val TAG = "SyncService"
    }
}

/**
 * ContentProvider dummy requerido por SyncAdapter
 */
class SyncContentProvider : ContentProvider() {
    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ) = 0

    override fun applyBatch(operations: ArrayList<ContentProviderOperation>): Array<android.content.ContentProviderResult> {
        return super.applyBatch(operations)
    }

    companion object {
        private const val TAG = "SyncContentProvider"
    }
}

