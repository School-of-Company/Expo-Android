package com.school_of_company.expo.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.ExpoTypography
import com.school_of_company.design_system.theme.color.ColorTheme
import com.school_of_company.expo.view.component.CreatedExpoList
import com.school_of_company.expo.view.component.ExpoCreatedDeleteButton
import com.school_of_company.expo.view.component.ExpoCreatedTable
import com.school_of_company.expo.view.component.ExpoCreatedTopCard
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.immutableListOf

@Composable
internal fun ExpoCreatedRoute(
    modifier: Modifier = Modifier,
    expoViewModel: ExpoViewModel = hiltViewModel(),
) {
    val getExpoListUiState by expoViewModel.getExpoListUiState.collectAsStateWithLifecycle()

    ExpoCreatedScreen(
        modifier = modifier,
        participantCount = 0,
        getExpoListUiState = getExpoListUiState,
        deleteExpoInformation = expoViewModel::deleteExpoInformation,
    )
}

@Composable
private fun ExpoCreatedScreen(
    modifier: Modifier = Modifier,
    participantCount: Int,
    getExpoListUiState: GetExpoListUiState,
    scrollState: ScrollState = rememberScrollState(),
    deleteExpoInformation: (String) -> Unit,
) {
    val (selectedId, setSelectedId) = rememberSaveable { mutableLongStateOf(0L) }

    ExpoAndroidTheme { colors: ColorTheme, typography: ExpoTypography ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = colors.white)
                .padding(top = 40.dp)
                .fillMaxSize(),
        ) {
            ExpoCreatedTopCard(
                modifier = Modifier.fillMaxWidth(),
                participantCount = participantCount,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .border(width = 1.dp, color = colors.gray200)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .horizontalScroll(scrollState),
            ) {
                ExpoCreatedTable()
                when (getExpoListUiState) {
                    GetExpoListUiState.Empty -> TODO()
                    is GetExpoListUiState.Error -> TODO()
                    GetExpoListUiState.Loading -> TODO()
                    is GetExpoListUiState.Success -> {
                        CreatedExpoList(
                            expoList = getExpoListUiState.data,
                            onItemClick = { isSelected, id ->
                                setSelectedId(if (isSelected) 0L else id)
                            },
                            selectedIndex = selectedId,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            ExpoCreatedDeleteButton(
                enabled = selectedId != 0L,
                onClick = { deleteExpoInformation(selectedId.toString()) })
        }
    }
}

@Preview
@Composable
private fun ExpoCreatedScreenPreview() {
    ExpoCreatedScreen(
        getExpoListUiState = GetExpoListUiState.Success(
            immutableListOf(
                ExpoListResponseEntity(
                    id = "2",
                    title = "제목",
                    description = "묘사",
                    startedDay = "어제",
                    finishedDay = "오늘",
                    coverImage = null,
                ),
                ExpoListResponseEntity(
                    id = "2",
                    title = "제목",
                    description = "묘사",
                    startedDay = "어제",
                    finishedDay = "오늘",
                    coverImage = null,
                )
            )
        ),
        participantCount = 100,
        deleteExpoInformation = { _ -> }
    )
}