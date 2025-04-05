package com.school_of_company.expo.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
internal fun ExpoChartGroupButton(
    isPieChartSelected: Boolean,
    onPieChartClick: () -> Unit,
    onBarChartClick: () -> Unit,
) {
    Row {
        ExpoChartButton(
            text = "원 그래프",
            selected = isPieChartSelected,
            onClick = { onPieChartClick() }
        )

        Spacer(modifier = Modifier.padding(end = 12.dp))

        ExpoChartButton(
            text = "막대 그래프",
            selected = !isPieChartSelected,
            onClick = { onBarChartClick() }
        )
    }
}

@Composable
private fun ExpoChartButton(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    defaultTextColor: Color = ExpoColor.main,
    clickedTextColor: Color = ExpoColor.white,
    defaultBackgroundColor: Color = ExpoColor.white,
    clickedBackgroundColor: Color = ExpoColor.main,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { _, typography ->

        Button(
            modifier = modifier.height(34.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) clickedBackgroundColor else defaultBackgroundColor,
                contentColor = if (selected) clickedTextColor else defaultTextColor,
            ),
            border = BorderStroke(1.dp, ExpoColor.main),
            shape = RoundedCornerShape(6.dp),
            onClick = onClick,
        ) {
            Text(
                text = text,
                style = typography.captionRegular2,
                color = if (selected) clickedTextColor else defaultTextColor
            )
        }
    }
}

@Preview
@Composable
private fun PreviewExpoChartButton(modifier: Modifier = Modifier) {
    ExpoChartGroupButton(
        onBarChartClick = {},
        onPieChartClick = {},
        isPieChartSelected = true
    )
}