package com.school_of_company.design_system.theme

import androidx.compose.runtime.Composable
import com.school_of_company.design_system.theme.color.ColorTheme
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
fun ExpoAndroidTheme(
    colors: ColorTheme = ExpoColor,
    typography: ExpoTypography,
    content: @Composable (colors: ColorTheme, typography: ExpoTypography) -> Unit
) {
    content(colors, typography)
}