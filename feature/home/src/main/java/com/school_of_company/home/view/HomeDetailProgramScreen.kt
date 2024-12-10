package com.school_of_company.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.home.view.component.ProgramList
import com.school_of_company.home.view.component.ProgramTabRowItem
import com.school_of_company.home.view.component.StandardProgramList
import com.school_of_company.home.viewmodel.HomeViewModel
import com.school_of_company.home.viewmodel.uistate.StandardProgramListUiState
import com.school_of_company.home.viewmodel.uistate.TrainingProgramListUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun HomeDetailProgramRoute(
    id: String,
    onBackClick: () -> Unit,
    navigateToProgramDetail: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val trainingProgramListUiState by viewModel.trainingProgramListUiState.collectAsStateWithLifecycle()
    val standardProgramListUiState by viewModel.standardProgramListUiState.collectAsStateWithLifecycle()


    HomeDetailProgramScreen(
        id = id,
        onBackClick = onBackClick,
        navigateToProgramDetail = navigateToProgramDetail,
        trainingProgramUiState = trainingProgramListUiState,
        standardProgramListUiState = standardProgramListUiState,
        swipeRefreshState = swipeRefreshState,
        getTrainingProgramList = { viewModel.trainingProgramList(id) },
        getStandardProgramList = { viewModel.standardProgramList(id) }
    )

    LaunchedEffect(id) {
        viewModel.trainingProgramList(id)
        viewModel.standardProgramList(id)
    }
}

@Composable
internal fun HomeDetailProgramScreen(
    id: String,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    trainingProgramUiState: TrainingProgramListUiState,
    standardProgramListUiState: StandardProgramListUiState,
    pagerState: PagerState = rememberPagerState(pageCount = { 2 }),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onBackClick: () -> Unit,
    navigateToProgramDetail: (Long) -> Unit,
    getTrainingProgramList: () -> Unit,
    getStandardProgramList: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(top = 68.dp)
        ) {

            ExpoTopBar(
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onBackClick() }
                    )
                },
                betweenText = "프로그램",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.padding(top = 28.dp))

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = colors.main,
                        modifier = modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    )
                },
                modifier = Modifier.width(280.dp)
            ) {
                persistentListOf(
                    "일반 프로그램",
                    "연수 프로그램"
                ).forEachIndexed { index, title ->
                    ProgramTabRowItem(
                        isCurrentIndex = index == pagerState.currentPage,
                        title = title,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = colors.gray200
                    )
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.weight(0.5f))

                    Text(
                        text = "프로그램",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.weight(2f)
                    )

                    Text(
                        text = "선택",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "필수",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    getTrainingProgramList()
                    getStandardProgramList()
                },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main
                    )
                }
            ) {
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> {
                            when (standardProgramListUiState) {
                                is StandardProgramListUiState.Loading -> Unit
                                is StandardProgramListUiState.Success -> {
                                    StandardProgramList(
                                        standardItem = standardProgramListUiState.data.toImmutableList(),
                                        navigateToProgramDetail = navigateToProgramDetail
                                    )
                                }
                                is StandardProgramListUiState.Empty -> Unit
                                is StandardProgramListUiState.Error -> Unit
                            }
                        }

                        1 -> {
                            when (trainingProgramUiState) {
                                is TrainingProgramListUiState.Loading -> Unit
                                is TrainingProgramListUiState.Success -> {
                                    ProgramList(
                                        trainingItem = trainingProgramUiState.data.toImmutableList(),
                                        navigateToProgramDetail = navigateToProgramDetail
                                    )
                                }

                                is TrainingProgramListUiState.Empty -> Unit
                                is TrainingProgramListUiState.Error -> Unit
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailProgramScreenPreview() {
    HomeDetailProgramScreen(
        onBackClick = {},
        navigateToProgramDetail = {},
        id = "",
        trainingProgramUiState = TrainingProgramListUiState.Loading,
        standardProgramListUiState = StandardProgramListUiState.Loading,
        swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
        getTrainingProgramList = {},
        getStandardProgramList = {}
    )
}