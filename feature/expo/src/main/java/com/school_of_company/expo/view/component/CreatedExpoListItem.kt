package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.CircleIcon
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ColorTheme

@Composable
fun CreatedExpoListItem(
    modifier: Modifier = Modifier,
    selectedIndex: Long,
    id: Long,
    title: String,
    startedDay: String,
    finishedDay: String,
    coverImage: String?,
    onClick: (Boolean) -> Unit,
) {
    ExpoAndroidTheme { colors: ColorTheme, typography: ExpoTypography ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(38.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .background(
                    color = if (selectedIndex == id) colors.main100 else colors.white,
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .expoClickable { onClick(selectedIndex == id) }
                .padding(8.dp),
        ) {
            Text(
                text = id.toString(),
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
                    text = title,
                    style = typography.captionRegular2,
                    fontWeight = FontWeight.W400,
                    color = colors.black,
                    textAlign = TextAlign.Center,
                )
                Text(
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

@Preview
@Composable
fun CreatedExpoListItemNotSelectedPreview() {
    CreatedExpoListItem(
        id = 0,
        coverImage = null,
        startedDay = "시작날",
        finishedDay = "끝날",
        title = "제목입니다",
        selectedIndex = 1,
        onClick = { _ -> },
    )
}

@Preview
@Composable
fun CreatedExpoListItemSelectedPreview() {
    CreatedExpoListItem(
        id = 1,
        coverImage = null,
        startedDay = "시작날",
        finishedDay = "끝날",
        title = "제목입니다",
        selectedIndex = 1,
        onClick = { _ -> },
    )
}