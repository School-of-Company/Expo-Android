package com.school_of_company.form.view.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlin.math.roundToInt

@Composable
internal fun FormToggleButton(
    modifier: Modifier = Modifier,
    check: Boolean,
    width: Dp,
    height: Dp,
    onClick: () -> Unit
) {
    ExpoAndroidTheme { colors, _ ->
        val density = LocalDensity.current
        val minBound = with(density) { 0.dp.toPx() }
        val maxBound = with(density) { (width - height).toPx() }

        var thumbOffset by remember { mutableFloatStateOf(if (check) maxBound else minBound) }
        var isDragging by remember { mutableStateOf(false) }

        val positionFraction = thumbOffset / maxBound

        LaunchedEffect(isDragging) {
            if (!isDragging) {
                val isOverHalf = if (check) thumbOffset < (maxBound / 2)
                else thumbOffset > (maxBound / 2)

                if (isOverHalf) onClick()
                thumbOffset = if (check) maxBound else minBound
            }
        }

        LaunchedEffect(check) {
            thumbOffset = if (check) maxBound else minBound
        }
        val trackColor by animateColorAsState(
            targetValue = lerp(colors.gray100, colors.main100, positionFraction),
            animationSpec = spring(),
            label = "track_color"
        )

        val thumbColor by animateColorAsState(
            targetValue = lerp(colors.gray500, colors.main, positionFraction),
            animationSpec = spring(),
            label = "thumb_color"
        )

        val animatedOffset by animateFloatAsState(
            targetValue = thumbOffset,
            animationSpec = spring(),
            label = "thumb_offset"
        )

        val dragModifier = Modifier.pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragStart = { isDragging = true },
                onHorizontalDrag = { change, dragAmount ->
                    change.consume()
                    thumbOffset = (thumbOffset + dragAmount).coerceIn(minBound, maxBound)
                },
                onDragEnd = {
                    isDragging = false
                }
            )
        }

        Box(
            modifier = modifier
                .size(width = width, height = height)
                .clickable {
                    onClick()
                    thumbOffset = if (check) minBound else maxBound
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height - 4.dp)
                    .align(Alignment.CenterStart)
                    .background(trackColor, shape = RoundedCornerShape(height / 2))
            )

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            if (isDragging) thumbOffset.roundToInt() else animatedOffset.roundToInt(),
                            0
                        )
                    }
                    .size(height)
                    .background(color = thumbColor, shape = CircleShape)
                    .then(dragModifier)
            )
        }
    }
}

@Preview
@Composable
private fun ExpoToggleButtonPreview() {
    var isToggled by remember { mutableStateOf(false) }

    FormToggleButton(
        check = isToggled,
        height = 24.dp,
        width = 70.dp,
        onClick = { isToggled = !isToggled },
    )
}