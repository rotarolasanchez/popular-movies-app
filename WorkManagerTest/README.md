# WorkManager + SyncAdapter - Ejemplo de Sincronización

Este proyecto demuestra cómo implementar sincronización en background usando **WorkManager** y **SyncAdapter** en una aplicación Android Kotlin con Jetpack Compose.

## 📋 Características

- ✅ **Sincronización Periódica**: Programada cada 15 minutos usando WorkManager
- ✅ **Sincronización Inmediata**: Opción para sincronizar bajo demanda
- ✅ **SyncAdapter Compatibility**: Soporte para Android antiguo (API 24+)
- ✅ **UI en Compose**: Interfaz moderna con estado reactivo
- ✅ **Reintentos Automáticos**: Hasta 3 intentos en caso de fallo
- ✅ **Monitoreo de Estado**: Flow reactivo que actualiza la UI en tiempo real

## 📁 Estructura del Proyecto

```
app/src/main/java/com/example/workmanagertest/
├── sync/
│   ├── SyncWorker.kt              # Worker que ejecuta tareas de sincronización
│   ├── SyncService.kt             # SyncAdapter y ContentProvider
│   └── AuthenticatorService.kt    # AccountAuthenticator dummy
├── api/
│   └── ApiService.kt              # Interfaz Retrofit para API
├── data/
│   └── SyncRepository.kt           # Repositorio: orquesta WorkManager
├── ui/
│   └── SyncScreen.kt              # UI Compose para control de sincronización
└── MainActivity.kt                # Punto de entrada

app/src/main/res/xml/
├── syncadapter.xml                # Configuración del SyncAdapter
└── authenticator.xml              # Configuración del Authenticator
```

## 🔧 Componentes Principales

### SyncWorker
Hereda de `CoroutineWorker` y ejecuta el trabajo de sincronización:
- Realiza llamadas a la API usando Retrofit
- Implementa lógica de reintentos
- Registra errores para debugging

### SyncAdapter
Proporciona compatibilidad con Android antiguo:
- Implementa `AbstractThreadedSyncAdapter`
- Se integra con el Sistema de Cuentas de Android
- Coordina con WorkManager para ejecutar tareas

### SyncRepository
Orquesta toda la lógica de sincronización:
- Programa trabajos periódicos
- Ejecuta sincronizaciones inmediatas
- Expone el estado como Flow reactivo
- Implementa patrón Singleton

### SyncScreen
Interfaz de usuario en Compose:
- Botones para sincronizar ahora o periodicamente
- Muestra estado actual de la sincronización
- Permite cancelar trabajos en progreso

## 📝 Uso

### 1. Programar Sincronización Periódica

```kotlin
val repository = SyncRepository.getInstance(context)
repository.schedulePeriodicSync()  // Cada 15 minutos
```

### 2. Sincronizar Bajo Demanda

```kotlin
repository.scheduleSyncNow()  // Sincronización inmediata
```

### 3. Monitorear Estado

```kotlin
repository.getSyncWorkStatus()
    .collect { status ->
        when (status) {
            is WorkStatus.Running -> println("Sincronizando...")
            is WorkStatus.Success -> println("¡Sincronizado!")
            is WorkStatus.Failed -> println("Error en sincronización")
            else -> {}
        }
    }
```

### 4. Cancelar Sincronización

```kotlin
repository.cancelAllSyncWork()
```

## 🔌 Integración en MainActivity

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    val syncRepository = SyncRepository.getInstance(this)
    syncRepository.schedulePeriodicSync()
    
    setContent {
        WorkManagerTestTheme {
            Scaffold { innerPadding ->
                SyncScreen(
                    syncRepository = syncRepository,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
```

## 📊 Estados de Sincronización

| Estado | Descripción |
|--------|-------------|
| `NotScheduled` | No hay trabajo programado |
| `Queued` | Trabajo en cola esperando ejecución |
| `Running` | Sincronización en progreso |
| `Success` | Sincronización completada exitosamente |
| `Failed` | Error durante la sincronización |
| `Blocked` | Trabajo bloqueado (ej: sin conectividad) |
| `Cancelled` | Trabajo cancelado manualmente |
| `Unknown` | Estado desconocido |

## 🔐 Permisos Requeridos

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.READ_SYNC_SETTINGS" />
<uses-permission android:name="android.permission.WRITE_SYNC_SETTINGS" />
<uses-permission android:name="android.permission.AUTHENTICATE_ACCOUNTS" />
```

## 🛠️ Personalización

### Cambiar Frecuencia de Sincronización

Modifica en `SyncRepository.kt`:
```kotlin
val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
    30, TimeUnit.MINUTES  // Cambiar a 30 minutos
).build()
```

### Agregar Restricciones de Red

```kotlin
val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
    15, TimeUnit.MINUTES
)
.setBackoffCriteria(
    BackoffPolicy.EXPONENTIAL,
    15, TimeUnit.MINUTES
)
.addTag("sync")
.build()
```

### Personalizar API

Reemplaza la URL en `SyncWorker.kt`:
```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://tu-api.com/")
    .build()
```

## 📚 Dependencias Principales

- **androidx.work:work-runtime-ktx**: 2.8.1
- **com.squareup.retrofit2:retrofit**: 2.10.0
- **org.jetbrains.kotlinx:kotlinx-coroutines-android**: 1.7.3
- **androidx.lifecycle:lifecycle-livedata-ktx**: 2.6.1 (incluida en lifecycle-runtime-ktx)

## 🐛 Debugging

Habilita logs detallados en `SyncWorker.kt`:
```kotlin
val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY  // BASIC, HEADERS, o BODY
}
```

Monitorea WorkManager en Android Studio:
1. Abre Logcat
2. Filtra por "WorkManager"
3. Observa eventos de enqueue, start, finish

## 📖 Referencias

- [WorkManager Documentation](https://developer.android.com/jetpack/androidx/releases/work)
- [Android Sync Adapter Guide](https://developer.android.com/training/sync-adapters)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Retrofit](https://square.github.io/retrofit/)

## ⚖️ Licencia

Este proyecto es un ejemplo educativo sin licencia específica.

---

**Nota**: Este es un ejemplo básico. Para producción, considera:
- Implementar persistencia de datos locales (Room, DataStore)
- Agregar encriptación para datos sensibles
- Implementar sincronización bidireccional
- Agregar manejo robusto de errores
- Implementar analítica y logging centralizado

