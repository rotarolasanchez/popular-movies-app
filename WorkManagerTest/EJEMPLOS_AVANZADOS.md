// Ejemplos de uso avanzado de WorkManager + SyncAdapter

// ============================================================================
// EJEMPLO 1: Configurar sincronización con restricciones de red
// ============================================================================

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

fun schedulePeriodicSyncWithConstraints(repository: SyncRepository) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)  // Solo wifi o datos móviles
        .setRequiresCharging(false)                      // ¿Requiere que esté cargando?
        .setRequiresBatteryNotLow(true)                  // Batería no baja
        .build()
    
    // Nota: Para usar constraints, necesitarías extender el repositorio
    // Este es un ejemplo conceptual
}

// ============================================================================
// EJEMPLO 2: Escuchar cambios de estado de sincronización en Compose
// ============================================================================

@Composable
fun SyncStatusObserver(repository: SyncRepository) {
    val syncStatus by repository.getSyncWorkStatus()
        .collectAsState(WorkStatus.NotScheduled)
    
    LaunchedEffect(syncStatus) {
        when (syncStatus) {
            is WorkStatus.Success -> {
                // Mostrar Snackbar de éxito
                println("¡Sincronización completada!")
            }
            is WorkStatus.Failed -> {
                // Mostrar Snackbar de error
                println("Error en sincronización")
            }
            else -> {}
        }
    }
    
    Text("Estado actual: ${syncStatus.getDisplayText()}")
}

// ============================================================================
// EJEMPLO 3: Sincronización con datos específicos
// ============================================================================

// Modificar SyncWorker.kt para aceptar datos de entrada
fun scheduleDataSyncWithPayload(repository: SyncRepository, userId: String) {
    val syncData = workDataOf(
        "user_id" to userId,
        "sync_type" to "full"
    )
    
    // Necesitarías extender SyncRepository para usar setInputData()
    // Este es un ejemplo de cómo se vería
}

// En SyncWorker.doWork():
// val userId = inputData.getString("user_id")
// val syncType = inputData.getString("sync_type")

// ============================================================================
// EJEMPLO 4: Manejar sincronización en segundo plano
// ============================================================================

// En MainActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val repository = SyncRepository.getInstance(this)
        
        // Programar la sincronización al iniciar la app
        repository.schedulePeriodicSync()
        
        // También puedes escuchar cambios de conectividad
        listenToConnectivityChanges(repository)
        
        setContent {
            WorkManagerTestTheme {
                SyncScreen(repository)
            }
        }
    }
    
    private fun listenToConnectivityChanges(repository: SyncRepository) {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) 
            as ConnectivityManager
        
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // Cuando se restaura la conectividad, sincronizar de inmediato
                repository.scheduleSyncNow()
            }
        }
        
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}

// ============================================================================
// EJEMPLO 5: Extender SyncWorker con lógica personalizada
// ============================================================================

class CustomSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    private val apiService: ApiService by lazy {
        // Inicializar Retrofit
        Retrofit.Builder()
            .baseUrl("https://api.ejemplo.com/")
            .build()
            .create(ApiService::class.java)
    }
    
    override suspend fun doWork(): Result = try {
        Log.d(TAG, "Iniciando sincronización personalizada")
        
        // Sincronizar datos del usuario
        syncUserData()
        
        // Sincronizar configuración
        syncSettings()
        
        // Sincronizar cambios locales
        syncLocalChanges()
        
        Log.d(TAG, "Sincronización completada exitosamente")
        Result.success()
    } catch (e: Exception) {
        Log.e(TAG, "Error en sincronización", e)
        if (runAttemptCount < 3) {
            Result.retry()
        } else {
            Result.failure()
        }
    }
    
    private suspend fun syncUserData() {
        try {
            val userData = SyncData(
                id = "user_123",
                timestamp = System.currentTimeMillis(),
                data = "User sync data"
            )
            val response = apiService.uploadData(userData)
            Log.d(TAG, "User sync response: ${response.status}")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing user data", e)
            throw e
        }
    }
    
    private suspend fun syncSettings() {
        // Implementar sincronización de configuración
    }
    
    private suspend fun syncLocalChanges() {
        // Implementar sincronización de cambios locales
    }
    
    companion object {
        private const val TAG = "CustomSyncWorker"
    }
}

// ============================================================================
// EJEMPLO 6: Agregar caché local con Room
// ============================================================================

// Entidad de Room
@Entity(tableName = "sync_records")
data class SyncRecord(
    @PrimaryKey val id: String,
    val data: String,
    val timestamp: Long,
    val status: String  // "PENDING", "SYNCED", "FAILED"
)

// DAO
@Dao
interface SyncRecordDao {
    @Query("SELECT * FROM sync_records WHERE status = 'PENDING'")
    suspend fun getPendingSyncs(): List<SyncRecord>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSyncRecord(record: SyncRecord)
    
