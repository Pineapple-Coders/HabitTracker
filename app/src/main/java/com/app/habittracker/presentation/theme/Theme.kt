package com.app.habittracker.presentation.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.app.habittracker.domain.model.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Primary,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Primary,
    background = Background,
    surface = Surface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

data class AppColors(
    val textPrimary: Color,
    val textSecondary: Color,
    val textLight: Color,
    val cardBackground: Color,
    val inputBackground: Color,
    val inputText: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val smokingCardBg: Color,
    val drinkingCardBg: Color,
    val junkFoodCardBg: Color,
    val gamingCardBg: Color,
    val coffeeCardBg: Color,
    val socialMediaCardBg: Color,
    val shoppingCardBg: Color,
    val defaultCardBg: Color
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        textPrimary = TextPrimary,
        textSecondary = TextSecondary,
        textLight = TextLight,
        cardBackground = CardBackground,
        inputBackground = InputBackgroundLight,
        inputText = InputTextLight,
        gradientStart = GradientStartLight,
        gradientEnd = GradientEndLight,
        smokingCardBg = SmokingCardBgLight,
        drinkingCardBg = DrinkingCardBgLight,
        junkFoodCardBg = JunkFoodCardBgLight,
        gamingCardBg = GamingCardBgLight,
        coffeeCardBg = CoffeeCardBgLight,
        socialMediaCardBg = SocialMediaCardBgLight,
        shoppingCardBg = ShoppingCardBgLight,
        defaultCardBg = DefaultCardBgLight
    )
}

@Composable
fun HabitTrackerTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val appColors = if (darkTheme) {
        AppColors(
            textPrimary = TextPrimaryDark,
            textSecondary = TextSecondaryDark,
            textLight = TextLightDark,
            cardBackground = CardBackgroundDark,
            inputBackground = InputBackgroundDark,
            inputText = InputTextDark,
            gradientStart = GradientStartDark,
            gradientEnd = GradientEndDark,
            smokingCardBg = SmokingCardBgDark,
            drinkingCardBg = DrinkingCardBgDark,
            junkFoodCardBg = JunkFoodCardBgDark,
            gamingCardBg = GamingCardBgDark,
            coffeeCardBg = CoffeeCardBgDark,
            socialMediaCardBg = SocialMediaCardBgDark,
            shoppingCardBg = ShoppingCardBgDark,
            defaultCardBg = DefaultCardBgDark
        )
    } else {
        AppColors(
            textPrimary = TextPrimary,
            textSecondary = TextSecondary,
            textLight = TextLight,
            cardBackground = CardBackground,
            inputBackground = InputBackgroundLight,
            inputText = InputTextLight,
            gradientStart = GradientStartLight,
            gradientEnd = GradientEndLight,
            smokingCardBg = SmokingCardBgLight,
            drinkingCardBg = DrinkingCardBgLight,
            junkFoodCardBg = JunkFoodCardBgLight,
            gamingCardBg = GamingCardBgLight,
            coffeeCardBg = CoffeeCardBgLight,
            socialMediaCardBg = SocialMediaCardBgLight,
            shoppingCardBg = ShoppingCardBgLight,
            defaultCardBg = DefaultCardBgLight
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}