package com.school_of_company.program.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.trainee.TraineeResponseEntity

@Composable
internal fun ProgramTraineeListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: TraineeResponseEntity,
    horizontalScrollState: ScrollState
) {
    Spacer(modifier = Modifier.height(20.dp))

    ExpoAndroidTheme { colors, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .horizontalScroll(horizontalScrollState),
            horizontalArrangement = Arrangement.spacedBy(44.dp)
        ) {

            Text(
                text = index.toString(),
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.requiredWidthIn(20.dp)
            )

            Text(
                text = data.name,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.requiredWidthIn(80.dp)
            )

            Text(
                text = data.trainingId,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.requiredWidthIn(130.dp)
            )

            Text(
                text = data.phoneNumber,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.requiredWidthIn(130.dp)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.requiredWidthIn(116.dp)
            ) {
                Text(
                    text = if (data.applicationType == "FIELD") "현장" else "사전",
                    style = typography.captionRegular2,
                    color = colors.black,
                )
            }
        }
    }
}