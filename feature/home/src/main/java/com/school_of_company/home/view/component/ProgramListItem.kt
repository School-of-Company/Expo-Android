package com.school_of_company.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.CircleIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

data class ProgramTempList(
    val programName: String,
    val check: Boolean,
    val must: Boolean
)

@Composable
fun ProgramListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: ProgramTempList,
    navigateToProgramDetail: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(color = colors.white)
                .padding(vertical = 10.dp)
                .expoClickable { navigateToProgramDetail() }
        ) {

            Text(
                text = index.toString(),
                style = typography.captionBold1,
                color = colors.black,
                modifier = Modifier.weight(0.5f)
            )


            Text(
                text = data.programName,
                style = typography.captionRegular2,
                color = colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(2f)
            )


            if (data.check) {
                CircleIcon(
                    tint = colors.black,
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f)
                )
            } else {
                XIcon(
                    tint = colors.error,
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f)
                )
            }
            

            if (data.must) {
                CircleIcon(
                    tint = colors.black,
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f)
                )
            } else {
                XIcon(
                    tint = colors.error,
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProgramListItemPreview() {
    ProgramListItem(
        index = 1,
        data = ProgramTempList(
            programName = "프로그램 이름",
            check = true,
            must = false
        ),
        navigateToProgramDetail = {}
    )
}