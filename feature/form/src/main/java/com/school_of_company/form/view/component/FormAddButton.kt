package com.school_of_company.form.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.PlusIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
fun FormAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .expoClickable(onClick = onClick)
                .background(
                    color = colors.main100,
                    shape = RoundedCornerShape(size = 6.dp),
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
        ) {
            PlusIcon(tint = colors.main)

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "추가하기",
                style = typography.bodyBold2,
                fontWeight = FontWeight.W600,
                color = colors.main,
            )
        }
    }
}

@Preview
@Composable
private fun FormAddButtonPreview() {
    FormAddButton(
        onClick = { },
    )
}