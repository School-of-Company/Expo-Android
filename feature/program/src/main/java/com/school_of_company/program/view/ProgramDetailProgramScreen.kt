package com.school_of_company.program.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.school_of_company.design_system.component.uistate.empty.ShowEmptyState
import com.school_of_company.design_system.component.uistate.error.ShowErrorState
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.program.view.component.ProgramList
import com.school_of_company.program.view.component.ProgramTabRowItem
import com.school_of_company.program.view.component.StandardProgramList
import com.school_of_company.program.viewmodel.ProgramViewModel
import com.school_of_company.program.viewmodel.uistate.StandardProgramListUiState
import com.school_of_company.program.viewmodel.uistate.TrainingProgramListUiState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun ProgramDetailProgramRoute(
    modifier: Modifier = Modifier,
    id: String,
    onBackClick: () -> Unit,
    navigateToTrainingProgramDetail: (Long) -> Unit,
    navigateToStandardProgramDetail: (Long) -> Unit,
    viewModel: ProgramViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val trainingProgramListUiState by viewModel.trainingProgramListUiState.collectAsStateWithLifecycle()
    val standardProgramListUiState by viewModel.standardProgramListUiState.collectAsStateWithLifecycle()


    ProgramDetailProgramScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        swipeRefreshState = swipeRefreshState,
        trainingProgramUiState = trainingProgramListUiState,
        standardProgramListUiState = standardProgramListUiState,
        getTrainingProgramList = { viewModel.trainingProgramList(id) },
        getStandardProgramList = { viewModel.standardProgramList(id) },
        navigateToTrainingProgramDetail = navigateToTrainingProgramDetail,
        navigateToStandardProgramDetail = navigateToStandardProgramDetail,
    )

    LaunchedEffect(id) {
        viewModel.trainingProgramList(id)
        viewModel.standardProgramList(id)
    }
}

@Composable
private fun ProgramDetailProgramScreen(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    trainingProgramUiState: TrainingProgramListUiState,
    standardProgramListUiState: StandardProgramListUiState,
    scrollState: ScrollState = rememberScrollState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    pagerState: PagerState = rememberPagerState(pageCount = { 2 }),
    onBackClick: () -> Unit,
    getTrainingProgramList: () -> Unit,
    getStandardProgramList: () -> Unit,
    navigateToTrainingProgramDetail: (Long) -> Unit,
    navigateToStandardProgramDetail: (Long) -> Unit,
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

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = colors.gray200)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = colors.white
                    )
                    .padding(all = 16.dp)
                    .horizontalScroll(scrollState)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Spacer(modifier = Modifier.width(62.dp))

                    Text(
                        text = "프로그램",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(100.dp)
                    )

                    Spacer(modifier = Modifier.width(64.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(64.dp, Alignment.End)
                    ) {
                        Text(
                            text = "시작시간",
                            style = typography.captionBold1,
                            color = colors.gray600,
                            modifier = Modifier.width(150.dp)
                        )

                        Text(
                            text = "종료시간",
                            style = typography.captionBold1,
                            color = colors.gray600,
                            modifier = Modifier.width(150.dp)
                        )

                        Text(
                            text = "필수",
                            style = typography.captionBold1,
                            color = colors.gray600,
                            modifier = Modifier.width(40.dp)
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = colors.gray100)
            )

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
                                        navigateToStandardProgramDetail = navigateToStandardProgramDetail,
                                        horizontalScrollState = scrollState
                                    )
                                }

                                is StandardProgramListUiState.Empty -> {
                                    ShowEmptyState(
                                        scrollState = scrollState,
                                        emptyMessage = "아직 일반 프로그램이 등장하지 않았아요.."
                                    )
                                }

                                is StandardProgramListUiState.Error -> {
                                    ShowErrorState(
                                        scrollState = scrollState,
                                        errorText = "데이터를 불러올 수 없어요!"
                                    )
                                }
                            }
                        }

                        1 -> {
                            when (trainingProgramUiState) {
                                is TrainingProgramListUiState.Loading -> Unit
                                is TrainingProgramListUiState.Success -> {
                                    ProgramList(
                                        trainingItem = trainingProgramUiState.data.toImmutableList(),
                                        navigateToTrainingProgramDetail = navigateToTrainingProgramDetail,
                                        horizontalScrollState = scrollState
                                    )
                                }

                                is TrainingProgramListUiState.Empty -> {
                                    ShowEmptyState(
                                        scrollState = scrollState,
                                        emptyMessage = "아직 연수 프로그램이 등장하지 않았아요.."
                                    )
                                }

                                is TrainingProgramListUiState.Error -> {
                                    ShowErrorState(
                                        scrollState = scrollState,
                                        errorText = "데이터를 불러올 수 없어요!"
                                    )
                                }
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
    ProgramDetailProgramScreen(
        onBackClick = {},
        trainingProgramUiState = TrainingProgramListUiState.Loading,
        standardProgramListUiState = StandardProgramListUiState.Loading,
        swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
        getTrainingProgramList = {},
        getStandardProgramList = {},
        navigateToStandardProgramDetail = {},
        navigateToTrainingProgramDetail = {}
    )
}