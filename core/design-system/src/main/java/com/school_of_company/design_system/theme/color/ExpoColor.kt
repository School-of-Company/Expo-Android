package com.school_of_company.design_system.theme.color

import androidx.compose.ui.graphics.Color

object ExpoColor : ColorTheme() {

    // Main Color
    override val main = Color(0xFF448FFF)
    override val main500 = Color(0xFF63A2FF)
    override val main400 = Color(0xFF82B4FF)
    override val main300 = Color(0xFFA2C7FF)
    override val main200 = Color(0xFFC1DAFF)
    override val main100 = Color(0xFFE0ECFF)

    // Gray Color
    override val gray900 = Color(0xFF383838)
    override val gray800 = Color(0xFF4E4E4E)
    override val gray700 = Color(0xFF646464)
    override val gray600 = Color(0xFF7A7A7A)
    override val gray500 = Color(0xFF909090)
    override val gray400 = Color(0xFFA7A7A7)
    override val gray300 = Color(0xFFBDBDBD)
    override val gray200 = Color(0xFFD3D3D3)
    override val gray100 = Color(0xFFE9E9E9)

    // System Color
    override val error = Color(0xFFFF3434)
    override val success = Color(0xFF4CFF34)

    // Black And White Color
    override val black = Color(0xFF121212)
    override val white = Color(0xFFFFFFFF)
}