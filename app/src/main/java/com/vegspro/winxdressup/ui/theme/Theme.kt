package com.vegspro.winxdressup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PinkPrimary,
    onPrimary = Color.White,
    secondary = PurpleLight,
    onSecondary = Color.White,
    tertiary = Gold,
    background = PurpleDark,
    onBackground = SoftWhite,
    surface = CardBg,
    onSurface = SoftWhite,
    surfaceVariant = PurpleMid,
    onSurfaceVariant = PinkLight
)

@Composable
fun WinxDressUpTheme(
    darkTheme: Boolean = true, // always fairy dark theme
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
