package com.school_of_company.expo.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.R
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.shimmer.shimmerEffect
import com.school_of_company.design_system.component.uistate.error.ShowErrorState
import com.school_of_company.expo.enum.FilterOptionEnum
import com.school_of_company.expo.view.component.ExpoFormFilterDialog
import com.school_of_company.expo.view.component.ExpoList
import com.school_of_company.expo.view.component.HomeFilterButton
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ExpoRoute(
    navigationToDetail: (String) -> Unit,
    viewModel: ExpoViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)
    val getExpoListUiState by viewModel.getExpoListUiState.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    ExpoScreen(
        swipeRefreshState = swipeRefreshState,
        getExpoListData = getExpoListUiState,
        getExpoList = { viewModel.getExpoList() },
        navigationToDetail = navigationToDetail,
        selectedFilter = selectedFilter,
        onFilterSelected = { filter -> viewModel.onFilterSelected(filter) }
    )

    LaunchedEffect(Unit) {
        viewModel.getExpoList()
    }
}

@Composable
private fun ExpoScreen(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    getExpoListData: GetExpoListUiState,
    scrollState: ScrollState = rememberScrollState(),
    selectedFilter: FilterOptionEnum?,
    onFilterSelected: (FilterOptionEnum) -> Unit, // ViewModel에 전달용
    getExpoList: () -> Unit,
    navigationToDetail: (String) -> Unit
) {
    val (openFilterDialog, setOpenFilterDialog) = rememberSaveable { mutableStateOf(false) }

    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(horizontal = 15.dp)
        ) {
            Spacer(modifier = Modifier.padding(top = 50.dp))

            Text(
                text = stringResource(id = R.string.main_string),
                style = typography.mainTypo,
                fontSize = 30.sp,
                color = colors.main
            )

            Spacer(modifier = Modifier.padding(top = 30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.expo_register),
                    style = typography.titleBold2,
                    color = colors.black
                )

                HomeFilterButton(
                    text = "필터",
                    onClick = { setOpenFilterDialog(true) }
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 24.dp))

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { getExpoList() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main
                    )
                }
            ) {
                when (getExpoListData) {
                    is GetExpoListUiState.Success -> {
                        ExpoList(
                            item = getExpoListData.data.toImmutableList(),
                            emptyList = false,
                            navigateToExpoDetail = navigationToDetail
                        )
                    }

                    is GetExpoListUiState.Empty -> {
                        ExpoList(
                            emptyList = true,
                            navigateToExpoDetail = navigationToDetail
                        )
                    }

                    is GetExpoListUiState.Loading -> {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = colors.white,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .fillMaxSize()
                        ) {
                            repeat(10) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(135.dp)
                                        .padding(vertical = 10.dp)
                                        .background(
                                            color = colors.white,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .shimmerEffect(
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                )
                            }
                        }
                    }

                    is GetExpoListUiState.Error -> {
                        ShowErrorState(
                            scrollState = scrollState,
                            errorText = "네트워크가 불안정해요.."
                        )
                    }
                }
            }
        }
    }


    if (openFilterDialog) {
        Dialog(onDismissRequest = {setOpenFilterDialog(false)}) {
            ExpoFormFilterDialog(
                selectedOptions = listOf(selectedFilter),
                onStudentFormTrueClick = { onFilterSelected(FilterOptionEnum.STUDENT_FORM_TRUE) },
                onStudentFormFalseClick = { onFilterSelected(FilterOptionEnum.STUDENT_FORM_FALSE) },
                onTrainingFormFalseClick = { onFilterSelected(FilterOptionEnum.TRAINING_FORM_FALSE) },
                onTrainingFormTrueClick = { onFilterSelected(FilterOptionEnum.TRAINING_FORM_TRUE) },
                onDismissClick = { setOpenFilterDialog(false) }
            )
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    ExpoScreen(
        navigationToDetail = {},
        swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
        getExpoListData = GetExpoListUiState.Loading,
        getExpoList = {},
        onFilterSelected = {},
        selectedFilter = FilterOptionEnum.TRAINING_FORM_FALSE
    )
}