# Guía de Ejecución y Troubleshooting

## 🚀 Cómo Ejecutar la Aplicación

### Requisitos Previos

- Android Studio 2023.1 o superior
- SDK de Android 36 instalado (compileSdk)
- Emulador o dispositivo con Android 7.0 (API 24) o superior
- Java 11 o superior

### Pasos para Ejecutar

1. **Clonar/Abrir el proyecto**
   ```bash
   cd WorkManagerTest
   ```

2. **Compilar el proyecto**
   ```bash
   ./gradlew clean build
   ```

3. **Ejecutar en emulador o dispositivo**
   ```bash
   ./gradlew installDebug
   # o desde Android Studio: Run > Run 'app'
   ```

4. **Monitorear logs**
   ```bash
   adb logcat | grep -E "SyncWorker|SyncRepository|SyncAdapter"
   ```

## 📱 Cómo Usar la Aplicación

### En la Interfaz

1. **Sincronizar Ahora**
   - Toca el botón "Sincronizar Ahora"
   - Verás que el estado cambia a "Sincronizando..."
   - Espera a que se complete

2. **Programar Sincronización Periódica**
   - Toca "Programar Sincronización Periódica"
   - Se ejecutará automáticamente cada 15 minutos
   - La app NO necesita estar abierta

3. **Cancelar Sincronización**
   - Si hay una sincronización en progreso
   - Toca "Cancelar Sincronización"

### Estados Esperados

- **Inicial**: "No programado"
- **Después de pulsar Sincronizar**: "En cola" → "Sincronizando..."
- **Éxito**: "Sincronizado exitosamente"
- **Error**: "Error en sincronización" (reintentos automáticos)

## 🔍 Debugging y Monitoreo

### Ver Logs de WorkManager

```bash
adb logcat WorkManager:V *:S
```

### Monitorear Estado de Trabajos

```bash
# Ver todos los trabajos programados
adb shell dumpsys jobscheduler | grep workmanager

# Ver logs de sincronización
adb logcat | grep Sync
```

### Usar Android Studio Device Explorer

1. Abre Device Explorer (View > Tool Windows > Device Explorer)
2. Navega a `/data/data/com.example.workmanagertest/`
3. Busca archivos de logs o DB

## ⚠️ Troubleshooting

### El trabajo no se ejecuta

**Síntoma**: El trabajo no se inicia tras programarlo

**Soluciones**:
1. Verifica que tienes conexión a internet
2. Abre Developer Settings y desactiva las optimizaciones de batería para esta app
3. Asegúrate de que el dispositivo tiene espacio suficiente
4. Revisa que los permisos están otorgados

```bash
# Ver permisos de la app
adb shell pm dump com.example.workmanagertest | grep "android.permission"
```

### Error "No protocol specified"

**Síntoma**: Error al realizar la llamada HTTP

**Solución**: El puerto correcto en `ApiService.kt` es `https://api.example.com/`
Reemplaza con tu servidor real

### WorkManager dice que el trabajo falló

**Síntoma**: Estado "Error en sincronización" después de 3 reintentos

**Soluciones**:
1. Revisa los logs para ver el error específico:
   ```bash
   adb logcat | grep "ERROR"
   ```

2. Verifica que `ApiService` puede alcanzar el servidor
3. Comprueba que los datos enviados son válidos
4. Revisa la respuesta del servidor en los logs

### La UI no se actualiza

**Síntoma**: El estado de sincronización no cambia en la pantalla

**Soluciones**:
1. El Flow puede no estar siendo colectado correctamente
2. Verifica que `getSyncWorkStatus()` está retornando eventos
3. Agrega logging en `SyncScreen.kt`:
   ```kotlin
   LaunchedEffect(syncStatus) {
       Log.d("SyncScreen", "Status changed to: $syncStatus")
   }
   ```

### Problema: Sincronización se detiene después de X minutos

