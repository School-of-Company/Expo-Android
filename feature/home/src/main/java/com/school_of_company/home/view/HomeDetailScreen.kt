package com.school_of_company.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoButton
import com.school_of_company.design_system.component.button.ExpoEnableButton
import com.school_of_company.design_system.component.button.ExpoEnableDetailButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingVertical
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.home.view.component.HomeKakaoMap
import com.school_of_company.home.view.component.HomeTempData
import com.school_of_company.home.view.component.MessageDialog
import com.school_of_company.home.view.component.QrCode
import com.school_of_company.home.view.component.QrDialog
import com.school_of_company.ui.preview.ExpoPreviews
import com.school_of_company.ui.util.formatServerDate

@Composable
internal fun HomeDetailRoute(
    onBackClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCheckClick: () -> Unit,
    onQrGenerateClick: () -> Unit,
    onModifyClick: () -> Unit,
    onProgramClick: () -> Unit
) {
    HomeDetailScreen(
        data = HomeTempData(
            image = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
            started_at = "09-01",
            ended_at = "09-30",
            title = "2024 AI 광주 미래교육",
            content = "2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육"
        ),
        qrData = QrCode(content = "121231342352"),
        onBackClick = onBackClick,
        onMessageClick = onMessageClick,
        onCheckClick = onCheckClick,
        onQrGenerateClick = onQrGenerateClick,
        onModifyClick = onModifyClick,
        onProgramClick = onProgramClick
    )
}

@Composable
internal fun HomeDetailScreen(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    data: HomeTempData,
    qrData: QrCode,
    onBackClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCheckClick: () -> Unit,
    onQrGenerateClick: () -> Unit,
    onModifyClick: () -> Unit,
    onProgramClick: () -> Unit
) {
    val (openDialog, isOpenDialog) = rememberSaveable { mutableStateOf(false) }
    val (openQrDialog, isOpenQrDialog) = rememberSaveable { mutableStateOf(false) }

    ExpoAndroidTheme { colors, typography ->
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
                betweenText = data.title,
                modifier = Modifier.padding(top = 68.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(modifier = Modifier.verticalScroll(scrollState)) {
                Image(
                    painter = rememberAsyncImagePainter(model = data.image),
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

                Spacer(modifier = Modifier.height(18.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {
                    Text(
                        text = "소개 글",
                        style = typography.bodyRegular2,
                        color = colors.gray600,
                        fontWeight = FontWeight(600),
                    )

                    Text(
                        text = "안녕하세요!\n" +
                                "2024 AI광주미래교육박람회 사전 등록 페이지에 오신 것을 환영합니다.\n" +
                                "아래 양식을 작성해주시면 등록이 완료됩니다.",
                        style = typography.bodyRegular2,
                        color = colors.gray400
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start)) {

                        Text(
                            text = stringResource(id = R.string.register_temp),
                            style = typography.captionRegular2,
                            color = colors.gray600
                        )

                        Text(
                            text = stringResource(
                                R.string.date_type,
                                data.started_at.formatServerDate(),
                                data.ended_at.formatServerDate()
                            ),
                            style = typography.captionRegular2,
                            color = colors.gray600,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)) {

                    Text(
                        text = "연수",
                        style = typography.bodyRegular2,
                        color = colors.gray600,
                        fontWeight = FontWeight(600),
                    )

                    Text(
                        text = "안녕하세요!\n" +
                                "2024 AI광주미래교육박람회 사전 등록 페이지에 오신 것을 환영합니다.\n" +
                                "아래 양식을 작성해주시면 등록이 완료됩니다.",
                        style = typography.bodyRegular2,
                        color = colors.gray400
                    )
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
                        text = "장소 : 광주소프트웨어마이스터고등학교",
                        style = typography.bodyRegular2,
                        color = colors.gray400,
                    )

                    Text(
                        text = "주소 : 광주광역시 광산구 상무대로 312",
                        style = typography.bodyRegular2,
                        color = colors.gray400,
                    )

                    HomeKakaoMap(
                        locationY = 126.80042860412009,
                        locationX = 35.14308063423194,
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
                            text = "문자 보내기",
                            color = colors.main,
                            onClick = { isOpenDialog(true) },
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    vertical = 15.dp,
                                    horizontal = 41.5.dp
                                )
                        )

                        ExpoButton(
                            text = "QR 조회하기",
                            color = colors.main,
                            onClick = {
                                onQrGenerateClick()
                                isOpenQrDialog(true)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    vertical = 15.dp,
                                    horizontal = 37.dp
                                )
                        )
                    }

                    ExpoEnableDetailButton(
                        text = "프로그램",
                        onClick = onProgramClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = colors.main,
                                shape = RoundedCornerShape(6.dp)
                            )
                    )

                    ExpoEnableDetailButton(
                        text = "조회하기",
                        onClick = onCheckClick,
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
                        onClick = onModifyClick,
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
            if (openDialog) {
                Dialog(onDismissRequest = { isOpenDialog(false) }) {
                    MessageDialog(
                        onCancelClick = { isOpenDialog(false) },
                        onParticipantClick = {
                            isOpenDialog(false)
                            onMessageClick()
                            /*TODO -> SMS Logic*/
                        },
                        onTraineeClick = {
                            isOpenDialog(false)
                            onMessageClick()
                            /*TODO -> SMS Logic*/
                        }
                    )
                }
            }

            if (openQrDialog) {
                Dialog(onDismissRequest = { isOpenQrDialog(false) }) {
                    QrDialog(
                        data = qrData,
                        onCancelClick = { isOpenQrDialog(false) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailScreenPreview() {
    HomeDetailScreen(
        data = HomeTempData(
            image = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
            started_at = "09-01",
            ended_at = "09-30",
            title = "2024 AI 광주 미래교육",
            content = "2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육"
        ),
        scrollState = ScrollState(0),
        onBackClick = {},
        onMessageClick = {},
        onCheckClick = {},
        onQrGenerateClick = {},
        onModifyClick = {},
        onProgramClick = {},
        qrData = QrCode(content = "121231342352")
    )
}