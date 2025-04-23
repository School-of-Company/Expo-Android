package com.school_of_company.program.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme

@Composable
internal fun ProgramDetailParticipantDropdownMenu(
    modifier: Modifier = Modifier,
    selectedItem: Int?,
    onCancelClick: () -> Unit,
    onItemSelected: (Int) -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        DropdownMenu(
            expanded = true,
            onDismissRequest = onCancelClick,
            modifier = modifier.background(color = colors.white)
        ) {

            val menuItems = listOf("사전 & 현장 행사 참가자", "교원 원수 참가자")

            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = title,
                            style = typography.bodyRegular2,
                            color = if (selectedItem == index) colors.main else colors.gray500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        onItemSelected(index)
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .background(
                            if (selectedItem == index) colors.main100 else colors.white,
                            shape = RoundedCornerShape(size = 6.dp)
                        )
                )
            }
        }
    }
}