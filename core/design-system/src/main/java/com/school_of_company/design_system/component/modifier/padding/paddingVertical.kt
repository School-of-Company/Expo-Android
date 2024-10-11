package com.school_of_company.design_system.component.modifier.padding

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.paddingVertical(
    vertical: Dp = 0.dp,
    start: Dp = 0.dp,
    end: Dp = 0.dp
) : Modifier = this then Modifier
    .padding(vertical = vertical)
    .padding(
        start = start,
        end = end
    )