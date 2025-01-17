package com.school_of_company.program.view

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.DownArrowIcon
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.program.view.component.HomeDetailParticipantManagementData
import com.school_of_company.program.view.component.ProgramDetailParticipantDropdownMenu
import com.school_of_company.program.viewmodel.ProgramViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

internal fun generateParticipantManagementSampleData(): ImmutableList<HomeDetailParticipantManagementData> {
    return List(20) {
        HomeDetailParticipantManagementData(
            name = "이명훈",
            company = "초등학교",
            position = "교사",
            schoolSubject = "컴퓨터공학",
            checkHere = true,
            inTime = "12:00",
            outTime = "13:00"
        )
    }.toPersistentList()
}

@Composable
internal fun ProgramDetailParticipantManagementRoute(
    onBackClick: () -> Unit,
    viewModel: ProgramViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    ProgramDetailParticipantManagementScreen(
        onBackClick = onBackClick,
        participantManagementData = generateParticipantManagementSampleData(),
        swipeRefreshState = swipeRefreshState
    )
}

@Composable
private fun ProgramDetailParticipantManagementScreen(
    modifier: Modifier = Modifier,
    participantManagementData: ImmutableList<HomeDetailParticipantManagementData>,
    onBackClick: () -> Unit,
    swipeRefreshState: SwipeRefreshState
) {
    var participantTextState by remember { mutableStateOf("사전 행사 참가자") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Int?>(0) }


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

            Spacer(modifier = Modifier.height(25.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.expoClickable { isDropdownExpanded = true }
            ) {
                Text(
                    text = participantTextState,
                    style = typography.bodyBold2,
                    color = colors.black,
                    modifier = Modifier.padding(start = 16.dp)
                )

                DownArrowIcon(
                    tint = colors.black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Box(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
                if (isDropdownExpanded) {
                    ProgramDetailParticipantDropdownMenu(
                        onCancelClick = { isDropdownExpanded = false },
                        onExpandClick = isDropdownExpanded,
                        selectedItem = selectedItem,
                        onItemSelected = { index ->
                            selectedItem = index
                            isDropdownExpanded = false

                            participantTextState = when (index) {
                                0 -> "사전 행사 참가자"
                                1 -> "현장 행사 참가자"
                                2 -> "사전 교원 원수 참가자"
                                else -> participantTextState
                            }
                        },
                    )
                }
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

                    Text(
                        text = "${participantManagementData.size}명",
                        style = typography.captionRegular2,
                        color = colors.main
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
                        vertical = 16.dp
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
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
                        text = "프로그램 선택",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(120.dp)
                    )

                    Text(
                        text = "출석 여부",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(100.dp)
                    )

                    Text(
                        text = "입실시간",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(80.dp)
                    )

                    Text(
                        text = "퇴실시간",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(80.dp)
                    )
                }
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {  },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main
                    )
                }
            ) {
                when (selectedItem) {
                    0 -> {

                    }

                    1 -> {

                    }

                    2 -> {

                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailParticipantManagementScreenPreview() {
    ProgramDetailParticipantManagementScreen(
        onBackClick = {},
        participantManagementData = generateParticipantManagementSampleData(),
        swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    )
}