package com.school_of_company.expo.view.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun ParticipantBarGraph(
    modifier: Modifier = Modifier,
    schoolCategoryList: List<Pair<String, SchoolCategoryItem>>,
) {
    ExpoAndroidTheme { colors, typography ->

        var trigger by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            trigger = true
        }

        val animateFloat by animateFloatAsState(
            targetValue = if (trigger) 100f else 0f,
            animationSpec = tween(durationMillis = 600)
        )
        val chartColors = listOf(
            colors.main,
            colors.main400,
            colors.main500,
            colors.main200,
            colors.main100
        )
        val scrollState = rememberScrollState()

        Column(modifier = modifier.verticalScroll(scrollState)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 25.dp)
                        .height(IntrinsicSize.Max),
                    verticalArrangement = Arrangement.spacedBy(49.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    schoolCategoryList
                        .map { it.first }
                        .forEach { schoolCategoryName ->
                            Text(
                                text = schoolCategoryName,
                                style = typography.captionRegular2,
                                color = colors.black,
                                textAlign = TextAlign.Center,
                            )
                        }
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = colors.gray300,
                                shape = RoundedCornerShape(size = 6.dp)
                            )
                            .height(IntrinsicSize.Max)
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp),
                    ) {
                        schoolCategoryList
                            .map { it.second.percent }
                            .forEachIndexed { index, percent ->
                                Spacer(
                                    Modifier
                                        .fillMaxWidth((percent / 100f) * (animateFloat / 100f))
                                        .height(32.dp)
                                        .background(
                                            color = chartColors[index],
                                            shape = RoundedCornerShape(
                                                topStart = 0.dp,
                                                topEnd = 6.dp,
                                                bottomStart = 0.dp,
                                                bottomEnd = 6.dp
                                            )
                                        )
                                )
                            }
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "0%",
                            style = typography.captionRegular2,
                            color = colors.black,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "25%",
                            style = typography.captionRegular2,
                            color = colors.black,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "50%",
                            style = typography.captionRegular2,
                            color = colors.black,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "75%",
                            style = typography.captionRegular2,
                            color = colors.black,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "100%",
                            style = typography.captionRegular2,
                            color = colors.black,
                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun ParticipantBarGraphPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        ParticipantBarGraph(
            schoolCategoryList = listOf(
                "Category 1" to SchoolCategoryItem(10f, 10f),
                "Category 2" to SchoolCategoryItem(20f, 20f),
                "Category 2" to SchoolCategoryItem(20f, 20f),
                "Category 2" to SchoolCategoryItem(20f, 20f),
                "Category 3" to SchoolCategoryItem(50f, 50f)
            )
        )
    }
}