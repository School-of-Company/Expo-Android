package com.school_of_company.home.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

data class HomeDetailParticipantManagementData(
    val name: String,
    val company: String,
    val position: String,
    val schoolSubject: String,
    val checkHere: Boolean,
    val inTime: String?,
    val outTime: String?
)

@Composable
fun HomeDetailParticipantManagementListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: HomeDetailParticipantManagementData,
    horizontalScrollState: ScrollState = rememberScrollState(),
) {
    Spacer(modifier = Modifier.height(20.dp))

    ExpoAndroidTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .horizontalScroll(horizontalScrollState),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = index.toString(),
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(20.dp)
            )

            Text(
                text = data.name,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(80.dp)
            )

            Text(
                text = data.company,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(100.dp)
            )

            Text(
                text = data.position,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(80.dp)
            )

            Text(
                text = data.schoolSubject,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(120.dp)
            )

            Text(
                text = if (data.checkHere) "출석" else "미출석",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(100.dp)
            )

            Text(
                text = data.inTime ?: "x",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(80.dp)
            )

            Text(
                text = data.outTime ?: "x",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(80.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomeDetailParticipantManagementListItemPreview() {
    HomeDetailParticipantManagementListItem(
        index = 1,
        data = HomeDetailParticipantManagementData(
            name = "이명훈",
            company = "초등학교",
            position = "교사",
            schoolSubject = "컴퓨터공학",
            checkHere = true,
            inTime = "12:00",
            outTime = "13:00"
        )
    )
}