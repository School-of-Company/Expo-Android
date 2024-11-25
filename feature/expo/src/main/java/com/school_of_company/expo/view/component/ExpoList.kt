package com.school_of_company.expo.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.ExpoIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ExpoList(
    modifier: Modifier = Modifier,
    emptyList: Boolean = false,
    item: ImmutableList<ExpoListResponseEntity> = persistentListOf(),
    navigateToExpoDetail: (String) -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    ExpoAndroidTheme { colors, typography ->
        if (emptyList) {
            Column(
                verticalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(color = colors.white)
            ) {
                ExpoIcon(
                    tint = colors.black,
                    modifier = Modifier.size(100.dp)
                )
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
                items(item) { item ->
                    ExpoListItem(
                        data = item,
                        navigateToExpoDetail = navigateToExpoDetail
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeListPreview() {
    ExpoList(
        item = persistentListOf(
            ExpoListResponseEntity(
                id = "",
                coverImage = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
                startedDay = "09-01",
                finishedDay = "09-30",
                title = "2024 AI 광주 미래교육 2024 AI 광주 미래교육",
                description = "2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육"
            ),
        ),
        emptyList = true,
        navigateToExpoDetail = {}
    )
}