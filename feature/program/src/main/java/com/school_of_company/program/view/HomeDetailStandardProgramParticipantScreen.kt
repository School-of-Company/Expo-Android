package com.school_of_company.home.view

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.ExpoIcon
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.home.view.component.HomeDetailStandardParticipantList
import com.school_of_company.home.view.component.QrButton
import com.school_of_company.home.viewmodel.HomeViewModel
import com.school_of_company.home.viewmodel.uistate.StandardProgramAttendListUiState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun HomeDetailStandardProgramParticipantRoute(
    id: Long,
    onBackClick: () -> Unit,
    navigateToQrScanner: (Long, Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val standardProgramAttendListUiState = viewModel.standardProgramAttendListUiState.collectAsStateWithLifecycle().value

    val standardId: Long = when (standardProgramAttendListUiState) {
        is StandardProgramAttendListUiState.Success -> {
            standardProgramAttendListUiState.data.firstOrNull()?.id ?: -1L
        }
        else -> -1L
    }

    HomeDetailStandardProgramParticipantScreen(
        id = id,
        onBackClick = onBackClick,
        standardProgramAttendListUiState = standardProgramAttendListUiState,
        swipeRefreshState = swipeRefreshState,
        getTeacherTrainingProgramList = { viewModel.standardProgramList(id) },
        navigateToQrScanner = navigateToQrScanner,
        standardId = standardId
    )

    LaunchedEffect(Unit) {
        viewModel.standardProgramList(id)
    }
}

@Composable
internal fun HomeDetailStandardProgramParticipantScreen(
    id: Long,
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    standardProgramAttendListUiState: StandardProgramAttendListUiState,
    getTeacherTrainingProgramList: () -> Unit,
    standardId: Long,
    navigateToQrScanner: (Long, Long) -> Unit,
    onBackClick: () -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
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
                betweenText = "참가자 관리",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "프로그램",
                    style = typography.bodyBold2,
                    color = colors.black,
                    modifier = Modifier.padding(start = 16.dp)
                )

                QrButton(
                    onClick = { navigateToQrScanner(id, standardId) },
                    modifier = Modifier.padding(end = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)) {
                    WarnIcon(
                        tint = colors.gray300,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "옆으로 넘겨서 확인해보세요",
                        style = typography.captionRegular2,
                        color = colors.gray300
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Spacer(
                    modifier = Modifier
                        .padding(0.dp)
                        .width(1.dp)
                        .height(14.dp)
                        .background(color = colors.gray100)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)) {
                    Text(
                        text = "참가자 전체 인원",
                        style = typography.captionRegular2,
                        color = colors.gray500
                    )
                    when (standardProgramAttendListUiState) {
                        is StandardProgramAttendListUiState.Loading -> {
                            Text(
                                text = "로딩중..",
                                style = typography.captionRegular2,
                                color = colors.gray200
                            )
                        }
                        is StandardProgramAttendListUiState.Success -> {
                            Text(
                                text = "${standardProgramAttendListUiState.data.size}명",
                                style = typography.captionRegular2,
                                color = colors.main
                            )
                        }
                        is StandardProgramAttendListUiState.Error -> {
                            Text(
                                text = "데이터를 불러올 수 없습니다..",
                                style = typography.captionRegular2,
                                color = colors.gray200
                            )
                        }
                        is StandardProgramAttendListUiState.Empty -> {
                            Text(
                                text = "0명",
                                style = typography.captionRegular2,
                                color = colors.main
                            )
                        }
                    }
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
                    .padding(vertical = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .horizontalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "성명",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(80.dp)
                    )

                    Text(
                        text = "소속",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(100.dp)
                    )

                    Text(
                        text = "직급",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(80.dp)
                    )

                    Text(
                        text = "출석여부",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(120.dp)
                    )

                    Text(
                        text = "입실시간",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(150.dp)
                    )

                    Text(
                        text = "퇴실시간",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(150.dp)
                    )
                }
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { getTeacherTrainingProgramList() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main
                    )
                }
            ) {
                when (standardProgramAttendListUiState) {
                    is StandardProgramAttendListUiState.Loading -> Unit
                    is StandardProgramAttendListUiState.Success -> {
                        HomeDetailStandardParticipantList(
                            item = standardProgramAttendListUiState.data.toImmutableList(),
                            horizontalScrollState = scrollState
                        )
                    }
                    is StandardProgramAttendListUiState.Error -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                28.dp,
                                Alignment.CenterVertically
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = colors.white)
                                .verticalScroll(scrollState)
                        ) {
                            WarnIcon(
                                tint = colors.black,
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "데이터를 불러올 수 없어요!",
                                style = typography.bodyRegular2,
                                color = colors.gray400
                            )
                        }
                    }
                    is StandardProgramAttendListUiState.Empty -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterVertically),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(scrollState)
                                .background(color = colors.white)
                        ) {
                            ExpoIcon(
                                tint = colors.black,
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                text = "아직 연수 참가자가 등장하지 않았아요..",
                                style = typography.bodyRegular2,
                                color = colors.gray400
                            )
                        }
                    }
                }
            }
        }
    }
}