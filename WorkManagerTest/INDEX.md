# 📑 Índice - WorkManager + SyncAdapter

## 🎯 Empezar Aquí

### Para Principiantes
1. **[QUICKSTART.md](QUICKSTART.md)** - ⚡ Empieza en 3 pasos
2. **[README.md](README.md)** - 📖 Documentación completa

### Para Desarrolladores Avanzados
1. **[EJEMPLOS_AVANZADOS.md](EJEMPLOS_AVANZADOS.md)** - 🔧 Código avanzado
2. **[TROUBLESHOOTING.md](TROUBLESHOOTING.md)** - 🔍 Solución de problemas

---

## 📂 Estructura del Proyecto

```
WorkManagerTest/
│
├── 📊 Documentación
│   ├── README.md                    # Documentación principal
│   ├── QUICKSTART.md                # Guía rápida (3 pasos)
│   ├── EJEMPLOS_AVANZADOS.md        # Código avanzado
│   ├── TROUBLESHOOTING.md           # Errors y soluciones
│   └── INDEX.md                     # Este archivo
│
├── 🏗️ Configuración del Proyecto
│   ├── build.gradle.kts             # Dependencias de la app
│   ├── settings.gradle.kts          # Configuración raíz
│   ├── gradle.properties            # Propiedades Gradle
│   ├── gradle/libs.versions.toml    # Versiones de librerías
│   ├── local.properties             # Configuración local
│   └── gradlew/gradlew.bat          # Script de Gradle
│
├── 📱 Código Fuente (app/src/main/)
│   │
│   ├── AndroidManifest.xml          # Permisos y componentes
│   │
│   ├── java/com/example/workmanagertest/
│   │   │
│   │   ├── 🔄 Sincronización (sync/)
│   │   │   ├── SyncWorker.kt            # El trabajo que se ejecuta
│   │   │   ├── SyncService.kt           # SyncAdapter + ContentProvider
│   │   │   └── AuthenticatorService.kt  # AccountAuthenticator dummy
│   │   │
│   │   ├── 🌐 API (api/)
│   │   │   └── ApiService.kt            # Interfaz Retrofit
│   │   │
│   │   ├── 📊 Datos (data/)
│   │   │   └── SyncRepository.kt        # Orquestador principal
│   │   │
│   │   ├── 🎨 UI (ui/)
│   │   │   ├── SyncScreen.kt            # Pantalla de Compose
│   │   │   └── theme/                   # Temas de Compose
│   │   │
│   │   └── MainActivity.kt              # Entrada principal
│   │
│   ├── 🎨 Recursos (res/)
│   │   ├── xml/
│   │   │   ├── syncadapter.xml          # Configuración SyncAdapter
│   │   │   ├── authenticator.xml        # Configuración Authenticator
│   │   │   ├── data_extraction_rules.xml
│   │   │   └── backup_rules.xml
│   │   │
│   │   ├── values/
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   └── themes.xml
│   │   │
│   │   ├── drawable/
│   │   │   ├── ic_launcher_background.xml
│   │   │   └── ic_launcher_foreground.xml
│   │   │
│   │   ├── mipmap-*/                   # Iconos de app
│   │   │   ├── ic_launcher.webp
│   │   │   └── ic_launcher_round.webp
│   │   │
│   │   └── mipmap-anydpi-v26/          # Iconos adaptables
│   │       ├── ic_launcher.xml
│   │       └── ic_launcher_round.xml
│   │
│   └── 🧪 Tests
│       ├── androidTest/                 # Tests instrumentados
│       │   └── ExampleInstrumentedTest.kt
│       └── test/                        # Tests unitarios
│           └── ExampleUnitTest.kt
│
└── 📝 Root Files
    ├── README.md                    # Documentación
    ├── QUICKSTART.md                # Quick start
    ├── EJEMPLOS_AVANZADOS.md        # Ejemplos avanzados
    ├── TROUBLESHOOTING.md           # Troubleshooting
    └── INDEX.md                     # Este archivo
```

---

## 🔑 Archivos Clave Explicados

### 1. **SyncWorker.kt** 🔄

