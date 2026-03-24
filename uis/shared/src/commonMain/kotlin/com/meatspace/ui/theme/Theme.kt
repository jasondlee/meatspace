package com.meatspace.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color palette inspired by Tailwind CSS
private val md_theme_light_primary = Color(0xFF2563EB)
private val md_theme_light_onPrimary = Color(0xFFFFFFFF)
private val md_theme_light_primaryContainer = Color(0xFFDBE2FF)
private val md_theme_light_onPrimaryContainer = Color(0xFF001850)
private val md_theme_light_secondary = Color(0xFF7C3AED)
private val md_theme_light_onSecondary = Color(0xFFFFFFFF)
private val md_theme_light_secondaryContainer = Color(0xFFEDE9FE)
private val md_theme_light_onSecondaryContainer = Color(0xFF2E0861)
private val md_theme_light_tertiary = Color(0xFF059669)
private val md_theme_light_onTertiary = Color(0xFFFFFFFF)
private val md_theme_light_tertiaryContainer = Color(0xFFD1FAE5)
private val md_theme_light_onTertiaryContainer = Color(0xFF00231A)
private val md_theme_light_error = Color(0xFFDC2626)
private val md_theme_light_errorContainer = Color(0xFFFEE2E2)
private val md_theme_light_onError = Color(0xFFFFFFFF)
private val md_theme_light_onErrorContainer = Color(0xFF7F1D1D)
private val md_theme_light_background = Color(0xFFF9FAFB)
private val md_theme_light_onBackground = Color(0xFF111827)
private val md_theme_light_surface = Color(0xFFFFFFFF)
private val md_theme_light_onSurface = Color(0xFF111827)
private val md_theme_light_surfaceVariant = Color(0xFFE5E7EB)
private val md_theme_light_onSurfaceVariant = Color(0xFF6B7280)
private val md_theme_light_outline = Color(0xFF9CA3AF)
private val md_theme_light_inverseOnSurface = Color(0xFFF3F4F6)
private val md_theme_light_inverseSurface = Color(0xFF1F2937)
private val md_theme_light_inversePrimary = Color(0xFF93C5FD)

private val md_theme_dark_primary = Color(0xFF93C5FD)
private val md_theme_dark_onPrimary = Color(0xFF00295D)
private val md_theme_dark_primaryContainer = Color(0xFF003D84)
private val md_theme_dark_onPrimaryContainer = Color(0xFFDBE2FF)
private val md_theme_dark_secondary = Color(0xFFC4B5FD)
private val md_theme_dark_onSecondary = Color(0xFF4C0F97)
private val md_theme_dark_secondaryContainer = Color(0xFF6226AF)
private val md_theme_dark_onSecondaryContainer = Color(0xFFEDE9FE)
private val md_theme_dark_tertiary = Color(0xFF6EE7B7)
private val md_theme_dark_onTertiary = Color(0xFF003A2D)
private val md_theme_dark_tertiaryContainer = Color(0xFF005142)
private val md_theme_dark_onTertiaryContainer = Color(0xFFD1FAE5)
private val md_theme_dark_error = Color(0xFFFCA5A5)
private val md_theme_dark_errorContainer = Color(0xFF991B1B)
private val md_theme_dark_onError = Color(0xFF7F1D1D)
private val md_theme_dark_onErrorContainer = Color(0xFFFEE2E2)
private val md_theme_dark_background = Color(0xFF111827)
private val md_theme_dark_onBackground = Color(0xFFF3F4F6)
private val md_theme_dark_surface = Color(0xFF1F2937)
private val md_theme_dark_onSurface = Color(0xFFF3F4F6)
private val md_theme_dark_surfaceVariant = Color(0xFF374151)
private val md_theme_dark_onSurfaceVariant = Color(0xFF9CA3AF)
private val md_theme_dark_outline = Color(0xFF6B7280)
private val md_theme_dark_inverseOnSurface = Color(0xFF1F2937)
private val md_theme_dark_inverseSurface = Color(0xFFF3F4F6)
private val md_theme_dark_inversePrimary = Color(0xFF2563EB)

private val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
)

private val DarkColors = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
)

@Composable
fun MeatspaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
