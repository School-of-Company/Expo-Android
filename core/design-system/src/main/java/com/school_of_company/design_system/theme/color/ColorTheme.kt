package com.school_of_company.design_system.theme.color

import androidx.compose.ui.graphics.Color

abstract class ColorTheme {

    // Main ColorTheme
    abstract val main: Color
    abstract val main600: Color
    abstract val main500: Color
    abstract val main400: Color
    abstract val main300: Color
    abstract val main200: Color
    abstract val main100: Color

    // Gray ColorTHeme
    abstract val gray900: Color
    abstract val gray800: Color
    abstract val gray700: Color
    abstract val gray600: Color
    abstract val gray500: Color
    abstract val gray400: Color
    abstract val gray300: Color
    abstract val gray200: Color
    abstract val gray100: Color

    // System ColorTheme
    abstract val error: Color
    abstract val success: Color

    // Black And White ColorTheme
    abstract val black: Color
    abstract val white: Color
}