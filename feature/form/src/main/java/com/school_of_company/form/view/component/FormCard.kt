package com.school_of_company.form.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.ExpoToggleButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.TransparentTextField
import com.school_of_company.design_system.icon.CheckBoxIcon
import com.school_of_company.design_system.icon.PlusIcon
import com.school_of_company.design_system.icon.TrashIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.form.enum.FormType
import com.school_of_company.form.viewModel.viewData.DynamicFormViewData

@Composable
fun FormCard(
    modifier: Modifier = Modifier,
    deleteThisForm: () -> Unit,
) {
    var formData by remember {
        mutableStateOf(
            DynamicFormViewData(
                formType = FormType.CHECKBOX,
                itemList = emptyList(),
                title = "",
                otherJson = false,
                requiredStatus = false
            )
        )
    }

    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = colors.gray200,
                    shape = RoundedCornerShape(size = 6.dp),
                )
                .background(
                    color = colors.white,
                    shape = RoundedCornerShape(size = 6.dp),
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    TransparentTextField(
                        placeholder = "제목 입력",
                        value = formData.title,
                        textStyle = typography.bodyBold2,
                        updateTextValue = { formData = formData.copy(title = it) }
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(colors.gray100),
                    )
                }

                FormDropDown(
                    currentItem = formData.formType,
                    onItemClick = { formData = formData.copy(formType = it) },
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (formData.formType) {
                    FormType.SENTENCE -> Unit
                    FormType.CHECKBOX -> Unit
                    FormType.DROPDOWN -> Unit
                    FormType.IMAGE -> Unit
                    FormType.MULTIPLE -> Unit
                }

                Column (horizontalAlignment = Alignment.CenterHorizontally){
                    Row(
                        modifier = Modifier.padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
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

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(colors.gray100),
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.expoClickable(
                        onClick = {
                            formData = formData.copy(otherJson = !formData.otherJson)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CheckBoxIcon(
                        modifier = Modifier.size(16.dp),
                        isSelected = formData.otherJson,
                        tint = if (formData.otherJson) colors.main
                        else colors.gray500,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "기타",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = if (formData.otherJson) colors.main
                        else colors.gray500,
                    )
                }
                Row(
                    modifier = Modifier.expoClickable(onClick = deleteThisForm),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TrashIcon(tint = colors.error)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "버리기",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.error,
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "필수",
                        style = typography.captionRegular2,
                        fontWeight = FontWeight.W400,
                        color = colors.black,
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    ExpoToggleButton(
                        width = 45.dp,
                        height = 18.dp,
                        check = formData.requiredStatus,
                        onClick = {
                            formData = formData.copy(requiredStatus = !formData.requiredStatus)
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FormCardPreview() {
    FormCard(deleteThisForm = {})
}