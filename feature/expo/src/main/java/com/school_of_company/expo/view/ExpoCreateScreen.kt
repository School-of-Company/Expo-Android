package com.school_of_company.expo.view

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.common.regex.isValidDateSequence
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.textfield.ExpoLocationIconTextField
import com.school_of_company.design_system.component.textfield.LimitedLengthTextField
import com.school_of_company.design_system.component.textfield.NoneLimitedLengthTextField
import com.school_of_company.design_system.icon.ImageIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.ExpoAddTextField
import com.school_of_company.expo.view.component.ExpoSettingBottomSheet
import com.school_of_company.expo.view.component.ExpoStandardAddTextField
import com.school_of_company.expo.view.component.ExpoStandardSettingBottomSheet
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterStandardProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterTrainingProgramListUiState
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.model.model.standard.StandardRequestModel
import com.school_of_company.model.model.training.TrainingDtoModel
import com.school_of_company.ui.keyBoardOption.numberKeyboardOptions
import com.school_of_company.ui.toast.makeToast
import com.school_of_company.ui.util.filterNonDigits
import com.school_of_company.ui.visualTransformation.DateTimeVisualTransformation

@Composable
internal fun ExpoCreateRoute(
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: ExpoViewModel = hiltViewModel()
) {
    val registerExpoInformationUiState by viewModel.registerExpoInformationUiState.collectAsStateWithLifecycle()
    val imageUpLoadUiState by viewModel.imageUpLoadUiState.collectAsStateWithLifecycle()
    val registerTrainingProgramListUiState by viewModel.registerTrainingProgramListUiState.collectAsStateWithLifecycle()
    val registerStandardProgramListUiState by viewModel.registerStandardProgramListUiState.collectAsStateWithLifecycle()

    val modifyTitleState by viewModel.modify_title.collectAsStateWithLifecycle()
    val startedDateState by viewModel.started_date.collectAsStateWithLifecycle()
    val endedDateState by viewModel.ended_date.collectAsStateWithLifecycle()
    val introduceTitleState by viewModel.introduce_title.collectAsStateWithLifecycle()
    val addressState by viewModel.address.collectAsStateWithLifecycle()
    val locationState by viewModel.location.collectAsStateWithLifecycle()
    val coverImageState by viewModel.cover_image.collectAsStateWithLifecycle()
    val trainingProgramTextState by viewModel.trainingProgramTextState.collectAsStateWithLifecycle()
    val standardProgramTextState by viewModel.standardProgramTextState.collectAsStateWithLifecycle()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream, null, options)
                    selectedImageUri = uri
                }
            }
        }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.initRegisterExpo()
        }
    }

    LaunchedEffect(imageUpLoadUiState) {
        when (imageUpLoadUiState) {
            is ImageUpLoadUiState.Loading -> Unit
            is ImageUpLoadUiState.Success -> {
                viewModel.registerExpoInformation(
                    body = ExpoRequestAndResponseModel(
                        title = viewModel.modify_title.value,
                        startedDay = viewModel.started_date.value,
                        finishedDay = viewModel.ended_date.value,
                        description = viewModel.introduce_title.value,
                        location = viewModel.location.value,
                        coverImage = (imageUpLoadUiState as ImageUpLoadUiState.Success).data.imageURL,
                        x = 37.511734f,
                        y = 127.05905f
                    )
                )
            }

            is ImageUpLoadUiState.Error -> {
                onErrorToast(null, R.string.expo_image_fail)
            }
        }
    }

    LaunchedEffect(registerExpoInformationUiState) {
        when (registerExpoInformationUiState) {
            is RegisterExpoInformationUiState.Loading -> Unit
            is RegisterExpoInformationUiState.Success -> {
                viewModel.registerTrainingProgramList(
                    expoId = (registerExpoInformationUiState as RegisterExpoInformationUiState.Success).data.expoId,
                    body = trainingProgramTextState
                )

                viewModel.registerStandardProgramList(
                    expoId = (registerExpoInformationUiState as RegisterExpoInformationUiState.Success).data.expoId,
                    body = standardProgramTextState
                )
            }

            is RegisterExpoInformationUiState.Error -> {
                onErrorToast(null, R.string.expo_register_fail)
            }
        }
    }

    LaunchedEffect(registerTrainingProgramListUiState, registerStandardProgramListUiState) {
        if (registerTrainingProgramListUiState is RegisterTrainingProgramListUiState.Loading && registerStandardProgramListUiState is RegisterStandardProgramListUiState.Loading) { Unit }
        if (registerTrainingProgramListUiState is RegisterTrainingProgramListUiState.Success && registerStandardProgramListUiState is RegisterStandardProgramListUiState.Success) {
            viewModel.resetExpoInformation()
            selectedImageUri = null
            makeToast(context, "박람회 등록을 완료하였습니다.")
            viewModel.initRegisterExpo()
        } else if (registerTrainingProgramListUiState is RegisterTrainingProgramListUiState.Error || registerStandardProgramListUiState is RegisterStandardProgramListUiState.Error) {
            onErrorToast(null, R.string.expo_register_fail)
        }
    }


    ExpoCreateScreen(
        onImageClick = { galleryLauncher.launch("image/*") },
        imageUri = selectedImageUri?.toString() ?: coverImageState,
        modifyTitleState = modifyTitleState,
        startedDateState = startedDateState,
        endedDateState = endedDateState,
        introduceTitleState = introduceTitleState,
        addressState = addressState,
        locationState = locationState,
        onModifyTitleChange = viewModel::onModifyTitleChange,
        onStartedDateChange = viewModel::onStartedDateChange,
        onEndedDateChange = viewModel::onEndedDateChange,
        onIntroduceTitleChange = viewModel::onIntroduceTitleChange,
        onAddressChange = viewModel::onAddressChange,
        onLocationChange = viewModel::onLocationChange,
        onExpoCreateCallBack = {
            if (selectedImageUri != null) {
                viewModel.imageUpLoad(context, selectedImageUri!!)
            } else {
                onErrorToast(null, R.string.expo_image_fail)
            }
        },
        trainingProgramTextState = trainingProgramTextState,
        onTrainingProgramChange = viewModel::updateTrainingProgramText,
        onAddTrainingProgram = viewModel::addTrainingProgramText,
        onRemoveTrainingProgram = viewModel::removeTrainingProgramText,
        standardProgramTextState = standardProgramTextState,
        onStandardProgramChange = viewModel::updateStandardProgramText,
        onAddStandardProgram = viewModel::addStandardProgramText,
        onRemoveStandardProgram = viewModel::removeStandardProgramText,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpoCreateScreen(
    modifier: Modifier = Modifier,
    startedDateState: String,
    endedDateState: String,
    modifyTitleState: String,
    introduceTitleState: String,
    addressState: String,
    locationState: String,
    imageUri: String?,
    trainingProgramTextState: List<TrainingDtoModel>,
    standardProgramTextState: List<StandardRequestModel>,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    onImageClick: () -> Unit,
    onExpoCreateCallBack: () -> Unit,
    onAddTrainingProgram: () -> Unit,
    onAddStandardProgram: () -> Unit,
    onStartedDateChange: (String) -> Unit,
    onEndedDateChange: (String) -> Unit,
    onModifyTitleChange: (String) -> Unit,
    onIntroduceTitleChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onRemoveTrainingProgram: (Int) -> Unit,
    onRemoveStandardProgram: (Int) -> Unit,
    onTrainingProgramChange: (Int, TrainingDtoModel) -> Unit,
    onStandardProgramChange: (Int, StandardRequestModel) -> Unit,
) {
    val (openTrainingSettingBottomSheet, isOpenTrainingSettingBottomSheet) = rememberSaveable { mutableStateOf(false) }
    val (openStandardSettingBottomSheet, isOpenStandardSettingBottomSheet) = rememberSaveable { mutableStateOf(false) }

    var selectedTrainingIndex by remember { mutableStateOf<Int?>(null) }
    var selectedStandardIndex by remember { mutableStateOf<Int?>(null) }

    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .paddingHorizontal(
                    horizontal = 16.dp,
                    top = 68.dp
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                }
        ) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                Text(
                    text = "사진",
                    style = typography.bodyBold2,
                    color = colors.black,
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (imageUri.isNullOrEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(178.dp)
                            .drawBehind {
                                drawRoundRect(
                                    color = colors.main300,
                                    style = Stroke(
                                        width = 3.dp.toPx(),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(20f, 20f),
                                            phase = 0f
                                        )
                                    ),
                                    topLeft = Offset.Zero,
                                    size = Size(size.width, size.height),
                                    cornerRadius = CornerRadius(6.dp.toPx())
                                )
                            }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .expoClickable { onImageClick() }
                        ) {
                            ImageIcon(
                                tint = colors.gray300,
                                modifier = Modifier.size(24.dp)
                            )

                            Text(
                                text = "사진 가져오기",
                                style = typography.captionBold1,
                                color = colors.gray300
                            )
                        }
                    }
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = stringResource(id = R.string.HomeScreen_Image_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(178.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .drawBehind {
                                drawRoundRect(
                                    color = colors.main300,
                                    style = Stroke(
                                        width = 3.dp.toPx(),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(20f, 20f),
                                            phase = 0f
                                        )
                                    ),
                                    topLeft = Offset.Zero,
                                    size = Size(size.width, size.height),
                                    cornerRadius = CornerRadius(6.dp.toPx())
                                )
                            }
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WarnIcon(
                        tint = colors.gray300,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "이미지 328 × 178 사이즈를 권장합니다.",
                        style = typography.captionRegular2,
                        color = colors.gray300
                    )
                }

                Spacer(modifier = Modifier.padding(top = 28.dp))

                LimitedLengthTextField(
                    label = "제목",
                    value = modifyTitleState,
                    placeholder = "제목을 입력해주세요.",
                    isError = false,
                    updateTextValue = onModifyTitleChange,
                    lengthLimit = 30,
                    overflowErrorMessage = "최대 글자수를 초과했습니다."
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)) {
                    LimitedLengthTextField(
                        label = "모집기간",
                        value = startedDateState,
                        lengthLimit = 8,
                        showLengthCounter = false,
                        placeholder = "시작일",
                        isError = false,
                        keyboardOptions = numberKeyboardOptions(),
                        visualTransformation = DateTimeVisualTransformation(),
                        updateTextValue = { newText -> onStartedDateChange(newText.filterNonDigits()) },
                        modifier = Modifier.weight(1f)
                    )

                    LimitedLengthTextField(
                        value = endedDateState,
                        lengthLimit = 8,
                        showLengthCounter = false,
                        placeholder = "마감일",
                        isError = false,
                        keyboardOptions = numberKeyboardOptions(),
                        visualTransformation = DateTimeVisualTransformation(),
                        updateTextValue = { newText -> onEndedDateChange(newText.filterNonDigits()) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WarnIcon(
                        tint = colors.gray300,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "시작일과 마감일 입력시 ‘ yyyy-mm-dd ‘ 형식으로 입력해주세요.",
                        style = typography.captionRegular2,
                        color = colors.gray300
                    )
                }

                Spacer(modifier = Modifier.padding(top = 28.dp))

                LimitedLengthTextField(
                    label = "소개글",
                    value = introduceTitleState,
                    placeholder = "소개글을 작성해주세요.",
                    isError = false,
                    updateTextValue = onIntroduceTitleChange,
                    lengthLimit = 1000,
                    overflowErrorMessage = "최대 글자수를 초과했습니다."
                )

                Spacer(modifier = Modifier.padding(top = 28.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {
                    Text(
                        text = "참가자 연수 종류",
                        style = typography.bodyBold2,
                        color = colors.black,
                    )

                    ExpoStandardAddTextField(
                        trainingTextFieldList = standardProgramTextState,
                        onValueChange = { index, newState ->
                            onStandardProgramChange(index, newState)
                        },
                        onAddTextField = { onAddStandardProgram() },
                        onRemoveTextField = { index ->
                            onRemoveStandardProgram(index)
                        },
                        onTrainingSetting = { index ->
                            selectedStandardIndex = index
                            isOpenStandardSettingBottomSheet(true)
                        },
                        placeHolder = "연수 종류를 입력해주세요."
                    )

                    Spacer(modifier = Modifier.padding(top = 28.dp))

                    Text(
                        text = "연수자 연수 종류",
                        style = typography.bodyBold2,
                        color = colors.black,
                    )

                    ExpoAddTextField(
                        trainingTextFieldList = trainingProgramTextState,
                        onValueChange = { index, newState ->
                            onTrainingProgramChange(index, newState)
                        },
                        onAddTextField = { onAddTrainingProgram() },
                        onRemoveTextField = { index ->
                            onRemoveTrainingProgram(index)
                        },
                        onTrainingSetting = { index ->
                            selectedTrainingIndex = index
                            isOpenTrainingSettingBottomSheet(true)
                        },
                        placeHolder = "연수 종류를 입력해주세요."
                    )

                    Spacer(modifier = Modifier.padding(top = 28.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {

                        ExpoLocationIconTextField(
                            placeholder = "장소를 입력해주세요.",
                            isDisabled = false,
                            onValueChange = onLocationChange,
                            onButtonClicked = { /* todo : Location Web Hook */ },
                            value = locationState,
                        )

                        NoneLimitedLengthTextField(
                            textState = addressState,
                            placeholder = "상세주소를 입력해주세요.",
                            updateTextValue = onAddressChange
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Spacer(modifier = Modifier.height(28.dp))

                    ExpoStateButton(
                        text = "생성하기",
                        state = if (
                            modifyTitleState.isNotEmpty() &&
                            startedDateState.isNotEmpty() &&
                            endedDateState.isNotEmpty() &&
                            introduceTitleState.isNotEmpty() &&
                            addressState.isNotEmpty() &&
                            locationState.isNotEmpty() &&
                            trainingProgramTextState.isNotEmpty() &&
                            standardProgramTextState.isNotEmpty() &&
                            startedDateState.isValidDateSequence(endedDateState)
                        ) {
                            ButtonState.Enable
                        } else {
                            ButtonState.Disable
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        onExpoCreateCallBack()
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }

    if (openTrainingSettingBottomSheet) {
        Dialog(onDismissRequest = { isOpenTrainingSettingBottomSheet(false) }) {

            val selectedTrainingItem = selectedTrainingIndex?.let { trainingProgramTextState[it] }

            if (selectedTrainingItem != null) {
                ExpoSettingBottomSheet(
                    onCancelClick = { isOpenTrainingSettingBottomSheet(false) },
                    onButtonClick = { isOpenTrainingSettingBottomSheet(false) },
                    trainingSettingItem = selectedTrainingItem,
                    onTrainingSettingChange = { updateItem ->
                        selectedTrainingIndex?.let { index ->
                            onTrainingProgramChange(index, updateItem)
                        }
                    },
                )
            }
        }
    }

    if (openStandardSettingBottomSheet) {
        Dialog(onDismissRequest = { isOpenStandardSettingBottomSheet(false) }) {

            val selectedStandardItem = selectedStandardIndex?.let { standardProgramTextState[it] }

            if (selectedStandardItem != null) {
                ExpoStandardSettingBottomSheet(
                    onCancelClick = { isOpenStandardSettingBottomSheet(false) },
                    onButtonClick = { isOpenStandardSettingBottomSheet(false) },
                    trainingSettingItem = selectedStandardItem,
                    onTrainingSettingChange = { updateItem ->
                        selectedStandardIndex?.let { index ->
                            onStandardProgramChange(index, updateItem)
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpoCreateScreenPreview() {
    ExpoCreateScreen(
        imageUri = null,
        onImageClick = {},
        modifyTitleState = "",
        startedDateState = "",
        endedDateState = "",
        introduceTitleState = "",
        addressState = "",
        locationState = "",
        onModifyTitleChange = {},
        onLocationChange = {},
        onAddressChange = {},
        onStartedDateChange = {},
        onEndedDateChange = {},
        onIntroduceTitleChange = {},
        onExpoCreateCallBack = {},
        trainingProgramTextState = emptyList(),
        onTrainingProgramChange = { _, _ -> },
        onAddTrainingProgram = {},
        onRemoveTrainingProgram = {},
        standardProgramTextState = emptyList(),
        onStandardProgramChange = { _, _ -> },
        onAddStandardProgram = {},
        onRemoveStandardProgram = {},
    )
}