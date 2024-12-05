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
import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity

@Composable
fun HomeDetailProgramParticipantListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: TeacherTrainingProgramResponseEntity,
    horizontalScrollState: ScrollState = rememberScrollState()
) {
    ExpoAndroidTheme { colors, typography ->
        Spacer(modifier = Modifier.height(20.dp))

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
                text = data.organization,
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
                text = "교사",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(120.dp)
            )
            Text(
                text = "010-3825-1716",
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(100.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomeDetailProgramParticipantListItemPreview() {
}