package com.school_of_company.design_system.component.lottie

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.school_of_company.design_system.R

@Composable
fun CheckLottie(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie))

    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier.size(128.dp)
    )
}

@Preview
@Composable
private fun PreviewCheckLottie() {
    CheckLottie()
}