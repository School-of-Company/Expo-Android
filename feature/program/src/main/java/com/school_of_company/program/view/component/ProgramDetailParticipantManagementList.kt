package com.school_of_company.program.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity

@Composable
internal fun ProgramDetailParticipantManagementList(
    modifier: Modifier = Modifier,
    item: ParticipantInformationResponseEntity,
    scrollState: ScrollState
) {
    ExpoAndroidTheme { colors, _ ->

        LazyColumn(
            modifier = modifier
                .background(color = colors.white)
                .padding(start = 16.dp)
        ) {
            itemsIndexed(
                items = item.participant,
                key = { _, item -> item.id },
            ) { index, item ->
                ProgramDetailParticipantManagementListItem(
                    index = index + 1,
                    data = item,
                    horizontalScrollState = scrollState
                )
            }
        }
    }
}
