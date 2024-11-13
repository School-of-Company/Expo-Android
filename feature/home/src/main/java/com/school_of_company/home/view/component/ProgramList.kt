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
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ProgramList(
    modifier: Modifier = Modifier,
    item: ImmutableList<ProgramTempList> = persistentListOf(),
    navigateToProgramDetail: () -> Unit
) {
    ExpoAndroidTheme { colors, _ ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(item) { index, item ->
                ProgramListItem(
                    index = index + 1,
                    data = item,
                    navigateToProgramDetail = navigateToProgramDetail
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProgramListPreview() {
    ProgramList(
        item = persistentListOf(
            ProgramTempList(
                programName = "adsfasfas",
                check = true,
                must = true
            ),
            ProgramTempList(
                programName = "adsfasdf",
                check = true,
                must = false
            ),
            ProgramTempList(
                programName = "adsfasdf",
                check = false,
                must = true
            ),
            ProgramTempList(
                programName = "adsfasdf",
                check = true,
                must = false
            ),
            ProgramTempList(
                programName = "adsfasdf",
                check = false,
                must = false
            ),
            ProgramTempList(
                programName = "adsfasdf",
                check = false,
                must = false
            ),
        ),
        navigateToProgramDetail = {}
    )
}