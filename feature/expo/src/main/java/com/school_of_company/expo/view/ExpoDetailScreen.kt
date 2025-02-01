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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoButton
import com.school_of_company.design_system.component.button.ExpoEnableButton
import com.school_of_company.design_system.component.button.ExpoEnableDetailButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.HomeKakaoMap
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetStandardProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.GetTrainingProgramListUiState
import com.school_of_company.model.enum.Authority
import com.school_of_company.ui.preview.ExpoPreviews
import com.school_of_company.ui.util.formatServerDate

@Composable
internal fun ExpoDetailRoute(
    modifier: Modifier = Modifier,
    id: String,
    onBackClick: () -> Unit,
    onCheckClick: (String) -> Unit,
    onModifyClick: (String) -> Unit,
    onProgramClick: (String) -> Unit,
    onMessageClick: (String, String) -> Unit,
    viewModel: ExpoViewModel = hiltViewModel(),
) {
    val getExpoInformationUiState by viewModel.getExpoInformationUiState.collectAsStateWithLifecycle()
    val getTrainingProgramUiState by viewModel.getTrainingProgramListUiState.collectAsStateWithLifecycle()
    val getStandardProgramUiState by viewModel.getStandardProgramListUiState.collectAsStateWithLifecycle()

    ExpoDetailScreen(
        modifier = modifier,
        id = id,
        getExpoInformationUiState = getExpoInformationUiState,
        getTrainingProgramUiState = getTrainingProgramUiState,
        getStandardProgramUiState = getStandardProgramUiState,
        onBackClick = onBackClick,
        onMessageClick = { authority -> onMessageClick(id, authority) },
        onCheckClick = onCheckClick,
        onModifyClick = onModifyClick,
        onProgramClick = onProgramClick
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
    scrollState: ScrollState = rememberScrollState(),
    onBackClick: () -> Unit,
    onMessageClick: (String) -> Unit,
    onCheckClick: (String) -> Unit,
    onModifyClick: (String) -> Unit,
    onProgramClick: (String) -> Unit
) {
    val (openDialog, isOpenDialog) = rememberSaveable { mutableStateOf(false) }

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
                                text = "소개글",
                                style = typography.bodyRegular2,
                                color = colors.gray600,
                                fontWeight = FontWeight(600),
                            )

                            Text(
                                text = getExpoInformationUiState.data.description,
                                style = typography.bodyRegular2,
                                color = colors.gray400
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    11.dp,
                                    Alignment.Start
                                )
                            ) {

                                Text(
                                    text = stringResource(id = R.string.register_temp),
                                    style = typography.captionRegular2,
                                    color = colors.gray600
                                )

                                Text(
                                    text = stringResource(
                                        R.string.date_type,
                                        getExpoInformationUiState.data.startedDay.formatServerDate(),
                                        getExpoInformationUiState.data.finishedDay.formatServerDate()
                                    ),
                                    style = typography.captionRegular2,
                                    color = colors.gray600,
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

                                getStandardProgramUiState.data.forEach { program ->
                                    Text(
                                        text = "· ${program.title}",
                                        style = typography.bodyRegular2,
                                        color = colors.gray400
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = "연수자 프로그램",
                                    style = typography.bodyRegular2,
                                    color = colors.gray400
                                )

                                getTrainingProgramUiState.data.forEach { program ->
                                    Text(
                                        text = "· ${program.title}",
                                        style = typography.bodyRegular2,
                                        color = colors.gray400
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {
                            Text(
                                text = "장소 지도",
                                style = typography.bodyRegular2,
                                color = colors.gray600,
                                fontWeight = FontWeight(600),
                            )

                            Text(
                                text = "장소 : ${getExpoInformationUiState.data.location}",
                                style = typography.bodyRegular2,
                                color = colors.gray400,
                            )

                            Text(
                                text = "주소 : 광주광역시 광산구 312",
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

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    16.dp,
                                    Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 38.dp)
                            ) {

                                ExpoButton(
                                    text = "프로그램",
                                    color = colors.main,
                                    onClick = { onProgramClick(id) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(
                                            vertical = 15.dp,
                                            horizontal = 41.5.dp
                                        )
                                )

                                ExpoButton(
                                    text = "조회하기",
                                    color = colors.main,
                                    onClick = { onCheckClick(id) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(
                                            vertical = 15.dp,
                                            horizontal = 41.5.dp
                                        )
                                )
                            }

                            ExpoEnableDetailButton(
                                text = "문자 보내기",
                                onClick = { isOpenDialog(true) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = colors.main,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )

                            ExpoEnableButton(
                                text = "수정하기",
                                onClick = { onModifyClick(id) },
                                textColor = colors.main,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = colors.main,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.padding(bottom = 28.dp))
                    }
                }
            }

            getExpoInformationUiState is GetExpoInformationUiState.Loading || getTrainingProgramUiState is GetTrainingProgramListUiState.Loading || getStandardProgramUiState is GetStandardProgramListUiState.Loading -> Unit

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
            com.school_of_company.expo.view.component.MessageDialog(
                onCancelClick = { isOpenDialog(false) },
                onParticipantClick = {
                    isOpenDialog(false)
                    onMessageClick(Authority.ROLE_STANDARD.name)
                },
                onTraineeClick = {
                    isOpenDialog(false)
                    onMessageClick(Authority.ROLE_TRAINEE.name)
                }
            )
        }
    }
}

@ExpoPreviews
@Composable
private fun HomeDetailScreenPreview() {
    ExpoDetailScreen(
        scrollState = ScrollState(0),
        onBackClick = {},
        onMessageClick = {},
        onCheckClick = {},
        onModifyClick = {},
        onProgramClick = {},
        getExpoInformationUiState = GetExpoInformationUiState.Loading,
        getTrainingProgramUiState = GetTrainingProgramListUiState.Loading,
        getStandardProgramUiState = GetStandardProgramListUiState.Loading,
        id = ""
    )
}