**Ubicación**: `app/src/main/java/com/example/workmanagertest/sync/`

**Qué hace**: Ejecuta la sincronización en background

**Puntos importantes**:
- Hereda de `CoroutineWorker`
- Implementa reintentos automáticos
- Usa Retrofit para llamadas HTTP
- Registra todo en logs

**Cuándo lo necesitas cambiar**:
- Si cambias la API del servidor
- Si necesitas procesamiento de datos diferente
- Para agregar encriptación o caché

---

### 2. **SyncRepository.kt** 📊

**Ubicación**: `app/src/main/java/com/example/workmanagertest/data/`

**Qué hace**: Orquesta toda la lógica de sincronización

**Métodos principales**:
- `schedulePeriodicSync()` - Sincronizar cada 15 min
- `scheduleSyncNow()` - Sincronizar inmediatamente
- `getSyncWorkStatus()` - Ver estado como Flow
- `cancelAllSyncWork()` - Cancelar sincronización

**Patrón**: Singleton (única instancia en la app)

---

### 3. **SyncScreen.kt** 🎨

**Ubicación**: `app/src/main/java/com/example/workmanagertest/ui/`

**Qué hace**: Interfaz de usuario en Compose

**Componentes**:
- Botón "Sincronizar Ahora"
- Botón "Programar Periódica"
- Botón "Cancelar"
- Indicador de estado

**Reactiva**: Se actualiza automáticamente según el estado

---

### 4. **ApiService.kt** 🌐

**Ubicación**: `app/src/main/java/com/example/workmanagertest/api/`

**Qué hace**: Define cómo hablar con el servidor

**Interfaces tipificadas**:
- `SyncData` - Estructura de datos a enviar
- `SyncResponse` - Respuesta esperada
- `ApiService` - Métodos HTTP

**Necesitas cambiar**:
- Base URL
- Estructura de datos
- Endpoints

---

### 5. **MainActivity.kt** 📱

**Ubicación**: `app/src/main/java/com/example/workmanagertest/`

**Qué hace**: Punto de entrada de la app

**Responsabilidades**:
- Inicializar `SyncRepository`
- Programar sincronización periódica
- Mostrar `SyncScreen`

---

### 6. **AndroidManifest.xml** ⚙️

**Ubicación**: `app/src/main/`

**Qué hay**:
- ✅ Permisos de internet y sincronización
- ✅ Registro de `SyncService`
- ✅ Registro de `AuthenticatorService`
- ✅ Registro de `ContentProvider`

**No modificar** a menos que agregues componentes nuevos

---

## 🚀 Flujo de Ejecución

```
┌─────────────────────────────────────┐
│ Usuario toca "Sincronizar Ahora"    │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│ MainActivity.onCreate()              │
│  └─ SyncRepository.getInstance()    │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│ SyncRepository.scheduleSyncNow()     │
│  └─ WorkManager.enqueueUniqueWork() │
└──────────────┬──────────────────────┘
               │
               ▼
┌─────────────────────────────────────┐
│ SyncWorker.doWork()                 │
│  ├─ Crear cliente Retrofit          │
│  ├─ Llamar ApiService               │
│  └─ Procesar datos                  │
└──────────────┬──────────────────────┘
               │
         ┌─────┴─────┐
         │           │
         ▼           ▼
    Success      Failure (retry)
         │           │
         └─────┬─────┘
               │
               ▼
┌─────────────────────────────────────┐
│ WorkStatus se actualiza              │
│  └─ SyncScreen se redibuja          │
└─────────────────────────────────────┘
```

---

## 📦 Dependencias Principales

```toml
# WorkManager
androidx.work:work-runtime-ktx = "2.8.1"

# HTTP Client
com.squareup.retrofit2:retrofit = "2.10.0"
com.squareup.okhttp3:okhttp = "4.11.0"

# Serialización
org.jetbrains.kotlinx:kotlinx-serialization-json = "1.6.0"

# Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android = "1.7.3"

# Compose / UI
androidx.activity:activity-compose = "1.13.0"
androidx.compose.material3:material3
androidx.compose.ui:ui

# LifeCycle
androidx.lifecycle:lifecycle-runtime-ktx = "2.10.0"
```

