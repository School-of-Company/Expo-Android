package com.school_of_company.expo.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CreatedExpoList(
    modifier: Modifier = Modifier,
    expoList: ImmutableList<ExpoListResponseEntity>, // getExpoListUiState.data 대신 데이터 리스트
    selectedIndex: Long, // 선택된 ID를 전달받음
    onItemClick: (Boolean, Long) -> Unit, // 클릭 이벤트 콜백
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(expoList) { item ->
            CreatedExpoListItem(
                selectedIndex = selectedIndex,
                item = item,
                onClick = { isSelected ->
                    onItemClick(isSelected, item.id.toLong())
                }
            )
        }
    }
}

@Preview
@Composable
fun CreatedExpoListPreview() {
    CreatedExpoList(
        expoList = persistentListOf(
            ExpoListResponseEntity(
                id = "1",
                title = "2024 AI광주미래교육박람회",
                description = "",
                startedDay = "09.10",
                finishedDay = "09.20",
                coverImage = null
            ),
        ),
        onItemClick = { _, _ -> },
        selectedIndex = 1
    )
}