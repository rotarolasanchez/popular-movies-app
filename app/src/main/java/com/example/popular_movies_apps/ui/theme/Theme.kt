package com.example.popular_movies_apps.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
private val LightColorScheme = lightColorScheme(
//private val DarkColorScheme = darkColorScheme(
    // Principales
    primary = NetflixRed,
    onPrimary = NetflixWhite,
    primaryContainer = NetflixDarkRed,
    onPrimaryContainer = NetflixWhite,

    // Secundarios
    secondary = NetflixGray,
    onSecondary = NetflixWhite,
    secondaryContainer = NetflixDarkGray,
    onSecondaryContainer = NetflixLightGray,

    // Terciarios
    tertiary = NetflixDarkRed,
    onTertiary = NetflixWhite,
    tertiaryContainer = NetflixCharcoal,
    onTertiaryContainer = NetflixWhite,

    // Fondos y superficies
    background = NetflixBlack,
    onBackground = NetflixWhite,
    surface = NetflixDarkGray,
    onSurface = NetflixWhite,
    surfaceVariant = NetflixCharcoal,
    onSurfaceVariant = NetflixDarkGray,

    // Errores
    error = NetflixRed,
    onError = NetflixWhite,
    errorContainer = NetflixCharcoal,
    onErrorContainer = NetflixRed,

    // Contornos
    outline = NetflixDimGray,
    outlineVariant = NetflixCharcoal
)
private val DarkColorScheme = darkColorScheme(
//private val LightColorScheme = lightColorScheme(
    // Principales
    primary = NetflixLightRed,
    onPrimary = NetflixWhite,
    primaryContainer = NetflixPearlGray,
    onPrimaryContainer = NetflixBlackText,

    // Secundarios
    secondary = NetflixSteelBlue,
    onSecondary = NetflixWhite,
    secondaryContainer = NetflixStone,
    onSecondaryContainer = NetflixBlackText,

    // Terciarios
    tertiary = NetflixBurgundyRed,
    onTertiary = NetflixWhite,
    tertiaryContainer = NetflixPearlGray,
    onTertiaryContainer = NetflixBurgundyRed,

    // Fondos y superficies
    background = NetflixLightBg,
    onBackground = NetflixBlackText,
    surface = NetflixPearlGray,
    onSurface = NetflixBlackText,
    surfaceVariant = NetflixStone,
    onSurfaceVariant = NetflixDarkGrayText,

    // Errores
    error = NetflixBurgundyRed,
    onError = NetflixWhite,
    errorContainer = NetflixPearlGray,
    onErrorContainer = NetflixBurgundyRed,

    // Contornos
    outline = NetflixDarkGrayText,
    outlineVariant = NetflixStone
)

@Composable
fun PopularmoviesappsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography) {
        // System bars
        val context = LocalContext.current
        if (context is Activity) {
            SideEffect {
                val window = context.window
                WindowCompat.setDecorFitsSystemWindows(window, false)
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, window.decorView)
                    .isAppearanceLightStatusBars = !darkTheme
            }
        }
        content()
    }
}