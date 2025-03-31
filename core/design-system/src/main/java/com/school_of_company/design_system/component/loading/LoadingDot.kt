package com.school_of_company.design_system.component.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
fun LoadingDot(modifier: Modifier = Modifier) {

    val dotScale1 by animateScaleWithDelay(0)
    val dotScale2 by animateScaleWithDelay(300)
    val dotScale3 by animateScaleWithDelay(600)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(top = 16.dp)
        ) {
            Dot(dotScale1)
            Spacer(Modifier.width(10.dp))
            Dot(dotScale2)
            Spacer(Modifier.width(10.dp))
            Dot(dotScale3)
        }

        Spacer(modifier = Modifier.padding(bottom = 4.5.dp))

        Text(
            text = "로딩중..",
            style = ExpoTypography.bodyBold1,
            color = ExpoColor.main
        )
    }
}

@Composable
private fun Dot(scale: Float) = Spacer(
    Modifier
        .size(16.dp)
        .scale(scale)
        .background(
            color = ExpoColor.main,
            shape = CircleShape
        )
)

@Composable
private fun animateScaleWithDelay(delay: Int) = rememberInfiniteTransition().animateFloat(
    initialValue = 1f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            durationMillis = 1100
            1f at delay with LinearEasing
            0f at delay + 275 with LinearEasing
            1f at delay + 550 with LinearEasing
        }
    )
)

@Preview
@Composable
private fun PreviewLoadingDots() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LoadingDot()
    }
}