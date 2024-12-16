package com.school_of_company.program.view.component

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
import com.school_of_company.model.entity.training.TeacherTrainingProgramResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeDetailProgramParticipantList(
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
                HomeDetailProgramParticipantListItem(
                    index = index + 1,
                    data = item,
                    horizontalScrollState = horizontalScrollState
                )
            }
        }
    }
}

@Composable
fun HomeDetailStandardParticipantList(
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
                HomeDetailStandardProgramParticipantListItem(
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

}