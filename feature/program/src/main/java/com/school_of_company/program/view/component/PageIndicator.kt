package com.school_of_company.program.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun PageIndicator(
    modifier: Modifier = Modifier,
    totalPages: Int,
    currentPage: Int,
    maxVisible: Int = 7,
    onCurrentPageChange: (Int) -> Unit
) {
    val displayPage = currentPage + 1
    val half = maxVisible / 2
    val start = maxOf(1, minOf(displayPage - half, totalPages - maxVisible + 1))
    val end = minOf(totalPages, start + maxVisible - 1)
    val pageList = (start..end).toList()

    ExpoAndroidTheme { colors, typography ->
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_left_arrow),
                tint = colors.black,
                contentDescription = null,
                modifier = Modifier.expoClickable {
                    if (displayPage != 1) {
                        onCurrentPageChange(currentPage - 1)
                    }
                },
            )
            pageList.forEach {
                Text(
                    text = it.toString(),
                    style = typography.captionRegular2,
                    color = if (it == displayPage) colors.main else colors.gray600,
                    modifier = Modifier.expoClickable { onCurrentPageChange(it - 1) }
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_right_arrow),
                tint = colors.black,
                contentDescription = null,
                modifier = Modifier.expoClickable {
                    if (totalPages != displayPage) {
                        onCurrentPageChange(currentPage + 1)
                    }
                },
            )
        }
    }
}


@Composable
@Preview
private fun PageIndicatorPreview() {
    val page = remember { mutableIntStateOf(0) }

    PageIndicator(
        totalPages = 13,
        currentPage = page.value,
        maxVisible = 7,
        onCurrentPageChange = { page.value = it }
    )
}