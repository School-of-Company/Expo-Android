package com.school_of_company.home.view.component

import android.text.style.TabStopSpan.Standard
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity

@Composable
fun StandardProgramListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: StandardProgramListResponseEntity,
    navigateToProgramDetail: (Long) -> Unit,
    horizontalScrollState: ScrollState = rememberScrollState()
) {
    ExpoAndroidTheme { colors, typography ->

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(color = colors.white)
                .padding(vertical = 10.dp)
                .horizontalScroll(horizontalScrollState)
                .expoClickable { navigateToProgramDetail(data.id) }
        ) {

            Text(
                text = index.toString(),
                style = typography.captionBold1,
                color = colors.black,
                modifier = Modifier.width(20.dp)
            )

            Spacer(modifier = Modifier.width(42.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(64.dp, Alignment.End)) {

                Text(
                    text = data.title,
                    style = typography.captionRegular2,
                    color = colors.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(100.dp)
                )

                Text(
                    text = data.startedAt,
                    style = typography.captionRegular2,
                    color = colors.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(150.dp)
                )

                Text(
                    text = data.endedAt,
                    style = typography.captionRegular2,
                    color = colors.black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(150.dp)
                )

                XIcon(
                    tint = colors.error,
                    modifier = Modifier
                        .size(16.dp)
                        .width(25.dp)
                )

                XIcon(
                    tint = colors.error,
                    modifier = Modifier
                        .size(16.dp)
                        .width(25.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun StandardProgramListItemPreview() {
    StandardProgramListItem(
        index = 1,
        data = StandardProgramListResponseEntity(
            id = 0,
            title = "title",
            startedAt = "2024-09-12 T 08:30",
            endedAt = "2024-09-12 T 08:30",
        ),
        navigateToProgramDetail = {}
    )
}