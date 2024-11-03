package com.school_of_company.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.home.enum.ArrayHomeListEnum
import com.school_of_company.home.view.component.HomeBottomSheet
import com.school_of_company.home.view.component.HomeFilterButton
import com.school_of_company.home.view.component.HomeList
import com.school_of_company.home.view.component.HomeTempData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

fun generateSampleData(): ImmutableList<HomeTempData> {
    return List(10) {
        HomeTempData(
            image = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
            started_at = "09-20",
            ended_at = "09-30",
            title = "2024 AI 광주 미래교육2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교",
            content = "2024 AI 광주 미래교육 컨텐츠2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교2024 AI 광주 미래교"
        )
    }.toPersistentList()
}

@Composable
internal fun HomeRoute(
    navigationToDetail: () -> Unit
) {
    HomeScreen(
        item = generateSampleData().toPersistentList(),
        navigationToDetail = navigationToDetail
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    item: ImmutableList<HomeTempData>,
    navigationToDetail: () -> Unit
) {
    val (openBottomSheet, isOpenBottomSheet) = rememberSaveable { mutableStateOf(false) }
    var arrayList by rememberSaveable { mutableStateOf(ArrayHomeListEnum.RECENT) }

    val arrayItems = when (arrayList) {
        ArrayHomeListEnum.RECENT -> item.sortedByDescending { it.started_at }
        ArrayHomeListEnum.OLDER -> item.sortedBy { it.started_at }
    }.toPersistentList()

    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 50.dp))

            Text(
                text = stringResource(id = R.string.main_string),
                style = typography.mainTypo,
                fontSize = 30.sp,
                color = colors.main
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.expo_register),
                    style = typography.titleBold2,
                    color = colors.black
                )

                HomeFilterButton { isOpenBottomSheet(true) }
            }

            HomeList(
                item = arrayItems,
                modifier = Modifier.expoClickable { navigationToDetail() }
            )
        }
    }
    if (openBottomSheet) {
        HomeBottomSheet(
            onRecentClick = {
                arrayList = ArrayHomeListEnum.RECENT
                isOpenBottomSheet(false)
            },
            onOldClick = {
                arrayList = ArrayHomeListEnum.OLDER
                isOpenBottomSheet(false)
            },
            onCancelClick = { isOpenBottomSheet(false) }
        )
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        item = persistentListOf(
            HomeTempData(
                image = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
                started_at = "09-01",
                ended_at = "09-30",
                title = "2024 AI 광주 미래교육 2024 AI 광주 미래교육",
                content = "2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육"
            )
        ),
        navigationToDetail = {}
    )
}