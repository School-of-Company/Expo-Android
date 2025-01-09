package com.school_of_company.user.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity

@Composable
internal fun SignUpRequestListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: AdminRequestAllowListResponseEntity,
    horizontalScrollState: ScrollState,
) {
    ExpoAndroidTheme { colors, typography ->
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier
                .fillMaxWidth()
                .background(color = colors.white)
                .padding(vertical = 8.dp)
                .horizontalScroll(horizontalScrollState)
        ) {
            Text(
                text = index.toString(),
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(20.dp)
            )

            Text(
                text = data.name,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(80.dp)
            )

            Text(
                text = data.nickname,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(120.dp)
            )

            Text(
                text = data.email,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(180.dp)
            )

            Text(
                text = data.phoneNumber,
                style = typography.captionRegular2,
                color = colors.black,
                modifier = Modifier.width(120.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SignUpRequestListItemPreview() {
    SignUpRequestListItem(
        index = 1,
        horizontalScrollState = rememberScrollState(),
        data = AdminRequestAllowListResponseEntity(
            id = 1,
            name = "이명훈",
            nickname = "뀨뀨뀨",
            email = "john.mclean@examplepetstore.com",
            phoneNumber = "010-1234-5678"
        )
    )
}