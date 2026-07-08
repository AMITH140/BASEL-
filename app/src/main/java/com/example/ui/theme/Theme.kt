package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val BaselColorScheme = darkColorScheme(
    primary = BaselPrimary,
    onPrimary = BaselOnPrimary,
    background = BaselBackground,
    surface = BaselSurface,
    surfaceVariant = BaselSurfaceVariant,
    onBackground = BaselTextPrimary,
    onSurface = BaselTextPrimary,
    onSurfaceVariant = BaselTextSecondary,
    outline = BaselDivider,
    error = BaselRed
)

@Composable
fun BaselTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = BaselColorScheme, 
        typography = Typography, 
        content = content
    )
}
