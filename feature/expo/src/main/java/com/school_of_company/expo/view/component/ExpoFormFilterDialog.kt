package com.school_of_company.expo.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.XIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.enum.FilterOptionEnum
import com.school_of_company.expo.viewmodel.ExpoViewModel

@Composable
internal fun ExpoFormFilterDialog(
    modifier: Modifier = Modifier,
    selectedOptions: List<FilterOptionEnum?>,
    onTrainingFormTrueClick: () -> Unit,
    onTrainingFormFalseClick: () -> Unit,
    onStudentFormTrueClick: () -> Unit,
    onStudentFormFalseClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    ExpoAndroidTheme { colors, typography ->

        val options = listOf(
            FilterOption(
                label = "연수자 폼 (O)",
                selected = selectedOptions.contains(FilterOptionEnum.TRAINING_FORM_TRUE),
                onClick = onTrainingFormTrueClick
            ),
            FilterOption(
                label = "연수자 폼 (X)",
                selected = selectedOptions.contains(FilterOptionEnum.TRAINING_FORM_FALSE),
                onClick = onTrainingFormFalseClick
            ),
            FilterOption(
                label = "참가자 폼 (O)",
                selected = selectedOptions.contains(FilterOptionEnum.STUDENT_FORM_TRUE),
                onClick = onStudentFormTrueClick
            ),
            FilterOption(
                label = "참가자 폼 (X)",
                selected = selectedOptions.contains(FilterOptionEnum.STUDENT_FORM_FALSE),
                onClick = onStudentFormFalseClick
            )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(all = 28.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "필터",
                    color = colors.black,
                    style = typography.titleBold3
                )

                Spacer(modifier = Modifier.weight(1f))

                XIcon(
                    tint = colors.black,
                    modifier = Modifier.expoClickable { onDismissClick() }
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 32.dp))

            Column(horizontalAlignment = Alignment.Start,) {
                Text(
                    text = "폼 생성",
                    color = colors.black,
                    style = typography.bodyRegular1
                )

                ExpoFormFilterGroupButton(
                    options = options
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExpoFormFilterDialogPreview() {
    ExpoFormFilterDialog(
        selectedOptions = listOf(),
        onTrainingFormTrueClick = {},
        onTrainingFormFalseClick = {},
        onStudentFormTrueClick = {},
        onStudentFormFalseClick = {},
        onDismissClick = {}
    )
}