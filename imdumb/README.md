# IMDUMB - Technical Challenge

## Resumen del Proyecto
IMDUMB es una aplicación Android que muestra categorías de películas y detalles de cada una. El proyecto está diseñado siguiendo los estándares de la industria para aplicaciones escalables y mantenibles.

## Arquitectura
Se ha implementado **MVP (Model-View-Presenter) + Clean Architecture**.

### Capas:
- **Data**: Implementación de repositorios, fuentes de datos remotas (Retrofit) y locales (SharedPreferences).
- **Domain**: Lógica de negocio pura, casos de uso y modelos de dominio.
- **Presentation**: UI (Activities/Fragments) y Presentadores (MVP).

## Tech Stack
- **Lenguaje**: Kotlin.
- **Inyección de Dependencias**: Hilt.
- **Networking**: Retrofit + Gson.
- **Programación Reactiva**: RxJava 2 + RxKotlin.
- **Imagen**: Glide.
- **Firebase**: Remote Config (para Splash, Feature Toggles y Temas).
- **UI**: XML, ConstraintLayout, ViewPager2, BottomSheet.

## Configuración de Firebase
El proyecto incluye el archivo `google-services.json`. Los siguientes parámetros se manejan desde Remote Config:
- `welcome_text`: Mensaje de bienvenida en el Splash.
- `home_title`: Título dinámico de la pantalla principal.
- `enable_recommendation`: Feature toggle para habilitar/deshabilitar el botón de recomendar.
- `app_theme`: Control dinámico del tema (`light` / `dark`).

## Principios SOLID Aplicados
- **Single Responsibility (SRP)**: Cada clase tiene una única responsabilidad (e.g., Mappers se encargan solo de conversión).
- **Open/Closed**: Los casos de uso son extensibles sin modificar la lógica base.
- **Liskov Substitution**: Uso de interfaces para repositorios inyectados.
- **Interface Segregation**: Contratos MVP específicos por pantalla.
- **Dependency Inversion**: Hilt inyecta abstracciones en lugar de implementaciones concretas.

## Cómo ejecutar el proyecto
1. Clonar el repositorio.
2. Abrir en Android Studio Ladybug o superior.
3. Realizar un Gradle Sync.
4. Seleccionar el flavor `dev` o `prod`.
5. Ejecutar en un emulador o dispositivo real.
