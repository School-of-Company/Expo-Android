package com.school_of_company.program.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.ExpoNoneLabelTextField
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.component.uistate.empty.ShowEmptyState
import com.school_of_company.design_system.component.uistate.error.ShowErrorState
import com.school_of_company.design_system.icon.DownArrowIcon
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.SearchIcon
import com.school_of_company.design_system.icon.UpArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.participant.PageInfoEntity
import com.school_of_company.model.entity.participant.ParticipantEntity
import com.school_of_company.model.entity.participant.ParticipantInformationResponseEntity
import com.school_of_company.program.view.component.LocalDateButton
import com.school_of_company.program.view.component.PageIndicator
import com.school_of_company.program.view.component.ProgramDetailParticipantDropdownMenu
import com.school_of_company.program.view.component.ProgramDetailParticipantManagementList
import com.school_of_company.program.view.component.ProgramDetailParticipantTable
import com.school_of_company.program.view.component.ProgramTraineeList
import com.school_of_company.program.view.component.ProgramTraineeTable
import com.school_of_company.program.viewmodel.ProgramViewModel
import com.school_of_company.program.viewmodel.uistate.ParticipantResponseListUiState
import com.school_of_company.program.viewmodel.uistate.TraineeResponseListUiState
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable
internal fun ProgramDetailParticipantManagementRoute(
    modifier: Modifier = Modifier,
    id: String,
    startDate: String,
    endDate: String,
    onBackClick: () -> Unit,
    viewModel: ProgramViewModel = hiltViewModel(),
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val participantListUiState by viewModel.participantListUiState.collectAsStateWithLifecycle()
    val traineeInformationUiState by viewModel.traineeResponseListUiState.collectAsStateWithLifecycle()

    val traineeName by viewModel.traineeName.collectAsStateWithLifecycle()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    var selectedDate by rememberSaveable { mutableStateOf<LocalDate?>(null) }

    LaunchedEffect(id) {

        viewModel.getParticipantInformationList(
            expoId = id,
            currentPage = 0,
        )

        viewModel.getTraineeList(expoId = id)
    }

    LaunchedEffect(traineeName) {
        delay(300L)
        viewModel.getTraineeList(
            expoId = id,
            name = traineeName
        )
    }

    LaunchedEffect(selectedDate, currentPage) {
        delay(300L)
        viewModel.getParticipantInformationList(
            expoId = id,
            localDate = selectedDate.toString(),
            currentPage = currentPage,
        )
    }

    ProgramDetailParticipantManagementScreen(
        modifier = modifier,
        swipeRefreshState = swipeRefreshState,
        participantListUiState = participantListUiState,
        traineeInformationUiState = traineeInformationUiState,
        selectedDate = selectedDate?.toString(),
        startDate = startDate,
        endDate = endDate,
        currentPage = currentPage,
        traineeName = traineeName,
        onBackClick = onBackClick,
        onSelectedDateChange = { selectedDate = it },
        onTraineeNameChange = viewModel::onTraineeNameChange,
        onCurrentPageChange = viewModel::onCurrentPageChange,
        getParticipantList = {
            viewModel.getParticipantInformationList(
                expoId = id,
                localDate = selectedDate.toString(),
                currentPage = currentPage,
            )
        },
        getTraineeList = {
            viewModel.getTraineeList(
                expoId = id,
                name = traineeName,
            )
        },
    )
}

