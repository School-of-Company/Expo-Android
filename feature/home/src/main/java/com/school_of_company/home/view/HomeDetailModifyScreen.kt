package com.school_of_company.home.view

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.textfield.LimitedLengthTextField
import com.school_of_company.design_system.component.textfield.NoneLimitedLengthTextField
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.ImageIcon
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.home.view.component.HomeDetailModifyAddTextField
import com.school_of_company.home.view.component.HomeDetailModifyIconTextField
import com.school_of_company.home.viewmodel.HomeViewModel
import com.school_of_company.ui.toast.makeToast

@Composable
internal fun HomeDetailModifyRoute(
    onBackClick: () -> Unit,
    onModifyClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val modifyTitleState by viewModel.modify_title.collectAsStateWithLifecycle()
    val startedDateState by viewModel.started_date.collectAsStateWithLifecycle()
    val endedDateState by viewModel.ended_date.collectAsStateWithLifecycle()
    val introduceTitleState by viewModel.introduce_title.collectAsStateWithLifecycle()
    val addressState by viewModel.address.collectAsStateWithLifecycle()
    val locationState by viewModel.location.collectAsStateWithLifecycle()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream, null, options)
                    if (options.outWidth == 328 && options.outHeight == 178) {
                        selectedImageUri = uri
                    } else {
                        makeToast(context, "이미지 크기는 328 × 178이어야 합니다.")
                    }
                }
            }
        }


    HomeDetailModifyScreen(
        onBackClick = onBackClick,
        onImageClick = { galleryLauncher.launch("image/*") },
        imageUri = selectedImageUri?.toString() ?: "",
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
        onLocationChange = viewModel::onLocationChange
    )
}

@Composable
internal fun HomeDetailModifyScreen(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    imageUri: String?,
    onBackClick: () -> Unit,
    onImageClick: () -> Unit,
    modifyTitleState: String,
    startedDateState: String,
    endedDateState: String,
    introduceTitleState: String,
    addressState: String,
    locationState: String,
    onModifyTitleChange: (String) -> Unit,
    onStartedDateChange: (String) -> Unit,
    onEndedDateChange: (String) -> Unit,
    onIntroduceTitleChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onLocationChange: (String) -> Unit
) {
    var trainingTextState by rememberSaveable { mutableStateOf(listOf("")) }

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
                        text = "이미지 328 × 178 사이즈로 등록해주세요.",
                        style = typography.captionRegular2,
                        color = colors.gray300
                    )
                }

                Spacer(modifier = Modifier.padding(top = 28.dp))

                LimitedLengthTextField(
                    label = "제목",
                    textState = modifyTitleState,
                    placeholder = "제목을 입력해주세요.",
                    isError = false,
                    updateTextValue = onModifyTitleChange,
                    lengthLimit = 30,
                    overflowErrorMessage = "최대 글자수를 초과했습니다."
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)) {
                    LimitedLengthTextField(
                        label = "모집기간",
                        textState = startedDateState,
                        placeholder = "시작일",
                        isError = false,
                        updateTextValue = onStartedDateChange,
                        modifier = Modifier.weight(1f)
                    )

                    LimitedLengthTextField(
                        textState = endedDateState,
                        placeholder = "마감일",
                        isError = false,
                        updateTextValue = onEndedDateChange,
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
                        text = "시작일과 마감일 입력시 ‘ yyyy.mm.dd ‘ 형식으로 입력해주세요.",
                        style = typography.captionRegular2,
                        color = colors.gray300
                    )
                }

                Spacer(modifier = Modifier.padding(top = 28.dp))

                LimitedLengthTextField(
                    label = "소개글",
                    textState = introduceTitleState,
                    placeholder = "소개글을 작성해주세요.",
                    isError = false,
                    updateTextValue = onIntroduceTitleChange,
                    lengthLimit = 1000,
                    overflowErrorMessage = "최대 글자수를 초과했습니다."
                )

                Spacer(modifier = Modifier.padding(top = 28.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {
                    Text(
                        text = "연수 종류",
                        style = typography.bodyBold2,
                        color = colors.black,
                    )

                    HomeDetailModifyAddTextField(
                        trainingTextFieldList = trainingTextState,
                        onValueChange = { index, newState ->
                            trainingTextState = trainingTextState.toMutableList().apply {
                                set(index, newState)
                            }
                        },
                        onAddTextField = {
                            trainingTextState = trainingTextState.toMutableList().apply {
                                add("")
                            }
                        },
                        onRemoveTextField = {
                            trainingTextState = trainingTextState.toMutableList().apply {
                                removeAt(it)
                            }
                        },
                        placeHolder = "연수 종류를 입력해주세요."
                    )

                    Spacer(modifier = Modifier.padding(top = 28.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)) {

                        HomeDetailModifyIconTextField(
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
                        text = "수정완료",
                        state = if (imageUri.isNullOrEmpty() && modifyTitleState.isEmpty() && startedDateState.isEmpty() && endedDateState.isEmpty() && introduceTitleState.isEmpty() && addressState.isEmpty() && locationState.isEmpty() && trainingTextState.isEmpty()) ButtonState.Enable else ButtonState.Disable,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        /* todo : Expo Modify CallBack */
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailModifyScreenPreview() {
    HomeDetailModifyScreen(
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
        onAddressChange = {},
        onStartedDateChange = {},
        onEndedDateChange = {},
        onIntroduceTitleChange = {},
        scrollState = ScrollState(0),
        imageUri = ""
    )
}