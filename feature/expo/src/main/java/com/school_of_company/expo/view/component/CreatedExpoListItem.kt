package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.icon.CircleIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ColorTheme
import com.school_of_company.model.entity.expo.ExpoListResponseEntity

@Composable
internal fun CreatedExpoListItem(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    index: Int,
    item: ExpoListResponseEntity,
    onClick: () -> Unit,
) {
    with(item) {
        ExpoAndroidTheme { colors: ColorTheme, typography: ExpoTypography ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(38.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .background(
                        color = if (selectedIndex == index) colors.main100 else colors.white,
                        shape = RoundedCornerShape(size = 4.dp)
                    )
                    .clickable { onClick() }
                    .padding(8.dp),
            ) {
                Text(
                    text = "${index + 1}",
                    style = typography.captionBold1,
                    fontWeight = FontWeight.W600,
                    color = colors.black,
                    textAlign = TextAlign.Center,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (coverImage == null) {
                        XIcon(tint = colors.error)
                    } else {
                        CircleIcon(tint = colors.black)
                    }
                    Text(
                        modifier = Modifier.width(140.dp),
                        text = title,
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.black,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        modifier = Modifier.width(70.dp),
                        text = "$startedDay ~ $finishedDay",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.black,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CreatedExpoListItemNotSelectedPreview() {
    CreatedExpoListItem(
        item = ExpoListResponseEntity(
            id = "1",
            title = "2024 AI광주미래교육박람회",
            description = "",
            startedDay = "09.10",
            finishedDay = "09.20",
            coverImage = null
        ),
        selectedIndex = 0,
        index = 1,
        onClick = { },
    )
}

@Preview
@Composable
private fun CreatedExpoListItemSelectedPreview() {
    CreatedExpoListItem(
        item = ExpoListResponseEntity(
            id = "1",
            title = "2024 AI광주미래교육박람회",
            description = "",
            startedDay = "09.10",
            finishedDay = "09.20",
            coverImage = null
        ),
        index = 1,
        selectedIndex = 1,
        onClick = { },
    )
}