---

## 🎓 Mapa de Aprendizaje

### Principiante ➜ Avanzado

1. **Nivel Básico**
   - Lee: `QUICKSTART.md`
   - Entiende: `MainActivity.kt` + `SyncScreen.kt`
   - Prueba: Pulsa botones en la UI

2. **Nivel Intermedio**
   - Lee: `README.md`
   - Entiende: `SyncRepository.kt` + `SyncWorker.kt`
   - Modifica: URLs de API, frecuencia de sincronización

3. **Nivel Avanzado**
   - Lee: `EJEMPLOS_AVANZADOS.md`
   - Implementa: Caché local, autenticación, encriptación
   - Construye: Sistema de sincronización personalizado

---

## 🔧 Tareas Comunes

### Task: Cambiar frecuencia de sincronización

**Archivo**: `SyncRepository.kt` línea 26-27

```kotlin
val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
    30, TimeUnit.MINUTES  // ← Cambiar aquí
).build()
```

**Tiempo**: 2 minutos

---

### Task: Cambiar URL del servidor

**Archivo**: `SyncWorker.kt` línea 45

```kotlin
.baseUrl("https://tu-servidor.com/")  // ← Cambiar aquí
```

**Tiempo**: 1 minuto

---

### Task: Agregar campo nuevo a sincronizar

**Archivo**: `ApiService.kt` línea 9

```kotlin
@Serializable
data class SyncData(
    val id: String,
    val timestamp: Long,
    val data: String,
    val nuevocampo: String  // ← Agregar aquí
)
```

**Tiempo**: 5 minutos

---

### Task: Ver logs de sincronización

**Comando**:
```bash
adb logcat | grep -E "SyncWorker|SyncRepository"
```

**Tiempo**: 1 minuto

---

## 🧪 Checklist de Validación

- [ ] ¿La app compila sin errores?
  ```bash
  ./gradlew clean build
  ```

- [ ] ¿Puedo instalar la app?
  ```bash
  ./gradlew installDebug
  ```

- [ ] ¿Funciona "Sincronizar Ahora"?
  - Toca botón → Espera "Sincronizado exitosamente"

- [ ] ¿Se ejecuta periódicamente?
  - Programa → Cierra app → Abre después → Verifica logs

- [ ] ¿Funciona sin internet?
  - Desactiva WiFi → Toca "Sincronizar" → Reactiva WiFi

- [ ] ¿Se actualiza la UI?
  - Observa cambios en "Estado:" mientras sincroniza

---

## 📞 Soporte Rápido

| Problema | Solución | Docs |
|----------|----------|------|
| No compila | `./gradlew clean build` | [TROUBLESHOOTING.md](TROUBLESHOOTING.md) |
| No se ejecuta | Ver logs: `adb logcat` | [TROUBLESHOOTING.md](TROUBLESHOOTING.md) |
| UI no se actualiza | Revisar `SyncScreen.kt` | [README.md](README.md) |
| Necesito más funciones | Ver `EJEMPLOS_AVANZADOS.md` | [EJEMPLOS_AVANZADOS.md](EJEMPLOS_AVANZADOS.md) |

---

## 🌟 Características Destacadas

✅ **Automático**: Se ejecuta incluso con app cerrada  
✅ **Eficiente**: Optimizado para batería  
✅ **Robusto**: Reintentos automáticos  
✅ **Compatible**: API 24+  
✅ **Moderno**: Kotlin + Jetpack Compose  
✅ **Reactivo**: UI que responde en tiempo real  

---

## 🎯 Próximos Pasos Sugeridos

1. Ejecuta la app (`./gradlew installDebug`)
2. Prueba los botones en la UI
3. Abre los archivos mencionados en los comentarios
4. Lee la documentación según el nivel
5. Personaliza para tu caso de uso

---

**¡Bienvenido a tu sistema de sincronización! 🚀**

¿Preguntas? Revisa:
- [QUICKSTART.md](QUICKSTART.md) - Para empezar rápido
- [TROUBLESHOOTING.md](TROUBLESHOOTING.md) - Si algo no funciona
- [README.md](README.md) - Para documentación completa

