package com.school_of_company.expo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoEnableButton
import com.school_of_company.design_system.component.button.ExpoEnableDetailButton
import com.school_of_company.design_system.component.loading.LoadingDot
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.ExpoDetailModifyDialog
import com.school_of_company.expo.view.component.HomeKakaoMap
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.GetCoordinatesToAddressUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetStandardProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.GetTrainingProgramListUiState
import com.school_of_company.model.enum.Authority
import com.school_of_company.model.enum.ParticipantType
import com.school_of_company.ui.util.formatServerDate

@Composable
internal fun ExpoDetailRoute(
    modifier: Modifier = Modifier,
    id: String,
    onBackClick: () -> Unit,
    onCheckClick: (String, String, String) -> Unit,
    onModifyClick: (String) -> Unit,
    onProgramClick: (String) -> Unit,
    onMessageClick: (String, String) -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    navigationToFormCreate: (String, String) -> Unit,
    navigationToFormModify: (String, String) -> Unit,
    viewModel: ExpoViewModel = hiltViewModel(),
) {
    val getExpoInformationUiState by viewModel.getExpoInformationUiState.collectAsStateWithLifecycle()
    val getTrainingProgramUiState by viewModel.getTrainingProgramListUiState.collectAsStateWithLifecycle()
    val getStandardProgramUiState by viewModel.getStandardProgramListUiState.collectAsStateWithLifecycle()
    val getCoordinatesToAddressUiState by viewModel.getCoordinatesToAddressUiState.collectAsStateWithLifecycle()

    LaunchedEffect(getCoordinatesToAddressUiState) {
        when (getCoordinatesToAddressUiState) {
            is GetCoordinatesToAddressUiState.Loading -> Unit
            is GetCoordinatesToAddressUiState.Success -> Unit
            is GetCoordinatesToAddressUiState.Error -> onErrorToast(
                null,
                R.string.convert_coordinates_to_address_fail
            )
        }
    }

    ExpoDetailScreen(
        modifier = modifier,
        id = id,
        getExpoInformationUiState = getExpoInformationUiState,
        getTrainingProgramUiState = getTrainingProgramUiState,
        getStandardProgramUiState = getStandardProgramUiState,
        getCoordinatesToAddressUiState = getCoordinatesToAddressUiState,
        onBackClick = onBackClick,
        onMessageClick = { authority ->
            onMessageClick(id, authority)
        },
        onCheckClick = { start, end -> onCheckClick(id, start, end) },
        onModifyClick = onModifyClick,
        onProgramClick = onProgramClick,
        navigationToFormCreate = { type ->
            navigationToFormCreate(
                id,
                type,
            )
        },
        navigationToFormModify = { type ->
            navigationToFormModify(
                id,
                type,
            )
        },
    )

    LaunchedEffect(Unit) {
        viewModel.getExpoInformation(expoId = id)
        viewModel.getTrainingProgramList(expoId = id)
        viewModel.getStandardProgramList(expoId = id)
    }
}

