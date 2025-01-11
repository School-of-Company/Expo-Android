package com.school_of_company.program.view.component

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
import com.school_of_company.program.view.generateParticipantManagementSampleData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ProgramDetailParticipantManagementList(
    modifier: Modifier = Modifier,
    item: ImmutableList<HomeDetailParticipantManagementData> = persistentListOf()
) {
    ExpoAndroidTheme { colors, _ ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(start = 16.dp)
        ) {
            itemsIndexed(item) { index, item ->
                ProgramDetailParticipantManagementListItem(
                    index = index + 1,
                    data = item
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailParticipantManagementListPreview() {
    ProgramDetailParticipantManagementList(
        item = generateParticipantManagementSampleData()
    )
}