# Popular Movies App

## 1\. Resumen
Aplicación Android que muestra listado y detalle de películas populares consumiendo TMDB, aplicando Clean Architecture, Offline First, DI con Hilt y evento de detalle con Firebase Analytics.

## 2\. Objetivos Técnicos
- Arquitectura limpia (Presentation / Domain / Data).
- Estrategia Offline First (Realm como cache primaria).
- Inyección de dependencias (Hilt).
- Registro de evento personalizado al ver detalle.

## 3\. Stack
Kotlin, Jetpack Compose (100% UI), Coroutines/Flows, Hilt, Retrofit + OkHttp Interceptor, Realm, Firebase Analytics, Gradle Kotlin DSL.

## 4\. Arquitectura
Presentation (Compose + ViewModels) -> Domain (UseCases + Models) -> Data (Repository + Remote DTO + Local RealmObjects + Mappers) -> Core (Networking).  
Patrón de acceso: UI llama UseCase -> Repository decide fuente (local/remota) -> Local Realm persiste.

## 5\. Flujo Offline First
1. ViewModel solicita películas.
2. Repository lee Realm.
3. Si vacío o forceRefresh: solicita API, persiste en Realm y devuelve datos normalizados.
4. Detalle: intenta Realm; si no existe, consulta API y cachea.

## 6\. Decisiones
- Interceptor para `api_key` centralizado.
- Logging condicional `API_DEBUG` para inspección.
- Abstracción `AnalyticsLogger` para desacoplar Firebase.

## 7\. Configuración
Crear `local.properties`:
TMDB_API_KEY=TU_KEY TMDB_BASE_URL=https://api.themoviedb.org/3/
Requiere `google-services.json` en `app/`.

## 8\. Ejecución
Sincronizar Gradle y ejecutar en Android Studio. SDK 24+.

## 9\. Eventos Analytics
Evento: `view_movie_detail` con parámetros `movie_id`, `movie_title`, `vote_average`.  
Modo debug:
adb shell setprop debug.firebase.analytics.app com.example.popular_movies_apps adb shell setprop log.tag.FA VERBOSE

Ver en Firebase Console -> DebugView.

## 10\. Testing (pendiente)
- Unit tests: UseCases y Repository (mock ApiService y LocalDataSource).
- Instrumented: Navegación básica (Compose UI test).

## 11\. Seguridad
- API Key sólo en `local.properties`.
- No se expone en fuentes ni commits.

## 12\. Mejoras Futuras
- Paginación.
- Modo dark adaptativo dinámico.
- Cache de imágenes con Coil + MemoryPolicy.
- CI (GitHub Actions) con lint + tests.

## 13\. Licencia
MIT (agregar archivo LICENSE).