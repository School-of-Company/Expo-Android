package com.school_of_company.form.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.ExpoButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.form.enum.FormType
import com.school_of_company.form.view.component.FormAddButton
import com.school_of_company.form.view.component.FormCard
import com.school_of_company.form.viewModel.viewData.DynamicFormViewData

@Composable
internal fun FormCreateRoute(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit,
) {
    FormCreateScreen(
        modifier = modifier,
        formList = listOf(),
        popUpBackStack = popUpBackStack,
        addFormAtList = { },
        createForm = { },
        deleteForm = { _ -> },
        onFormDataChange = { _, _ -> }
    )
}

@Composable
private fun FormCreateScreen(
    modifier: Modifier = Modifier,
    formList: List<DynamicFormViewData>,
    focusManager: FocusManager = LocalFocusManager.current,
    popUpBackStack: () -> Unit,
    addFormAtList: () -> Unit,
    createForm: () -> Unit,
    deleteForm: (Int) -> Unit,
    onFormDataChange: (Int, DynamicFormViewData) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focusManager.clearFocus()
                        }
                    )
                },
        ) {
            ExpoTopBar(
                modifier = Modifier.padding(vertical = 16.dp),
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable(onClick = popUpBackStack)
                    )
                },
                betweenText = "신정차 폼",
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                ) {
                    itemsIndexed(formList) { index, item ->
                        FormCard(
                            formIndex = index,
                            formData = item,
                            onFormDataChange = onFormDataChange,
                            deleteThisForm = deleteForm,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormAddButton(onClick = addFormAtList)
            }

            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .widthIn(min = 16.dp),
            )

            ExpoButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                text = "다음",
                color = colors.main,
                onClick = createForm,
            )
        }
    }
}

@Preview
@Composable
private fun FormCreateScreenPreview() {
    FormCreateScreen(
        addFormAtList = { },
        onFormDataChange = { _, _ -> },
        createForm = {},
        deleteForm = { _ -> },
        formList = listOf(
            DynamicFormViewData(
                title = "제목",
                formType = FormType.DROPDOWN,
                itemList = listOf("예시", "예시 1"),
                requiredStatus = true,
                otherJson = true,
            ),
            DynamicFormViewData(
                title = "제목",
                formType = FormType.IMAGE,
                itemList = listOf("예시", "예시 1"),
                requiredStatus = true,
                otherJson = true,
            ),
        ),
        popUpBackStack = {},
    )
}