@Composable
private fun ExpoDetailScreen(
    modifier: Modifier = Modifier,
    id: String,
    getExpoInformationUiState: GetExpoInformationUiState,
    getTrainingProgramUiState: GetTrainingProgramListUiState,
    getStandardProgramUiState: GetStandardProgramListUiState,
    getCoordinatesToAddressUiState: GetCoordinatesToAddressUiState,
    scrollState: ScrollState = rememberScrollState(),
    onBackClick: () -> Unit,
    onMessageClick: (String) -> Unit,
    onModifyClick: (String) -> Unit,
    onProgramClick: (String) -> Unit,
    onCheckClick: (String, String) -> Unit,
    navigationToFormCreate: (String) -> Unit,
    navigationToFormModify: (String) -> Unit,
) {
    val (openDialog, isOpenDialog) = rememberSaveable { mutableStateOf(false) }
    val (openModifyDialog, isOpenModifyDialog) = rememberSaveable { mutableStateOf(false) }
    val (openFormModifyDialog, isOpenFormModifyDialog) = rememberSaveable { mutableStateOf(false) }
    val (openFormCreateDialog, isOpenFormCreateDialog) = rememberSaveable { mutableStateOf(false) }

    var expandedExpoIntroductionTextState by rememberSaveable { mutableStateOf(false) }
    var showReadMoreButtonState by rememberSaveable { mutableStateOf(false) }

    val maxLines = if (expandedExpoIntroductionTextState) 100 else 5

    ExpoAndroidTheme { colors, typography ->
        when {

            getExpoInformationUiState is GetExpoInformationUiState.Success && getTrainingProgramUiState is GetTrainingProgramListUiState.Success && getStandardProgramUiState is GetStandardProgramListUiState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = colors.white)
                        .padding(horizontal = 16.dp)
                ) {
                    ExpoTopBar(
                        startIcon = {
                            LeftArrowIcon(
                                tint = colors.black,
                                modifier = Modifier.expoClickable { onBackClick() }
                            )
                        },
                        betweenText = getExpoInformationUiState.data.title,
                        modifier = Modifier.padding(top = 68.dp)
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        if (getExpoInformationUiState.data.coverImage != null) {
                            Image(
                                painter = rememberAsyncImagePainter(model = getExpoInformationUiState.data.coverImage),
                                contentDescription = stringResource(id = R.string.HomeScreen_Image_description),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(178.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(
                                        width = 1.dp,
                                        color = colors.gray200,
                                        shape = RoundedCornerShape(size = 6.dp)
                                    )
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = colors.white)
                                    .height(178.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(
                                        width = 1.dp,
                                        color = colors.gray200,
                                        shape = RoundedCornerShape(size = 6.dp)
                                    )
                            ) {
                                Text(
                                    text = "이미지가 없습니다.",
                                    style = typography.bodyRegular2,
                                    color = colors.gray400,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {
                            Text(
                                text = "행사 기간",
                                style = typography.bodyRegular2,
                                color = colors.gray600,
                                fontWeight = FontWeight(600),
                            )

                            Text(
                                text = stringResource(
                                    R.string.date_type,
                                    getExpoInformationUiState.data.startedDay.formatServerDate(),
                                    getExpoInformationUiState.data.finishedDay.formatServerDate()
                                ),
                                style = typography.captionRegular1,
                                color = colors.gray600,
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {
                            Text(
                                text = "소개글",
                                style = typography.bodyRegular2,
                                color = colors.gray600,
                                fontWeight = FontWeight(600),
                            )

                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = getExpoInformationUiState.data.description,
                                    style = typography.bodyRegular2,
                                    color = colors.gray400,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = maxLines,
                                    onTextLayout = { textLayoutResult: TextLayoutResult ->
                                        if (textLayoutResult.lineCount > 4) {
                                            if (textLayoutResult.isLineEllipsized(4)) showReadMoreButtonState =
                                                true
                                        } else {
                                            showReadMoreButtonState = false
                                        }
                                    }
                                )

                                if (!expandedExpoIntroductionTextState && showReadMoreButtonState) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .align(Alignment.BottomCenter)
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        colors.white
                                                    ),
                                                    startY = 0f,
                                                    endY = 120f
                                                )
                                            )
                                    )
                                }
                            }

                            if (showReadMoreButtonState) {
                                Text(
                                    text = if (expandedExpoIntroductionTextState) "접기" else "더보기",
                                    color = if (expandedExpoIntroductionTextState) colors.main else colors.gray200,
                                    modifier = Modifier.expoClickable {
                                        expandedExpoIntroductionTextState =
                                            !expandedExpoIntroductionTextState
                                    },
                                    style = typography.bodyRegular2
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {

                            Text(
                                text = "프로그램",
                                style = typography.bodyRegular2,
                                color = colors.gray600,
                                fontWeight = FontWeight(600),
                            )

                            Column {
                                Text(
                                    text = "일반 프로그램",
                                    style = typography.bodyRegular2,
                                    color = colors.gray400
                                )

                                if (getStandardProgramUiState.data.isEmpty()) {
                                    Text(
                                        text = "· 일반 프로그램이 존재하지 않음",
                                        style = typography.bodyRegular2,
                                        color = colors.gray400
                                    )
                                } else {
                                    getStandardProgramUiState.data.forEach { program ->
                                        Text(
                                            text = "· ${program.title}",
                                            style = typography.bodyRegular2,
                                            color = colors.gray400
                                        )
                                    }
                                }
                            }

                            Column {
                                Text(
                                    text = "연수자 프로그램",
                                    style = typography.bodyRegular2,
                                    color = colors.gray400
                                )

                                if (getTrainingProgramUiState.data.isEmpty()) {
                                    Text(
                                        text = "· 연수자 프로그램이 존재하지 않음",
                                        style = typography.bodyRegular2,
                                        color = colors.gray400
                                    )
                                } else {
                                    getTrainingProgramUiState.data.forEach { program ->
                                        Text(
                                            text = "· ${program.title}",
                                            style = typography.bodyRegular2,
                                            color = colors.gray400
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {
                            Text(
                                text = "장소",
                                style = typography.bodyRegular2,
                                color = colors.gray600,
                                fontWeight = FontWeight(600),
                            )
                            val addressMessage = when (getCoordinatesToAddressUiState) {
                                is GetCoordinatesToAddressUiState.Error -> "오류 발생"
                                is GetCoordinatesToAddressUiState.Loading -> "로딩중입니다.."
                                is GetCoordinatesToAddressUiState.Success -> "주소 : ${getCoordinatesToAddressUiState.data.addressName}"
                            }

                            Text(
                                text = addressMessage,
                                style = typography.bodyRegular2,
                                color = colors.gray400,
                            )

                            Text(
                                text = "상세주소 : ${getExpoInformationUiState.data.location}",
                                style = typography.bodyRegular2,
                                color = colors.gray400,
                            )

                            HomeKakaoMap(
                                locationY = getExpoInformationUiState.data.y.toDouble(),
                                locationX = getExpoInformationUiState.data.x.toDouble(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = colors.gray200,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Spacer(modifier = Modifier.padding(bottom = 38.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    16.dp,
                                    Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                ExpoEnableButton(
                                    text = "프로그램",
                                    textColor = colors.main,
                                    backgroundColor = colors.white,
                                    onClick = { onProgramClick(id) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(
                                            width = 1.dp,
                                            color = colors.main,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                )

                                ExpoEnableButton(
                                    text = "조회하기",
                                    textColor = colors.main,
                                    backgroundColor = colors.white,
                                    onClick = {
                                        onCheckClick(
                                            getExpoInformationUiState.data.startedDay,
                                            getExpoInformationUiState.data.finishedDay,
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .border(
                                            width = 1.dp,
                                            color = colors.main,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                )
                            }

                            ExpoEnableButton(
                                text = "문자 보내기(사용X)",
                                onClick = { isOpenDialog(false) },
                                textColor = colors.main,
                                backgroundColor = colors.white,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = colors.main,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )

                            ExpoEnableDetailButton(
                                text = "폼 생성하기",
                                onClick = { isOpenFormCreateDialog(true) },
                                modifier = Modifier.fillMaxWidth()
                            )

                            ExpoEnableButton(
                                text = "수정하기",
                                onClick = { isOpenModifyDialog(true) },
                                textColor = colors.gray700,
                                backgroundColor = colors.gray100,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.padding(bottom = 28.dp))
                    }
                }
            }

            getExpoInformationUiState is GetExpoInformationUiState.Loading || getTrainingProgramUiState is GetTrainingProgramListUiState.Loading || getStandardProgramUiState is GetStandardProgramListUiState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colors.white)
                ) {
                    LoadingDot()
                }
            }

            getExpoInformationUiState is GetExpoInformationUiState.Error || getTrainingProgramUiState is GetTrainingProgramListUiState.Error || getStandardProgramUiState is GetStandardProgramListUiState.Error -> {

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(color = colors.white)
                        .padding(horizontal = 16.dp)
                ) {
                    ExpoTopBar(
                        startIcon = {
                            LeftArrowIcon(
                                tint = colors.black,
                                modifier = Modifier.expoClickable { onBackClick() }
                            )
                        },
                        betweenText = "나가기",
                        modifier = Modifier.padding(top = 68.dp)
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            28.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = colors.white)
                    ) {
                        WarnIcon(
                            tint = colors.black,
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "네트워크가 불안정해요..",
                            style = typography.bodyRegular2,
                            color = colors.gray400
                        )
                    }
                }
            }
        }
    }

    if (openDialog) {
        Dialog(onDismissRequest = { isOpenDialog(false) }) {
            ExpoDetailModifyDialog(
                titleText = "누구에게 문자를 전송하시겠습니까?",
                startButtonText = "참가자",
                endButtonText = "연수자",
                onStartClick = {
                    isOpenDialog(false)
                    onMessageClick(Authority.ROLE_STANDARD.name)
                },
                onEndClick = {
                    isOpenDialog(false)
                    onMessageClick(Authority.ROLE_TRAINEE.name)
                },
                onDismissClick = { isOpenDialog(false) }
            )
        }
    }

    if (openModifyDialog) {
        Dialog(onDismissRequest = { isOpenModifyDialog(false) }) {
            ExpoDetailModifyDialog(
                titleText = "수정할 항목을 선택해주세요",
                startButtonText = "박람회",
                endButtonText = "폼",
                onStartClick = {
                    isOpenModifyDialog(false)
                    onModifyClick(id)
                },
                onEndClick = {
                    isOpenModifyDialog(false)
                    isOpenFormModifyDialog(true)
                },
                onDismissClick = { isOpenModifyDialog(false) }
            )
        }
    }

    if (openFormModifyDialog) {
        Dialog(onDismissRequest = { isOpenFormModifyDialog(false) }) {
            ExpoDetailModifyDialog(
                titleText = "어떤 폼을 수정하시겠습니까?",
                startButtonText = "참가자",
                endButtonText = "연수자",
                onStartClick = {
                    isOpenFormModifyDialog(false)
                    navigationToFormModify(ParticipantType.STANDARD.name)
                },
                onEndClick = {
                    isOpenFormModifyDialog(false)
                    navigationToFormModify(ParticipantType.TRAINEE.name)
                },
                onDismissClick = { isOpenFormModifyDialog(false) }
            )
        }
    }

    if (openFormCreateDialog) {
        Dialog(onDismissRequest = { isOpenFormCreateDialog(false) }) {
            ExpoDetailModifyDialog(
                titleText = "어떤 폼을 생성하시겠습니까?",
                startButtonText = "참가자",
                endButtonText = "연수자",
                onStartClick = {
                    isOpenFormCreateDialog(false)
                    navigationToFormCreate(ParticipantType.STANDARD.name)
                },
                onEndClick = {
                    isOpenFormCreateDialog(false)
                    navigationToFormCreate(ParticipantType.TRAINEE.name)
                },
                onDismissClick = { isOpenFormCreateDialog(false) }
            )
        }
    }
}

@Preview
@Composable
private fun HomeDetailScreenPreview() {
    ExpoDetailScreen(
        id = "",
        getExpoInformationUiState = GetExpoInformationUiState.Loading,
        getTrainingProgramUiState = GetTrainingProgramListUiState.Loading,
        getStandardProgramUiState = GetStandardProgramListUiState.Loading,
        getCoordinatesToAddressUiState = GetCoordinatesToAddressUiState.Loading,
        scrollState = ScrollState(0),
        onBackClick = {},
        onMessageClick = {},
        onCheckClick = { _, _ -> },
        onModifyClick = {},
        onProgramClick = {},
        navigationToFormCreate = { _ -> },
        navigationToFormModify = { _ -> }
    )
}