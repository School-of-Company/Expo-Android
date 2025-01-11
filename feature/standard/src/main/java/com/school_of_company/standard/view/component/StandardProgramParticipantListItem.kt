package com.school_of_company.standard.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity

@Composable
fun StandardProgramParticipantListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: StandardAttendListResponseEntity,
    horizontalScrollState: ScrollState
) {
    ExpoAndroidTheme { colors, typography ->
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier
                .fillMaxWidth()
                .background(color = colors.white)
                .padding(vertical = 8.dp)
                .horizontalScroll(horizontalScrollState)
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
                text = data.affiliation,
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
                text = if (data.status) "출석" else "미출석",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(120.dp)
            )

            Text(
                text = data.entryTime ?: "입실전",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(150.dp)
            )

            Text(
                text = data.leaveTime ?: "퇴실전",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(150.dp)
            )
        }
    }
}

@Preview
@Composable
private fun StandardProgramParticipantListItemPreview() {
    StandardProgramParticipantListItem(
        data = StandardAttendListResponseEntity(
            affiliation = "affiliation",
            entryTime = "입장시간",
            id = 1,
            leaveTime = "퇴장시간",
            name = "연수자 이름",
            position = "직위",
            programName = "연수 이름",
            status = true
        ),
        index = 2,
        horizontalScrollState = ScrollState(1)
    )
}