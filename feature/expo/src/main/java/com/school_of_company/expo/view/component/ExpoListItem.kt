package com.school_of_company.expo.view.component

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.ImageIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import com.school_of_company.ui.util.formatServerDate

@Composable
fun ExpoListItem(
    modifier: Modifier = Modifier,
    data: ExpoListResponseEntity,
    navigateToExpoDetail: (Long) -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            modifier = modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colors.gray200,
                    shape = RoundedCornerShape(6.dp)
                )
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .paddingHorizontal(
                    horizontal = 12.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
                .expoClickable { navigateToExpoDetail(data.id) }
        ) {
            if (data.coverImage.isNullOrEmpty()) {
                Row (horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),) {
                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(110.dp)
                            .background(
                                color = colors.main,
                                shape = RoundedCornerShape(6.dp)
                            )
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            ImageIcon(tint = colors.white)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = R.string.not_image_description),
                                style = typography.captionRegular1,
                                color = colors.white
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start)) {

                            Text(
                                text = stringResource(id = R.string.register_temp),
                                style = typography.captionRegular2,
                                color = colors.gray600
                            )

                            Text(
                                text = stringResource(
                                    R.string.date_type,
                                    data.startedDay.formatServerDate(),
                                    data.finishedDay.formatServerDate()
                                ),
                                style = typography.captionRegular2,
                                color = colors.gray600,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Text(
                            text = data.title,
                            style = typography.captionBold1,
                            color = colors.black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = data.description,
                            style = typography.captionRegular2,
                            color = colors.gray300,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            } else {
                Row (horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),) {
                    Image(
                        painter = rememberAsyncImagePainter(model = data.coverImage),
                        contentDescription = stringResource(id = R.string.HomeScreen_Image_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(110.dp)
                            .height(110.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start)) {

                            Text(
                                text = stringResource(id = R.string.register_temp),
                                style = typography.captionRegular2,
                                color = colors.main400
                            )

                            Text(
                                text = stringResource(
                                    R.string.date_type,
                                    data.startedDay.formatServerDate(),
                                    data.finishedDay.formatServerDate()
                                ),
                                style = typography.captionRegular2,
                                color = colors.main400,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Text(
                            text = data.title,
                            style = typography.captionBold1,
                            color = colors.black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = data.description,
                            style = typography.captionRegular2,
                            color = colors.gray300,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeListItemPreview() {
    ExpoListItem(
        data = ExpoListResponseEntity(
            id = 0,
            coverImage = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
            startedDay = "09-01",
            finishedDay = "09-30",
            title = "2024 AI 광주 미래교육 2024 AI 광주 미래교육",
            description = "2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육 2024 AI 광주 미래교육2024 AI 광주 미래교육"
        ),
        navigateToExpoDetail = {}
    )
}