package com.school_of_company.home.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.standard.StandardProgramListResponseEntity

@Composable
fun StandardProgramListItem(
    modifier: Modifier = Modifier,
    index: Int,
    data: StandardProgramListResponseEntity,
    navigateToProgramDetail: (Long) -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(color = colors.white)
                .padding(vertical = 10.dp)
                .expoClickable { navigateToProgramDetail(data.id) }
        ) {

            Text(
                text = index.toString(),
                style = typography.captionBold1,
                color = colors.black,
                modifier = Modifier.weight(0.5f)
            )


            Text(
                text = data.title,
                style = typography.captionRegular2,
                color = colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(2f)
            )

            XIcon(
                tint = colors.error,
                modifier = Modifier
                    .size(16.dp)
                    .weight(1f)
            )

            XIcon(
                tint = colors.error,
                modifier = Modifier
                    .size(16.dp)
                    .weight(1f)
            )
        }
    }
}