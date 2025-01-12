package com.school_of_company.training.view.component

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
import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun TrainingProgramParticipantList(
    modifier: Modifier = Modifier,
    item: ImmutableList<TeacherTrainingProgramResponseEntity> = persistentListOf(),
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
                TrainingProgramParticipantListItem(
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
private fun HomeDetailProgramParticipantListPreview() {
    TrainingProgramParticipantList(
        item = persistentListOf(
            TeacherTrainingProgramResponseEntity(
                id = 0,
                name = "홍길동",
                organization = "한국대학교",
                position = "교사",
                programName = "프로그램",
                status = true,
                entryTime = "2024-09-12 T 08:30",
                leaveTime = "2024-09-12 T 08:30"
            )
        ),
        horizontalScrollState = ScrollState(1)
    )
}