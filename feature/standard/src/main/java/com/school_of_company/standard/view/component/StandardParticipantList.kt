package com.school_of_company.standard.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.standard.StandardAttendListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun StandardParticipantList(
    modifier: Modifier = Modifier,
    item: ImmutableList<StandardAttendListResponseEntity> = persistentListOf(),
    horizontalScrollState: ScrollState
) {
    ExpoAndroidTheme { colors, _ ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(start = 16.dp)
        ) {
            itemsIndexed(item) { index, item ->
                StandardProgramParticipantListItem(
                    index = index + 1,
                    data = item,
                    horizontalScrollState = horizontalScrollState
                )
            }
        }
    }
}

@Preview
@Composable
private fun StandardParticipantListPreview() {
    StandardParticipantList(
        horizontalScrollState = ScrollState(1),
        item = persistentListOf(
            StandardAttendListResponseEntity(
                entryTime = "입장시간",
                id = 1,
                leaveTime = "퇴장시간",
                name = "연수자 이름",
                programName = "연수 이름",
                status = true
            )
        )
    )
}