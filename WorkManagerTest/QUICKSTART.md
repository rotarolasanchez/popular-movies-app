# ⚡ Guía Rápida - WorkManager + SyncAdapter

## 3 Pasos para Empezar

### 1️⃣ Instalar la App
```bash
./gradlew installDebug
```

### 2️⃣ Abrir en el Dispositivo
- La app se abrirá automáticamente
- Verás la pantalla de sincronización

### 3️⃣ ¡Listo! Prueba estas acciones

```
┌─────────────────────────────────────┐
│  WorkManager + SyncAdapter          │
│                                      │
│  Estado: [En cola]                   │
│                                      │
│ [Sincronizar Ahora]                  │
│ [Programar Periódica]                │
│ [Cancelar Sincronización]            │
└─────────────────────────────────────┘
```

## 🎯 Acciones Principales

### A. Sincronizar Ahora (Inmediato)
```
Toca "Sincronizar Ahora"
↓
"En cola" (1-2 seg)
↓
"Sincronizando..."
↓
"Sincronizado exitosamente" ✓
```

### B. Sincronización Automática
```
Toca "Programar Sincronización Periódica"
↓
Se ejecutará cada 15 minutos
↓
Funciona incluso con la app cerrada
```

### C. Ver Progreso en Logs
```bash
# Terminal 1: Ver todos los logs
adb logcat

# Terminal 2: Ver solo sincronización
adb logcat | grep -E "Sync|WorkManager"

# Terminal 3: Ver logs específicos de SyncWorker
adb logcat | grep SyncWorker
```

## 🔧 Lo Más Importantes del Código

### Usar el Repositorio (la forma correcta)

```kotlin
// En MainActivity.kt o donde sea que lo necesites
val syncRepository = SyncRepository.getInstance(context)

// Sincronizar ahora
syncRepository.scheduleSyncNow()

// Sincronizar cada 15 minutos
syncRepository.schedulePeriodicSync()

// Escuchar estado
syncRepository.getSyncWorkStatus().collect { status ->
    println("Estado: ${status.getDisplayText()}")
}

// Cancelar
syncRepository.cancelAllSyncWork()
```

### Monitorear en UI (Compose)

```kotlin
@Composable
fun MiPantalla(syncRepository: SyncRepository) {
    val syncStatus by syncRepository.getSyncWorkStatus()
        .collectAsState(WorkStatus.NotScheduled)
    
    Text("Estado: ${syncStatus.getDisplayText()}")
    
    Button(onClick = { syncRepository.scheduleSyncNow() }) {
        Text("Sincronizar")
    }
}
```

## 📂 Archivos Clave Que Debes Conocer

| Archivo | Qué hace |
|---------|----------|
| `SyncWorker.kt` | El trabajo que se ejecuta en background |
| `SyncRepository.kt` | Controla cuándo y cómo sincronizar |
| `SyncScreen.kt` | La UI de Compose |
| `ApiService.kt` | Define cómo hablar con tu servidor |
| `AuthenticatorService.kt` | Necesario para SyncAdapter |

## 🚦 Estados Posibles

```
┌──────────────────────────┐
│  Estados de Sincronización
├──────────────────────────┤
│ 🔴 No programado         │ Nada sucediendo
│ ⏳ En cola               │ Esperando para ejecutarse
│ ⚙️  Sincronizando...     │ En progreso AHORA
│ ✅ Sincronizado         │ ¡Completado exitosamente!
│ ❌ Error                 │ Falló (reintentos automáticos)
└──────────────────────────┘
```

## 🔌 Integración Rápida en Tu App

### Paso 1: Inicializa en MainActivity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // ← AQUÍ: Inicializar sincronización
        val syncRepository = SyncRepository.getInstance(this)
        syncRepository.schedulePeriodicSync()
        
        setContent {
            WorkManagerTestTheme {
                SyncScreen(syncRepository)
            }
        }
    }
}
```

### Paso 2: Actualiza tu API en ApiService.kt

```kotlin
interface ApiService {
    @GET("tu-endpoint") 
    suspend fun getStatus(): SyncResponse
    
