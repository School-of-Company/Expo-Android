package com.school_of_company.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.home.util.qrGenerator

data class QrCode(
    val content: String,
)

@Composable
internal fun QrDialog(
    modifier: Modifier = Modifier,
    data: QrCode,
    onCancelClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .paddingHorizontal(horizontal = 12.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "현장 신청 QR코드",
                    style = typography.titleBold3,
                    fontWeight = FontWeight(600)
                )

                XIcon(modifier = Modifier.expoClickable { onCancelClick() })
            }

            Image(
                painter = qrGenerator(content = data.content),
                contentDescription = "QR Code Image"
            )
        }
    }
}

@Preview
@Composable
private fun QrDialogPreview() {
    QrDialog(
        data = QrCode(content = "121231342352"),
        onCancelClick = {}
    )
}