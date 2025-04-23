package com.school_of_company.training.view.component

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity

@Composable
internal fun TrainingProgramParticipantListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: TeacherTrainingProgramResponseEntity,
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
                text = data.programName,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(100.dp)
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
private fun HomeDetailProgramParticipantListItemPreview() {
    TrainingProgramParticipantListItem(
        index = 1,
        data = TeacherTrainingProgramResponseEntity(
            id = 0,
            name = "홍길동",
            programName = "프로그램",
            status = true,
            entryTime = "2024-09-12 T 08:30",
            leaveTime = "2024-09-12 T 08:30"
        ),
        horizontalScrollState = rememberScrollState()
    )
}