    @Query("UPDATE sync_records SET status = 'SYNCED' WHERE id = :id")
    suspend fun markAsSynced(id: String)
}

// Usar en Worker
class SyncWorkerWithCache(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    private val database = DatabaseProvider.getDatabase(context)
    private val syncRecordDao = database.syncRecordDao()
    
    override suspend fun doWork(): Result = try {
        // Obtener registros pendientes
        val pendingSyncs = syncRecordDao.getPendingSyncs()
        
        // Sincronizar cada uno
        for (sync in pendingSyncs) {
            try {
                // ... código de sincronización ...
                syncRecordDao.markAsSynced(sync.id)
            } catch (e: Exception) {
                Log.e(TAG, "Error syncing record ${sync.id}", e)
                // No marcar como sincronizado, reintentar después
            }
        }
        
        Result.success()
    } catch (e: Exception) {
        Log.e(TAG, "Sync worker failed", e)
        if (runAttemptCount < 3) {
            Result.retry()
        } else {
            Result.failure()
        }
    }
    
    companion object {
        private const val TAG = "SyncWorkerWithCache"
    }
}

// ============================================================================
// EJEMPLO 7: Monitoreo avanzado con LiveData
// ============================================================================

class AdvancedSyncMonitor(context: Context) {
    private val workManager = WorkManager.getInstance(context)
    
    // LiveData de todos los trabajos etiquetados como "sync"
    val allSyncJobs = workManager.getWorkInfosByTagLiveData("sync")
    
    // LiveData de trabajos completados recientemente
    val recentSuccessfulSyncs = workManager
        .getWorkInfosByTagLiveData("sync")
        .map { workInfoList ->
            workInfoList.filter { 
                it.state == WorkInfo.State.SUCCEEDED &&
                (System.currentTimeMillis() - it.outputData.getLong("completion_time", 0)) < 3600000
            }
        }
}

// ============================================================================
// EJEMPLO 8: Pruebas unitarias para SyncWorker
// ============================================================================

class SyncWorkerTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var context: Context
    private lateinit var workManager: WorkManager
    private lateinit var testDriver: TestDriver
    
    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        workManager = WorkManager.getInstance(context)
        testDriver = WorkManagerTestInitHelper.initializeTestDriver(context)
    }
    
    @Test
    fun testSyncWorkerSuccess() = runTest {
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .build()
        
        // Enqueue
        workManager.enqueueUniqueWork("test_sync", ExistingWorkPolicy.KEEP, request)
        testDriver.setAllConstraintsMet(request.id)
        testDriver.setPeriodDelayMet(request.id)
        
        // Simulate work
        testDriver.getWorkSpec(request.id)?.let {
            // Verificar que el trabajo se ejecutó correctamente
            assert(true)
        }
    }
}

// ============================================================================
// EJEMPLO 9: Notificación de progreso de sincronización
// ============================================================================

class SyncWorkerWithProgress(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result = try {
        // Reportar progreso inicial
        setProgress(workDataOf("progress" to 0))
        
        // Simular trabajo con progreso
        for (i in 1..10) {
            delay(1000)
            setProgress(workDataOf("progress" to i * 10))
        }
        
        Result.success()
    } catch (e: Exception) {
        Result.failure()
    }
    
    companion object {
        const val PROGRESS = "progress"
    }
}

// En la UI, observar el progreso:
/*
workManager.getWorkInfosForUniqueWorkLiveData("sync_now")
    .observe(lifecycleOwner) { workInfoList ->
        workInfoList.firstOrNull()?.progress?.let { progress ->
            val currentProgress = progress.getInt(SyncWorkerWithProgress.PROGRESS, 0)
            progressBar.progress = currentProgress
        }
    }
*/

// ============================================================================
// EJEMPLO 10: Sincronización selectiva basada en cambios locales
// ============================================================================

data class SyncStrategy(
    val onlyChangedData: Boolean = true,
    val incrementalSync: Boolean = true,
    val conflictResolution: ConflictResolutionStrategy = ConflictResolutionStrategy.LAST_WRITE_WINS,
    val maxRetries: Int = 3,
    val backoffMultiplier: Double = 1.5
)

enum class ConflictResolutionStrategy {
    LAST_WRITE_WINS,
    SERVER_WINS,
    CLIENT_WINS,
    MANUAL
}

// Usar esta estrategia en el Worker
class SmartSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    private val strategy = SyncStrategy()
    
    override suspend fun doWork(): Result = try {
        if (strategy.onlyChangedData) {
            // Solo sincronizar datos que cambiaron
            syncChangedDataOnly()
        } else {
            // Sincronización completa
            syncAllData()
        }
        
        Result.success()
    } catch (e: Exception) {
        if (runAttemptCount < strategy.maxRetries) {
            Result.retry()
        } else {
            Result.failure()
        }
    }
    
    private suspend fun syncChangedDataOnly() {
        // Implementar sincronización selectiva
    }
    
    private suspend fun syncAllData() {
        // Implementar sincronización completa
    }
}

