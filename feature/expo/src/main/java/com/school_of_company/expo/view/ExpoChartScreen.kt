package com.school_of_company.expo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.DetailsPieChart
import com.school_of_company.expo.view.component.ExpoChartGroupButton
import com.school_of_company.expo.view.component.ParticipantPieChart
import com.school_of_company.expo.view.component.SchoolCategory
import com.school_of_company.expo.view.component.SchoolData

@Composable
internal fun ExpoChartRoute(
    onBackClick: () -> Unit
) {

    ExpoChartScreen(
        data = SchoolData(
            kindergarten = SchoolCategory(number = 10, percent = 10),
            elementary = SchoolCategory(number = 30, percent = 30),
            middle = SchoolCategory(number = 20, percent = 20),
            high = SchoolCategory(number = 30, percent = 30),
            other = SchoolCategory(number = 10, percent = 10),
        ),
        onBackClick = onBackClick
    )
}

@Composable
private fun ExpoChartScreen(
    modifier: Modifier = Modifier,
    data: SchoolData,
    onBackClick: () -> Unit,
) {
    val (isPieChartSelected, setIsPieChartSelected) = rememberSaveable { mutableStateOf(true) }

    ExpoAndroidTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .paddingHorizontal(
                    horizontal = 16.dp,
                    bottom = 36.dp
                )
        ) {
            ExpoTopBar(
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onBackClick() }
                    )
                },
                betweenText = "통계보기",
                modifier = Modifier.padding(top = 68.dp)
            )

            Spacer(modifier = Modifier.padding(bottom = 30.dp))

            Text(
                text = "행사 참가자",
                style = typography.bodyBold1,
                color = colors.black,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))

            Row(horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "참가자 전체 인원",
                    style = typography.bodyRegular2,
                    color = colors.gray300
                )

                Spacer(modifier = Modifier.padding(end = 12.dp))

                Text(
                    text = "Not Yet",
                    style = typography.bodyRegular2,
                    color = colors.gray500
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 28.dp))

            ExpoChartGroupButton(
                isPieChartSelected = isPieChartSelected,
                onPieChartClick = {
                    setIsPieChartSelected(true)
                },
                onBarChartClick = {
                    setIsPieChartSelected(false)
                }
            )

            Spacer(modifier = Modifier.padding(bottom = 32.dp))

            DetailsPieChart(data = data)

            Spacer(modifier = Modifier.padding(bottom = 81.dp))

            if (isPieChartSelected) {
                ParticipantPieChart(data = data)
            } else {
                Text(
                    text = "막대 그래프",
                    style = typography.bodyRegular2,
                    color = colors.gray500
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpoChartScreenPreview() {
    ExpoChartScreen(
        data = SchoolData(
            kindergarten = SchoolCategory(number = 10, percent = 10),
            elementary = SchoolCategory(number = 30, percent = 30),
            middle = SchoolCategory(number = 20, percent = 20),
            high = SchoolCategory(number = 30, percent = 30),
            other = SchoolCategory(number = 10, percent = 10),
        ),
        onBackClick = {}
    )
}