**Síntoma**: El trabajo se inicia pero no se completa

**Causas comunes**:
- Doze mode de Android mata el trabajo
- Falta de conexión a internet
- La tarea toma más de lo esperado

**Soluciones**:
1. Agregar excepciones de batería (solo para debug):
   ```bash
   adb shell dumpsys deviceidle whitelist +com.example.workmanagertest
   ```

2. Aumentar el timeout en `SyncWorker.kt`:
   ```kotlin
   val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
       15, TimeUnit.MINUTES
   )
   .setForeground(
       createForegroundInfo()  // Crear notificación persistente
   )
   .build()
   ```

## 🧪 Pruebas Manuales

### Test 1: Sincronización Básica
1. Abre la app
2. Pulsa "Sincronizar Ahora"
3. Observa el cambio de estado
4. Verifica en logs

```bash
adb logcat | grep "SyncWorker"
```

### Test 2: Sincronización Periódica

1. Pulsa "Programar Sincronización Periódica"
2. Cierra la app
3. Espera 15 minutos (o cambia el tiempo de prueba a 1 minuto en el código)
4. Verifica en logs o abre la app

### Test 3: Sin Conexión a Internet

1. Desactiva datos móviles y WiFi
2. Pulsa "Sincronizar Ahora"
3. Habilitará reintentos automáticos
4. Reactiva la conexión
5. El trabajo debería ejecutarse

### Test 4: Cancelación

1. Pulsa "Sincronizar Ahora"
2. Inmediatamente pulsa "Cancelar Sincronización"
3. El trabajo debería detenerse

## 📊 Estadísticas y Métricas

### Ver información del trabajo

```kotlin
workManager.getWorkInfosForUniqueWorkLiveData("sync_work")
    .observe(lifecycleOwner) { workInfoList ->
        workInfoList.firstOrNull()?.let { workInfo ->
            println("Run Attempt Count: ${workInfo.runAttemptCount}")
            println("Next Retry Time: ${workInfo.nextScheduleTimeMillis}")
            println("Progress: ${workInfo.progress}")
        }
    }
```

## 🔐 Pruebas de Seguridad

### Verificar Permisos

```bash
# Ver permisos solicitados
adb shell dumpsys package com.example.workmanagertest | grep "granted permissions" -A 20
```

### Revisar Datos Sensibles

- Asegúrate de que las credenciales NO se registran en logs
- Usa ProGuard/R8 para ofuscar en release
- Encripta datos sensibles antes de almacenar

## 📈 Optimización

### Reducir Consumo de Batería

1. Aumenta el intervalo de sincronización (ej: 30 min, 1 hora)
2. Agrega restricciones de red (solo WiFi)
3. Agrega restricciones de batería

```kotlin
.setBackoffCriteria(
    BackoffPolicy.EXPONENTIAL,
    15, TimeUnit.MINUTES
)
```

### Reducir Usar de Datos

1. Implementa sincronización incremental (delta sync)
2. Comprime datos antes de enviar
3. Cachea datos locales

## 📝 Comandos Útiles

```bash
# Compilar sin instalar
./gradlew assemble

# Instalar y ejecutar
./gradlew installDebug

# Ejecutar pruebas
./gradlew test

# Ver estructura del proyecto
./gradlew projects

# Ver dependencias
./gradlew dependencies

# Limpiar caché
./gradlew clean

# Generar APK signado
./gradlew assembleRelease
```

## 🆘 Contacto y Recursos

- [Documentación Oficial de WorkManager](https://developer.android.com/jetpack/androidx/releases/work)
- [Guía de Sync Adapters](https://developer.android.com/training/sync-adapters)
- [Logcat Documentation](https://developer.android.com/studio/debug/logcat)

---

**Nota**: Muchos problemas se resuelven con:
1. `./gradlew clean build`
2. Reiniciar el emulador
3. Limpiar caché de Android Studio

