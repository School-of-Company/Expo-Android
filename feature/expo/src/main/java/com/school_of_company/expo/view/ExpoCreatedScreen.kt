package com.school_of_company.expo.view

import CreatedExpoList
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.uistate.empty.ShowEmptyState
import com.school_of_company.design_system.component.uistate.error.ShowErrorState
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.ExpoCreatedDeleteButton
import com.school_of_company.expo.view.component.ExpoCreatedTable
import com.school_of_company.expo.view.component.ExpoCreatedTopCard
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun ExpoCreatedRoute(
    modifier: Modifier = Modifier,
    expoViewModel: ExpoViewModel = hiltViewModel(),
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    val getExpoListUiState by expoViewModel.getExpoListUiState.collectAsStateWithLifecycle()
    val swipeRefreshLoading by expoViewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val expoListSize by expoViewModel.expoListSize.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)
    val (selectedIndex, setSelectedIndex) = rememberSaveable { mutableIntStateOf(-1) }

    LaunchedEffect("initCreatedExpo") {
        expoViewModel.getExpoList()
    }

    LaunchedEffect(getExpoListUiState) {
        when (getExpoListUiState) {
            is GetExpoListUiState.Empty -> Unit
            is GetExpoListUiState.Error -> onErrorToast(null, R.string.get_created_expo_list_fail)
            is GetExpoListUiState.Success -> setSelectedIndex(-1)
            else -> Unit
        }
    }

    ExpoCreatedScreen(
        modifier = modifier,
        expoListSize = expoListSize,
        selectedIndex = selectedIndex,
        setSelectedIndex = setSelectedIndex,
        getExpoListUiState = getExpoListUiState,
        swipeRefreshState = swipeRefreshState,
        initCreatedExpoList = expoViewModel::getExpoList,
        deleteSelectedExpo = { selectedId ->
            val currentGetExpoListUiState = getExpoListUiState
            if (selectedId == -1) {
                onErrorToast(null, R.string.expo_not_selected)
            } else if (currentGetExpoListUiState is GetExpoListUiState.Success) expoViewModel.deleteExpoInformation(
                currentGetExpoListUiState.data[selectedId].id
            )
        },
    )
}

@Composable
private fun ExpoCreatedScreen(
    modifier: Modifier = Modifier,
    expoListSize: Int,
    selectedIndex: Int,
    getExpoListUiState: GetExpoListUiState,
    scrollState: ScrollState = rememberScrollState(),
    swipeRefreshState: SwipeRefreshState,
    setSelectedIndex: (Int) -> Unit,
    initCreatedExpoList: () -> Unit,
    deleteSelectedExpo: (Int) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(color = colors.white)
                .padding(top = 68.dp)
                .fillMaxSize(),
        ) {
            ExpoCreatedTopCard(
                modifier = Modifier.fillMaxWidth(),
                totalExpo = expoListSize,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                ExpoCreatedTable(modifier = Modifier.fillMaxWidth())
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { initCreatedExpoList() },
                    indicator = { state, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = state,
                            refreshTriggerDistance = refreshTrigger,
                            contentColor = colors.main
                        )
                    }
                ) {
                    when (getExpoListUiState) {
                        is GetExpoListUiState.Loading -> Unit
                        is GetExpoListUiState.Success -> {
                            CreatedExpoList(
                                scrollState = scrollState,
                                expoList = getExpoListUiState.data.toPersistentList(),
                                onItemClick = { isSelected, index ->
                                    setSelectedIndex(if (isSelected) -1 else index)
                                },
                                selectedIndex = selectedIndex,
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            ExpoCreatedDeleteButton(
                                enabled = selectedIndex != -1,
                                onClick = { deleteSelectedExpo(selectedIndex) }
                            )
                        }

                        is GetExpoListUiState.Empty -> {
                            ShowEmptyState(
                                scrollState = scrollState,
                                emptyMessage = "등뢱된 박람회가 없습니다.."
                            )
                        }

                        is GetExpoListUiState.Error -> {
                            ShowErrorState(
                                scrollState = scrollState,
                                errorText = "등록된 박람회를 불러올 수 없습니다.."
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            // TODO: 데이터 로딩시에 버튼 안보이게 설정
            ExpoCreatedDeleteButton(
                enabled = selectedIndex != -1,
                onClick = { deleteSelectedExpo(selectedIndex) }
            )
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
                    startedDay = "2024-11-23",
                    finishedDay = "2024-11-23",
                    coverImage = null,
                ),
                ExpoListResponseEntity(
                    id = "2",
                    title = "제목",
                    description = "묘사",
                    startedDay = "2024-11-23",
                    finishedDay = "2024-11-23",
                    coverImage = null,
                )
            )
        ),
        expoListSize = 100,
        deleteSelectedExpo = { _ -> },
        swipeRefreshState = SwipeRefreshState(isRefreshing = true),
        initCreatedExpoList = {},
        selectedIndex = 9,
        setSelectedIndex = { _ -> }
    )
}