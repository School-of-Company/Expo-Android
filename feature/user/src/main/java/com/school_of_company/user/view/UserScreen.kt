package com.school_of_company.user.view

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.UserIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.user.view.component.SignUpRequestList
import com.school_of_company.user.view.component.temparory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

fun generateSignUpRequestSampleData(): ImmutableList<temparory> {
    return List(20) {
        temparory(
            name = "이명훈",
            id = "audgns3825",
            email = "audgns3825@gmail.com",
            phoneNumber = "01012345678"
        )
    }.toPersistentList()
}
@Composable
internal fun UserRoute() {
    UserScreen(
        sampleData = generateSignUpRequestSampleData()
    )
}

@Composable
private fun UserScreen(
    modifier: Modifier = Modifier,
    sampleData: ImmutableList<temparory>,
    scrollState: ScrollState = rememberScrollState()
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .paddingHorizontal(top = 68.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .background(
                            color = colors.main,
                            shape = RoundedCornerShape(size = 35.dp)
                        )
                ) {
                    UserIcon(
                        tint = colors.white,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(36.dp)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "이름",
                            style = typography.captionRegular1,
                            color = colors.gray500
                        )

                        Text(
                            text = "이명훈", // todo : Apply UiState
                            style = typography.captionBold2,
                            color = colors.black
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "아이디",
                            style = typography.captionRegular1,
                            color = colors.gray500
                        )

                        Text(
                            text = "audgns3825 ", // todo : Apply UiState
                            style = typography.captionBold2,
                            color = colors.black
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "이메일",
                            style = typography.captionRegular1,
                            color = colors.gray500
                        )

                        Text(
                            text = "audgns3825@gmail.com", // todo : Apply UiState
                            style = typography.captionBold2,
                            color = colors.black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = "회원가입 요청",
                style = typography.bodyBold2,
                color = colors.black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .paddingHorizontal(
                        top = 10.dp,
                        horizontal = 16.dp
                    )
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)) {
                    WarnIcon(
                        tint = colors.gray300,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "옆으로 넘겨서 확인해보세요.",
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
                        text = "회원가입 요청",
                        style = typography.captionRegular2,
                        color = colors.gray500
                    )

                    Text(
                        text = "${12}명", // todo : Apply UiState Data Size
                        style = typography.captionRegular2,
                        color = colors.main
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

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
                        text = "아이디",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(120.dp)
                    )

                    Text(
                        text = "이메일",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(100.dp)
                    )

                    Text(
                        text = "전화번호",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(120.dp)
                    )
                }
            }

            SignUpRequestList(
                item = sampleData,
                horizontalScrollState = scrollState
            )
        }
    }
}

@Preview
@Composable
private fun UserScreenPreview() {
    UserScreen(
        sampleData = generateSignUpRequestSampleData()
    )
}