package com.school_of_company.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.ExpoIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    emptyList: Boolean = false,
    item: ImmutableList<HomeTempData> = persistentListOf(),
    navigateToHomeDetail: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->
        if (emptyList) {
            Column(
                verticalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colors.white)
            ) {
                ExpoIcon(modifier = Modifier.size(100.dp))
                Text(
                    text = "아직 박람회가 등장하지 않았아요.",
                    style = typography.bodyRegular2,
                    color = colors.gray400
                )
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = colors.white)
                    .paddingHorizontal(bottom = 8.dp)
            ) {
                itemsIndexed(item) { _, item ->
                    HomeListItem(
                        data = item,
                        navigateToHomeDetail = navigateToHomeDetail
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeListPreview() {
    HomeList(
        item = persistentListOf(
            HomeTempData(
                image = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
                started_at = "09-01",
                ended_at = "09-30",
                title = "2024 AI 광주 미래교육 2024 AI 광주 미래교육",
                content = "2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육"
            ),
        ),
        emptyList = true,
        navigateToHomeDetail = {}
    )
}