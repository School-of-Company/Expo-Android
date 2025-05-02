package com.school_of_company.expo.view

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.common.regex.isValidDateSequence
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.bottomsheet.SettingBottomSheet
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.textfield.ExpoLocationIconTextField
import com.school_of_company.design_system.component.textfield.LimitedLengthTextField
import com.school_of_company.design_system.component.textfield.NoneLimitedLengthTextField
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.ImageIcon
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.enum.CurrentScreen
import com.school_of_company.expo.view.component.ExpoAddModifyTextField
import com.school_of_company.expo.view.component.ExpoSettingModifyBottomSheet
import com.school_of_company.expo.view.component.ExpoStandardAddModifyTextField
import com.school_of_company.expo.view.component.ExpoStandardSettingModifyBottomSheet
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState
import com.school_of_company.model.param.expo.ExpoModifyRequestParam
import com.school_of_company.model.param.expo.StandardProIdRequestParam
import com.school_of_company.model.param.expo.TrainingProIdRequestParam
import com.school_of_company.ui.keyBoardOption.numberKeyboardOptions
import com.school_of_company.ui.toast.makeToast
import com.school_of_company.ui.util.filterNonDigits
import com.school_of_company.ui.visualTransformation.DateTimeVisualTransformation

@Composable
internal fun ExpoModifyRoute(
    onBackClick: () -> Unit,
    id: String,
    navigateToExpoAddressSearch: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: ExpoViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val modifyExpoInformationUiState by viewModel.modifyExpoInformationUiState.collectAsStateWithLifecycle()
    val imageUpLoadUiState by viewModel.imageUpLoadUiState.collectAsStateWithLifecycle()

    val modifyTitleState by viewModel.modify_title.collectAsStateWithLifecycle()
    val startedDateState by viewModel.started_date.collectAsStateWithLifecycle()
    val endedDateState by viewModel.ended_date.collectAsStateWithLifecycle()
    val introduceTitleState by viewModel.introduce_title.collectAsStateWithLifecycle()
    val addressState by viewModel.address.collectAsStateWithLifecycle()
    val locationState by viewModel.location.collectAsStateWithLifecycle()
    val coverImageState by viewModel.cover_image.collectAsStateWithLifecycle()
    val trainingProgramTextState by viewModel.trainingProgramModifyTextState.collectAsStateWithLifecycle()
    val standardProgramTextState by viewModel.standardProgramModifyTextState.collectAsStateWithLifecycle()

    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var previousImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream, null, options)
                    previousImageUri = selectedImageUri
                    selectedImageUri = uri
                    viewModel.onCoverImageChange(uri.toString())
                }
            }
        }

    LaunchedEffect(imageUpLoadUiState) {
        when (imageUpLoadUiState) {
            is ImageUpLoadUiState.Error -> {
                selectedImageUri = previousImageUri
                previousImageUri = null
                onErrorToast(null, R.string.expo_image_fail)
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setCurrentScreen(CurrentScreen.MODIFY)
    }

    LaunchedEffect(id) {
        viewModel.getExpoInformation(id)
        viewModel.getStandardProgramList(id)
        viewModel.getTrainingProgramList(id)
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetExpoInformation()
        }
    }

    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri != null) {
            viewModel.imageUpLoad(context, selectedImageUri!!)
        }
    }

    LaunchedEffect(modifyExpoInformationUiState) {
        when (modifyExpoInformationUiState) {
            is ModifyExpoInformationUiState.Loading -> Unit
            is ModifyExpoInformationUiState.Success -> {
                onBackClick()
                makeToast(context, "박람회 수정을 완료하였습니다.")
            }

            is ModifyExpoInformationUiState.Error -> {
                onErrorToast(null, R.string.expo_modify_fail)
            }
        }
    }

    ExpoModifyScreen(
        imageUri = selectedImageUri?.toString() ?: coverImageState,
        modifyCallBack = {
            viewModel.modifyExpoInformation(id)
        },
        onBackClick = onBackClick,
        onImageClick = { galleryLauncher.launch("image/*") },
        startedDateState = startedDateState,
        endedDateState = endedDateState,
        modifyTitleState = modifyTitleState,
        addressState = addressState,
        locationState = locationState,
        introduceTitleState = introduceTitleState,
        trainingProgramTextState = trainingProgramTextState,
        standardProgramTextState = standardProgramTextState,
        navigateToExpoAddressSearch = navigateToExpoAddressSearch,
        onAddStandardProgram = viewModel::addStandardProgramModifyText,
        onAddTrainingProgram = viewModel::addTrainingProgramModifyText,
        onStartedDateChange = viewModel::onStartedDateChange,
        onEndedDateChange = viewModel::onEndedDateChange,
        onModifyTitleChange = viewModel::onModifyTitleChange,
        onLocationChange = viewModel::onLocationChange,
        onIntroduceTitleChange = viewModel::onIntroduceTitleChange,
        onRemoveTrainingProgram = viewModel::removeTrainingProgramModifyText,
        onRemoveStandardProgram = viewModel::removeStandardProgramModifyText,
        onTrainingProgramChange = viewModel::updateTrainingProgramModifyText,
        onStandardProgramChange = viewModel::updateStandardProgramModifyText,
        updateExistingTrainingProgram = viewModel::updateExistingTrainingProgramModify,
        updateExistingStandardProgram = viewModel::updateExistingStandardProgramModify
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpoModifyScreen(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    imageUri: String?,
    modifyCallBack: () -> Unit,
    onBackClick: () -> Unit,
    onImageClick: () -> Unit,
    startedDateState: String,
    endedDateState: String,
    modifyTitleState: String,
    addressState: String,
    locationState: String,
    introduceTitleState: String,
    trainingProgramTextState: List<TrainingProIdRequestParam>,
    standardProgramTextState: List<StandardProIdRequestParam>,
    navigateToExpoAddressSearch: () -> Unit,
    onAddStandardProgram: () -> Unit,
    onAddTrainingProgram: () -> Unit,
    onStartedDateChange: (String) -> Unit,
    onEndedDateChange: (String) -> Unit,
    onModifyTitleChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onIntroduceTitleChange: (String) -> Unit,
    onRemoveTrainingProgram: (Int) -> Unit,
    onRemoveStandardProgram: (Int) -> Unit,
    onTrainingProgramChange: (Int, TrainingProIdRequestParam) -> Unit,
    onStandardProgramChange: (Int, StandardProIdRequestParam) -> Unit,
    updateExistingTrainingProgram: (Int, TrainingProIdRequestParam) -> Unit,
    updateExistingStandardProgram: (Int, StandardProIdRequestParam) -> Unit
) {

    val (openTrainingSettingBottomSheet, isOpenTrainingSettingBottomSheet) = rememberSaveable {
        mutableStateOf(
            false
        )
    }

    val (openStandardSettingBottomSheet, isOpenStandardSettingBottomSheet) = rememberSaveable {
        mutableStateOf(
            false
        )
    }

    var selectedTrainingIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var selectedStandardIndex by rememberSaveable { mutableStateOf<Int?>(null) }

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

            ExpoTopBar(
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onBackClick() }
                    )
                },
                betweenText = "박람회 수정하기"
            )

            Spacer(modifier = Modifier.height(33.dp))

            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = colors.black)) {
                            append("사진")
                        }
                        withStyle(style = SpanStyle(color = colors.main)) {
                            append(" *")
                        }
                    },
                    style = typography.bodyBold2
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

                Spacer(modifier = Modifier.height(3.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.expoClickable { onImageClick() }
                ) {
                    ImageIcon(
                        tint = colors.gray300,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "이미지 수정하기(클릭)",
                        style = typography.captionRegular2,
                        color = colors.gray300,
                        textDecoration = TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.padding(top = 28.dp))

                LimitedLengthTextField(
                    labelComposable = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = colors.black)) {
                                    append("제목")
                                }
                                withStyle(style = SpanStyle(color = colors.main)) {
                                    append(" *")
                                }
                            },
                            style = typography.bodyBold2
                        )
                    },
                    value = modifyTitleState,
                    placeholder = "제목을 입력해주세요.",
                    isError = false,
                    updateTextValue = onModifyTitleChange,
                    lengthLimit = 30,
                    overflowErrorMessage = "최대 글자수를 초과했습니다."
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)) {
                    LimitedLengthTextField(
                        labelComposable = {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(color = colors.black)) {
                                        append("행사 기간")
                                    }
                                    withStyle(style = SpanStyle(color = colors.main)) {
                                        append(" *")
                                    }
                                },
                                style = typography.bodyBold2
                            )
                        },
                        value = startedDateState,
                        lengthLimit = 8,
                        placeholder = "시작일",
                        isError = false,
                        showLengthCounter = false,
                        keyboardOptions = numberKeyboardOptions(),
                        visualTransformation = DateTimeVisualTransformation(),
                        updateTextValue = { newText -> onStartedDateChange(newText.filterNonDigits()) },
                        modifier = Modifier.weight(1f)
                    )

                    LimitedLengthTextField(
                        labelComposable = {
                            Text(
                                text = "행사기간",
                                color = colors.white,
                                style = typography.bodyBold2
                            )
                        },
                        value = endedDateState,
                        lengthLimit = 8,
                        placeholder = "마감일",
                        isError = false,
                        showLengthCounter = false,
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
                    labelComposable = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = colors.black)) {
                                    append("소개글")
                                }
                                withStyle(style = SpanStyle(color = colors.main)) {
                                    append(" *")
                                }
                            },
                            style = typography.bodyBold2
                        )
                    }, value = introduceTitleState,
                    placeholder = "소개글을 작성해주세요.",
                    isError = false,
                    updateTextValue = onIntroduceTitleChange,
                    lengthLimit = 1000,
                    overflowErrorMessage = "최대 글자수를 초과했습니다."
                )

                Spacer(modifier = Modifier.padding(top = 28.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {
                    Text(
                        text = "참가자 프로그램",
                        style = typography.bodyBold2,
                        color = colors.black,
                    )

                    ExpoStandardAddModifyTextField(
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
                        text = "연수자 프로그램",
                        style = typography.bodyBold2,
                        color = colors.black,
                    )

                    ExpoAddModifyTextField(
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
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = colors.black)) {
                                    append("장소")
                                }
                                withStyle(style = SpanStyle(color = colors.main)) {
                                    append(" *")
                                }
                            },
                            style = typography.bodyBold2
                        )

                        ExpoLocationIconTextField(
                            value = addressState,
                            onValueChange = { _ -> },
                            placeholder = "장소를 선택해주세요.",
                            isDisabled = true,
                            onButtonClicked = navigateToExpoAddressSearch,
                        )

                        NoneLimitedLengthTextField(
                            value = locationState,
                            updateTextValue = onLocationChange,
                            placeholder = "상세주소를 입력해주세요."
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Spacer(modifier = Modifier.height(28.dp))

                    ExpoStateButton(
                        text = "수정완료",
                        state = if (
                            modifyTitleState.isNotEmpty() &&
                            startedDateState.isNotEmpty() &&
                            endedDateState.isNotEmpty() &&
                            introduceTitleState.isNotEmpty() &&
                            addressState.isNotEmpty() &&
                            locationState.isNotEmpty() &&
                            startedDateState.isValidDateSequence(endedDateState)
                        ) {
                            ButtonState.Enable
                        } else {
                            ButtonState.Disable
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        modifyCallBack()
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }

    if (openTrainingSettingBottomSheet) {
        Dialog(onDismissRequest = { isOpenTrainingSettingBottomSheet(false) }) {

            val selectedTrainingItem = selectedTrainingIndex?.let { trainingProgramTextState[it] }

            SettingBottomSheet(
                onDismiss = { isOpenTrainingSettingBottomSheet(false) },
                selectedItem = selectedTrainingItem,
                onUpdateItem = { updateItem ->
                    selectedTrainingIndex?.let { index ->
                        updateExistingTrainingProgram(index, updateItem)
                    }
                }
            ) { item, updateItem ->
                ExpoSettingModifyBottomSheet(
                    onCancelClick = { isOpenTrainingSettingBottomSheet(false) },
                    onButtonClick = { isOpenTrainingSettingBottomSheet(false) },
                    trainingSettingItem = item,
                    onTrainingSettingChange = updateItem
                )
            }
        }
    }

    if (openStandardSettingBottomSheet) {
        Dialog(onDismissRequest = { isOpenStandardSettingBottomSheet(false) }) {

            val selectedStandardItem = selectedStandardIndex?.let { standardProgramTextState[it] }

            SettingBottomSheet(
                onDismiss = { isOpenStandardSettingBottomSheet(false) },
                selectedItem = selectedStandardItem,
                onUpdateItem = { updateItem ->
                    selectedStandardIndex?.let { index ->
                        updateExistingStandardProgram(index, updateItem)
                    }
                }
            ) { item, updateItem ->
                ExpoStandardSettingModifyBottomSheet(
                    onCancelClick = { isOpenStandardSettingBottomSheet(false) },
                    onButtonClick = { isOpenStandardSettingBottomSheet(false) },
                    trainingSettingItem = item,
                    onTrainingSettingChange = updateItem
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailModifyScreenPreview() {
    ExpoModifyScreen(
        onBackClick = {},
        onImageClick = {},
        modifyTitleState = "",
        startedDateState = "",
        endedDateState = "",
        introduceTitleState = "",
        addressState = "",
        locationState = "",
        onModifyTitleChange = {},
        onLocationChange = {},
        onStartedDateChange = {},
        onEndedDateChange = {},
        onIntroduceTitleChange = {},
        scrollState = ScrollState(0),
        imageUri = "",
        modifyCallBack = {},
        navigateToExpoAddressSearch = {},
        trainingProgramTextState = emptyList(),
        onTrainingProgramChange = { _, _ -> },
        onAddTrainingProgram = {},
        onRemoveTrainingProgram = {},
        standardProgramTextState = emptyList(),
        onStandardProgramChange = { _, _ -> },
        onAddStandardProgram = {},
        onRemoveStandardProgram = {},
        updateExistingTrainingProgram = { _, _ -> },
        updateExistingStandardProgram = { _, _ -> }
    )
}