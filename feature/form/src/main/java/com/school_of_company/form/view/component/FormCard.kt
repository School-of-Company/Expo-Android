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
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.TransparentTextField
import com.school_of_company.design_system.icon.CheckBoxIcon
import com.school_of_company.design_system.icon.PlusIcon
import com.school_of_company.design_system.icon.TrashIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.form.enum.FormType
import com.school_of_company.model.model.form.DynamicFormModel

@Composable
internal fun FormCard(
    modifier: Modifier = Modifier,
    formIndex: Int,
    formData: DynamicFormModel,
    onFormDataChange: (Int, DynamicFormModel) -> Unit,
    deleteThisForm: (Int) -> Unit,
) {
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
                        textStyle = typography.captionBold2.copy(color = colors.black),
                        placeholderTextStyle = typography.captionBold2.copy(color = colors.gray500),
                        updateTextValue = { onFormDataChange(formIndex, formData.copy(title = it)) }
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(colors.gray100),
                    )
                }

                FormDropDown(
                    currentItem = FormType.valueOf(formData.formType),
                    onItemClick = { onFormDataChange(formIndex, formData.copy(formType = it.name)) },
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                formData.itemList.forEachIndexed { index, item ->
                    when (FormType.valueOf(formData.formType)) {
                        FormType.SENTENCE -> FormSentenceItem(
                            modifier = Modifier.fillMaxWidth(),
                            onXClick = {
                                val newList =
                                    formData.itemList.toMutableList().apply { removeAt(index) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                        )

                        FormType.CHECKBOX -> FormCheckBoxItem(
                            modifier = Modifier.fillMaxWidth(),
                            itemIndex = index,
                            description = item,
                            updateTextValue = { newText ->
                                val newList =
                                    formData.itemList.toMutableList().apply { set(index, newText) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                            onXClick = {
                                val newList =
                                    formData.itemList.toMutableList().apply { removeAt(index) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                        )

                        FormType.DROPDOWN -> FormDropDownItem(
                            modifier = Modifier.fillMaxWidth(),
                            itemIndex = index,
                            description = item,
                            updateTextValue = { newText ->
                                val newList =
                                    formData.itemList.toMutableList().apply { set(index, newText) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                            onXClick = {
                                val newList =
                                    formData.itemList.toMutableList().apply { removeAt(index) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                        )

                        FormType.MULTIPLE -> FormMultiPleItem(
                            modifier = Modifier.fillMaxWidth(),
                            itemIndex = index,
                            description = item,
                            updateTextValue = { newText ->
                                val newList =
                                    formData.itemList.toMutableList().apply { set(index, newText) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                            onXClick = {
                                val newList =
                                    formData.itemList.toMutableList().apply { removeAt(index) }
                                onFormDataChange(formIndex, formData.copy(itemList = newList))
                            },
                        )
                    }

                }
                if (formData.otherJson) {
                    when (FormType.valueOf(formData.formType)) {
                        FormType.SENTENCE -> onFormDataChange(
                            formIndex,
                            formData.copy(otherJson = false)
                        )

                        FormType.CHECKBOX -> FormCheckBoxItem(
                            modifier = Modifier.fillMaxWidth(),
                            isEtc = true,
                            onXClick = {
                                onFormDataChange(
                                    formIndex, formData.copy(otherJson = false)
                                )
                            },
                        )

                        FormType.DROPDOWN -> FormDropDownItem(
                            modifier = Modifier.fillMaxWidth(),
                            itemIndex = formData.itemList.size,
                            isEtc = true,
                            onXClick = {
                                onFormDataChange(
                                    formIndex, formData.copy(otherJson = false)
                                )
                            },
                        )

                        FormType.MULTIPLE -> FormMultiPleItem(
                            modifier = Modifier.fillMaxWidth(),
                            isEtc = true,
                            onXClick = {
                                onFormDataChange(
                                    formIndex, formData.copy(otherJson = false)
                                )
                            },
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .expoClickable {
                                onFormDataChange(
                                    formIndex,
                                    formData.copy(itemList = formData.itemList + "")
                                )
                            },
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
                            onFormDataChange(
                                formIndex,
                                formData.copy(otherJson = !formData.otherJson)
                            )
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
                    modifier = Modifier.expoClickable { deleteThisForm(formIndex) },
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

                    FormToggleButton(
                        width = 45.dp,
                        height = 18.dp,
                        check = formData.requiredStatus,
                        onClick = {
                            onFormDataChange(
                                formIndex,
                                formData.copy(requiredStatus = !formData.requiredStatus)
                            )
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
    var formData by remember {
        mutableStateOf(
            DynamicFormModel(
                formType = FormType.CHECKBOX.name,
                title = "",
                itemList = listOf(),
                otherJson = false,
                requiredStatus = false
            )
        )
    }

    FormCard(
        formData = formData,
        onFormDataChange = { _, item -> formData = item },
        deleteThisForm = { /* 삭제 로직 */ },
        formIndex = 0
    )
}