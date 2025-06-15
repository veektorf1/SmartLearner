package com.example.learnsmarter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

val DarkColorScheme = darkColorScheme(
    primary = BlueAzureDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = BlueAzureContainerDark,
    onPrimaryContainer = OnBlueAzureContainerDark,

    secondary = AccentYellowDark,
    onSecondary = OnSecondaryDarkText,

    background = CloudyBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceLight,

    error = ErrorRedDark,
    onError = OnErrorDark
)


val LightColorScheme = lightColorScheme(
    primary = BlueAzure,
    onPrimary = OnPrimaryWhite,
    primaryContainer = BlueAzureContainer,
    onPrimaryContainer = OnBlueAzureContainer,

    secondary = AccentYellow,
    onSecondary = OnSecondaryBlack,

    background = CloudyBackground,
    surface = SurfaceWhite,
    onSurface = OnSurfaceDark,

    error = ErrorRed,
    onError = OnErrorWhite
)


@Composable
fun LearnSmarterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}