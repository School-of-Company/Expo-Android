package com.school_of_company.signup.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
internal fun CountdownTimer(
    onTimerFinish: () -> Unit,
) {
    var remainingTime by rememberSaveable { mutableIntStateOf(5 * 60) }

    LaunchedEffect(remainingTime) {
        if (remainingTime == 0) {
            onTimerFinish()
        }
        delay(1_000L)
        remainingTime--
    }

    ExpoAndroidTheme {
        colors, typography ->
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
                text = formatTime(remainingTime.toLong()),
                color = colors.white,
                style = typography.captionBold1
            )
        }
    }
}

@Composable
internal fun formatTime(seconds: Long): String {
    val minutes = TimeUnit.SECONDS.toMinutes(seconds)
    val remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes)
    return String.format("%01d:%02d", minutes, remainingSeconds)
}