    @POST("tu-endpoint")
    suspend fun uploadData(@Body data: SyncData): SyncResponse
}

// Cambiar base URL en SyncWorker.kt
.baseUrl("https://tu-servidor.com/")
```

### Paso 3: Personaliza los datos

En `ApiService.kt`:
```kotlin
@Serializable
data class SyncData(
    val id: String,      // ← Cambiar según tus datos
    val timestamp: Long,
    val data: String     // ← Tu estructura de datos
)
```

## 💡 Casos de Uso Comunes

### 1. Sincronizar cada X minutos
```kotlin
val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
    30, TimeUnit.MINUTES  // ← Cambiar aquí
).build()
```

### 2. Sincronizar solo con WiFi
```kotlin
val constraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.UNMETERED)
    .build()
```

### 3. Recuperarse automáticamente de errores
```kotlin
// Automático en SyncWorker:
if (runAttemptCount < 3) {
    Result.retry()  // ← Reintentar hasta 3 veces
}
```

## 🧪 Pruebas Rápidas

### Test 1: ¿Funciona la sincronización inmediata?
```bash
# 1. Abre la app
# 2. Toca "Sincronizar Ahora"
# 3. Deberías ver "Sincronizando..."
# 4. En 2-3 segundos: "Sincronizado exitosamente"

# Ver logs:
adb logcat | grep SyncWorker
```

### Test 2: ¿Funciona sin conexión?
```bash
# 1. Desactiva WiFi/datos
# 2. Toca "Sincronizar Ahora"
# 3. Deberías ver reintentos automáticos
# 4. Reactiva conexión → debería completarse
```

## 📊 Ver Estadísticas

```kotlin
workManager.getWorkInfosForUniqueWorkLiveData("sync_work")
    .observe(lifecycleOwner) { workInfoList ->
        workInfoList.firstOrNull()?.let {
            println("Intentos: ${it.runAttemptCount}")
            println("Estado: ${it.state}")
            println("Próximo reintento: ${it.nextScheduleTimeMillis}")
        }
    }
```

## 🎓 Para Aprender Más

```
WorkManager
├── Periódicos: Ejecutar cada X tiempo
├── One-time: Ejecutar una sola vez
├── Constraints: WiFi, batería, etc.
└── Resilencia: Reintentos automáticos

SyncAdapter
├── Compatible con iOS (vía servidor)
├── Usa cuentas del dispositivo
└── Sistema operativo lo controla
```

## ❓ Preguntas Frecuentes

**P: ¿La sincronización funciona si la app está cerrada?**
R: ✅ Sí, ese es el punto de WorkManager

**P: ¿Cuánta batería consume?**
R: ⚡ Poco. WorkManager optimiza automáticamente

**P: ¿Qué pasa sin internet?**
R: 🔄 Reintentos automáticos cada 15 minutos

**P: ¿Cómo cancelo la sincronización?**
R: ❌ Toca "Cancelar Sincronización" o:
```kotlin
syncRepository.cancelAllSyncWork()
```

**P: ¿Puedo cambiar a 5 minutos?**
R: ✅ Sí, en `SyncRepository.kt` línea 26-27

## 🚀 Próximos Pasos

1. **Reemplazar API**: Cambiar `https://api.example.com/` por tu servidor
2. **Agregar datos**: Personalizar `SyncData` con tus campos
3. **Agregar caché**: Integrar Room Database
4. **Agregar autenticación**: Token JWT o similar
5. **Agregar notificaciones**: Alertar al usuario cuando se complete

## 📚 Documentación Completa

- `README.md` - Documentación completa
- `EJEMPLOS_AVANZADOS.md` - Código avanzado y extensiones
- `TROUBLESHOOTING.md` - Solución de problemas

---

**¡Listo! Tu app de sincronización está funcionando. 🎉**

Ahora personalízala según tus necesidades.