@Composable
private fun ProgramDetailParticipantManagementScreen(
    modifier: Modifier = Modifier,
    swipeRefreshState: SwipeRefreshState,
    scrollState: ScrollState = rememberScrollState(),
    selectedDate: String?,
    startDate: String,
    endDate: String,
    currentPage: Int,
    traineeName: String,
    participantListUiState: ParticipantResponseListUiState,
    traineeInformationUiState: TraineeResponseListUiState,
    onBackClick: () -> Unit,
    onSelectedDateChange: (LocalDate?) -> Unit,
    onTraineeNameChange: (String) -> Unit,
    onCurrentPageChange: (Int) -> Unit,
    getParticipantList: () -> Unit,
    getTraineeList: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    var participantTextState by rememberSaveable { mutableStateOf("사전 & 현장 행사 참가자") }
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val parsedStartDate = rememberSaveable(startDate) { LocalDate.parse(startDate) }
    val parsedEndDate = rememberSaveable(endDate) { LocalDate.parse(endDate) }
    val parsedSelectedDate = remember(selectedDate) { selectedDate?.let { LocalDate.parse(it) } }

    val dateList = remember(parsedStartDate, parsedEndDate) {
        generateSequence(parsedStartDate) { it.plusDays(1) }
            .takeWhile { !it.isAfter(parsedEndDate) }
            .toList()
    }

    ExpoAndroidTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(top = 68.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager.clearFocus() }
                    )
                }
        ) {
            ExpoTopBar(
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onBackClick() }
                    )
                },
                betweenText = "참가자 조회",
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

                if (isDropdownExpanded) {
                    UpArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                } else {
                    DownArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }

            Box(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 10.dp
                ),
            ) {
                if (isDropdownExpanded) {

                    ProgramDetailParticipantDropdownMenu(
                        onCancelClick = { isDropdownExpanded = false },
                        selectedItem = selectedItem,
                        onItemSelected = { index ->
                            selectedItem = index
                            isDropdownExpanded = false

                            participantTextState = when (index) {
                                0 -> "사전 & 현장 행사 참가자"
                                1 -> "교원 원수 참가자"
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

                    when (selectedItem) {
                        0 -> {
                            when (participantListUiState) {
                                is ParticipantResponseListUiState.Loading -> {
                                    Text(
                                        text = "로딩중..",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }

                                is ParticipantResponseListUiState.Success -> {
                                    Text(
                                        text = "${participantListUiState.data.info.totalElement}명",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }

                                is ParticipantResponseListUiState.Error -> {
                                    Text(
                                        text = "데이터를 불러올 수 없습니다..",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }

                                is ParticipantResponseListUiState.Empty -> {
                                    Text(
                                        text = "0명",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }
                            }
                        }

                        1 -> {
                            when (traineeInformationUiState) {
                                is TraineeResponseListUiState.Loading -> {
                                    Text(
                                        text = "로딩중..",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }

                                is TraineeResponseListUiState.Success -> {
                                    Text(
                                        text = "${traineeInformationUiState.data.size}명",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }

                                is TraineeResponseListUiState.Error -> {
                                    Text(
                                        text = "데이터를 불러올 수 없습니다..",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }

                                is TraineeResponseListUiState.Empty -> {
                                    Text(
                                        text = "0명",
                                        style = typography.captionRegular2,
                                        color = colors.main
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            if (selectedItem != 2) {

                LazyRow(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dateList) { date: LocalDate ->
                        LocalDateButton(
                            date = date.toString(),
                            selected = parsedSelectedDate == date,
                            onClick = {
                                if (parsedSelectedDate == date) {
                                    onSelectedDateChange(null)
                                } else {
                                    onSelectedDateChange(date)
                                }
                            }
                        )
                    }
                }
            }

            if (selectedItem == 2) {
                ExpoNoneLabelTextField(
                    placeholder = "참가자 이름을 입력해주세요.",
                    isError = false,
                    isDisabled = false,
                    errorText = "",
                    value = traineeName,
                    onValueChange = onTraineeNameChange,
                    trailingIcon = {
                        SearchIcon(
                            tint = colors.black,
                            modifier = Modifier.clickable(onClick = getTraineeList)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .padding(horizontal = 16.dp)
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    when (selectedItem) {
                        0 -> getParticipantList()
                        1 -> getTraineeList()
                    }
                },
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
                        when (participantListUiState) {
                            is ParticipantResponseListUiState.Loading -> Unit
                            is ParticipantResponseListUiState.Success -> {
                                val data = participantListUiState.data

                                Column (horizontalAlignment = Alignment.CenterHorizontally){
                                    ProgramDetailParticipantTable(scrollState = scrollState)

                                    ProgramDetailParticipantManagementList(
                                        modifier = Modifier.weight(1f),
                                        scrollState = scrollState,
                                        item = data
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    PageIndicator(
                                        totalPages = data.info.totalPage,
                                        currentPage = currentPage,
                                        onCurrentPageChange = onCurrentPageChange,
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }

                            is ParticipantResponseListUiState.Error -> {
                                ShowErrorState(
                                    errorText = "행사 참가자를 불러올 수 없습니다..",
                                    scrollState = scrollState
                                )
                            }

                            is ParticipantResponseListUiState.Empty -> {
                                ShowEmptyState(
                                    emptyMessage = "행사 참가자가 존재하지 않습니다..",
                                    scrollState = scrollState
                                )
                            }
                        }
                    }

                    1 -> {
                        when (traineeInformationUiState) {
                            is TraineeResponseListUiState.Loading -> Unit
                            is TraineeResponseListUiState.Success -> {
                                Column {
                                    ProgramTraineeTable(scrollState = scrollState)

                                    ProgramTraineeList(
                                        scrollState = scrollState,
                                        item = traineeInformationUiState.data
                                    )
                                }
                            }

                            is TraineeResponseListUiState.Error -> {
                                ShowErrorState(
                                    errorText = "교원 원수를 불러올 수 없습니다..",
                                    scrollState = scrollState
                                )
                            }

                            is TraineeResponseListUiState.Empty -> {
                                ShowEmptyState(
                                    emptyMessage = "교원 원수가 존재하지 않습니다..",
                                    scrollState = scrollState
                                )
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
private fun HomeDetailParticipantManagementScreenPreview() {
    ProgramDetailParticipantManagementScreen(
        swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false),
        selectedDate = LocalDate.now().toString(),
        traineeName = "",
        currentPage = 0,
        participantListUiState = ParticipantResponseListUiState.Success(
            ParticipantInformationResponseEntity(
                info = PageInfoEntity(1, 10), participant = listOf(
                    ParticipantEntity(
                        id = 1,
                        name = "John Doe",
                        phoneNumber = "1234567890",
                        informationStatus = true
                    ),
                    ParticipantEntity(
                        id = 2,
                        name = "Jane Smith",
                        phoneNumber = "0987654321",
                        informationStatus = false
                    ),
                    ParticipantEntity(
                        id = 3,
                        name = "Alice Johnson",
                        phoneNumber = "1122334455",
                        informationStatus = true,
                    ),
                    ParticipantEntity(
                        id = 1,
                        name = "John Doe",
                        phoneNumber = "1234567890",
                        informationStatus = true
                    ),
                    ParticipantEntity(
                        id = 2,
                        name = "Jane Smith",
                        phoneNumber = "0987654321",
                        informationStatus = false
                    ),
                    ParticipantEntity(
                        id = 3,
                        name = "Alice Johnson",
                        phoneNumber = "1122334455",
                        informationStatus = true,
                    )
                )
            ),
        ),
        traineeInformationUiState = TraineeResponseListUiState.Loading,
        onBackClick = {},
        onTraineeNameChange = {},
        getParticipantList = {},
        getTraineeList = {},
        onSelectedDateChange = { _ -> },
        onCurrentPageChange = { _ -> },
        endDate = LocalDate.of(2025, 5, 30).toString(),
        startDate = LocalDate.of(2025, 5, 19).toString(),
    )
}