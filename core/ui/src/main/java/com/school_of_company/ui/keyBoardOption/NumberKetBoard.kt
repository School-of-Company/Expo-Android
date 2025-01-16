package com.school_of_company.ui.keyBoardOption

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

fun numericKeyboardOptions(): KeyboardOptions {
    return KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number
    )
}