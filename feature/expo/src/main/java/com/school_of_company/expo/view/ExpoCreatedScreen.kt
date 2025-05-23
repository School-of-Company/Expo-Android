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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.school_of_company.expo.view.component.ExpoCreatedTable
import com.school_of_company.expo.view.component.ExpoCreatedTopCard
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import com.school_of_company.ui.preview.ExpoPreviews
import com.school_of_company.ui.toast.makeToast
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun ExpoCreatedRoute(
    modifier: Modifier = Modifier,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    expoViewModel: ExpoViewModel = hiltViewModel(),
) {
    val getExpoListUiState by expoViewModel.getExpoListUiState.collectAsStateWithLifecycle()
    val deleteExpoInformationUiState by expoViewModel.deleteExpoInformationUiState.collectAsStateWithLifecycle()
    val swipeRefreshLoading by expoViewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val expoListSize by expoViewModel.expoListSize.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)
    val (selectedIndex, setSelectedIndex) = rememberSaveable { mutableIntStateOf(-1) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        expoViewModel.getExpoList()
    }

    DisposableEffect(Unit) {
        onDispose {
            expoViewModel.resetDeleteExpoInformationState()
        }
    }

    LaunchedEffect(deleteExpoInformationUiState) {
        when (deleteExpoInformationUiState) {
            is DeleteExpoInformationUiState.Loading -> Unit
            is DeleteExpoInformationUiState.Success -> {
                setSelectedIndex(-1)
                expoViewModel.getExpoList()
                makeToast(context, "박람회가 삭제되었습니다.")
            }
            is DeleteExpoInformationUiState.Error -> {
                onErrorToast(null, R.string.expo_delete_fail)
            }
        }
    }

    ExpoCreatedScreen(
        modifier = modifier,
        expoListSize = expoListSize,
        selectedIndex = selectedIndex,
        getExpoListUiState = getExpoListUiState,
        swipeRefreshState = swipeRefreshState,
        initCreatedExpoList = expoViewModel::getExpoList,
        setSelectedIndex = setSelectedIndex,
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
    swipeRefreshState: SwipeRefreshState,
    scrollState: ScrollState = rememberScrollState(),
    initCreatedExpoList: () -> Unit,
    setSelectedIndex: (Int) -> Unit,
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
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                CreatedExpoList(
                                    selectedIndex = selectedIndex,
                                    expoList = getExpoListUiState.data.toPersistentList(),
                                    scrollState = scrollState,
                                    onItemClick = { isSelected, index ->
                                        setSelectedIndex(if (isSelected) -1 else index)
                                    },
                                    deleteSelectedExpo = deleteSelectedExpo,
                                    enabled = selectedIndex != -1
                                )
                            }
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
        }
    }
}

@ExpoPreviews
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
                ),
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
                ),
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
                ),
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