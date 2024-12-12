package com.school_of_company.home.view.component

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
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity
import com.school_of_company.model.entity.training.TrainingProgramListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ProgramList(
    modifier: Modifier = Modifier,
    trainingItem: ImmutableList<TrainingProgramListResponseEntity> = persistentListOf(),
    navigateToTrainingProgramDetail: (Long) -> Unit
) {
    ExpoAndroidTheme { colors, _ ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(trainingItem) { index, item ->
                ProgramListItem(
                    index = index + 1,
                    data = item,
                    navigateToTrainingProgramDetail = navigateToTrainingProgramDetail
                )
            }
        }
    }
}

@Composable
fun StandardProgramList(
    modifier: Modifier = Modifier,
    standardItem: ImmutableList<StandardProgramListResponseEntity> = persistentListOf(),
    navigateToStandardProgramDetail: (Long) -> Unit
) {
    ExpoAndroidTheme { colors, _ ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(standardItem) { index, item ->
                StandardProgramListItem(
                    index = index + 1,
                    data = item,
                    navigateToStandardProgramDetail = navigateToStandardProgramDetail
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProgramListPreview() {
    ProgramList(
        trainingItem = persistentListOf(
            TrainingProgramListResponseEntity(
                id = 0,
                title = "title",
                startedAt = "startedAt",
                endedAt = "endedAt",
                category = "ESSENTIAL"
            )
        ),
        navigateToTrainingProgramDetail = {}
    )
}