package com.akshaym.weatherappcompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4A90E2),      // Top Gradient Color
    secondary = Color(0x26FFFFFF),    // Humidity/Metric Card Background
    tertiary = Color(0xFF4332E6),     // Bottom Gradient Color

    background = Color(0xFF4332E6),   // Deep base
    surface = Color(0x1AFFFFFF),      // Slightly darker glass
    onPrimary = Color.White, onSecondary = Color.White, onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A90E2),      // Stay with Sky Blue
    secondary = Color(0x40FFFFFF),    // Slightly more opaque (25%) for visibility
    tertiary = Color(0xFF82B1FF),     // A lighter, "daytime" blue for the bottom

    background = Color(0xFFE3F2FD),   // Very light blue background
    surface = Color(0xB3FFFFFF),      // 70% white to stand out against light blue
    onPrimary = Color.White, onSecondary = Color(0xFF1A73E8),  // Darker blue text for readability
    onSurface = Color(0xFF1A73E8)
)

@Composable
fun WeatherAppComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    val view = LocalView.current
    if (!view.isInEditMode) {
        // This will ONLY run when 'darkTheme' changes
        DisposableEffect(darkTheme) {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            window.statusBarColor = android.graphics.Color.TRANSPARENT
            insetsController.isAppearanceLightStatusBars = !darkTheme

            onDispose {
                // Optional: Reset if the theme is destroyed
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}