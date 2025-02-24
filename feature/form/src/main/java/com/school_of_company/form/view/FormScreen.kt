package com.school_of_company.form.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoButton
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.form.enum.FormActionType
import com.school_of_company.form.enum.FormType
import com.school_of_company.form.view.component.FormAddButton
import com.school_of_company.form.view.component.FormCard
import com.school_of_company.form.viewModel.FormViewModel
import com.school_of_company.form.viewModel.uiState.FormUiState
import com.school_of_company.model.model.form.DynamicFormModel

@Composable
internal fun FormRoute(
    modifier: Modifier = Modifier,
    expoId: String,
    informationImage: String,
    participantType: String,
    formActionType: FormActionType,
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: FormViewModel = hiltViewModel(),
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val formActionUiState by viewModel.formUiState.collectAsStateWithLifecycle()

    if (formActionType == FormActionType.MODIFY) {
        LaunchedEffect(Unit) {
            viewModel.getForm(
                expoId = expoId,
                participantType = participantType,
            )
        }
    }

    LaunchedEffect(formActionUiState) {
        when (formActionUiState) {
            is FormUiState.Loading -> Unit
            is FormUiState.Success -> {
                popUpBackStack()
                when (formActionType) {
                    FormActionType.MODIFY -> onErrorToast(null, R.string.form_modify_success)
                    FormActionType.CREATE -> onErrorToast(null, R.string.form_create_success)
                }
            }

            is FormUiState.Error -> {
                when (formActionType) {
                    FormActionType.MODIFY -> onErrorToast(null, R.string.form_modify_success)
                    FormActionType.CREATE -> onErrorToast(null, R.string.form_modify_fail)
                }
            }
        }
    }

    FormScreen(
        modifier = modifier,
        formActionType = formActionType,
        formList = formState,
        popUpBackStack = popUpBackStack,
        addFormAtList = viewModel::addEmptyDynamicFormItem,
        submitForm = {
            when (formActionType) {
                FormActionType.MODIFY -> viewModel.modifyForm(
                    expoId,
                    participantType,
                )
                FormActionType.CREATE -> viewModel.createForm(
                    expoId,
                    informationImage,
                    participantType,
                )
            }
        },
        deleteForm = viewModel::removeDynamicFormItem,
        onFormDataChange = viewModel::updateDynamicFormItem
    )
}

@Composable
private fun FormScreen(
    modifier: Modifier = Modifier,
    formActionType: FormActionType,
    formList: List<DynamicFormModel>,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    popUpBackStack: () -> Unit,
    addFormAtList: () -> Unit,
    submitForm: () -> Unit,
    deleteForm: (Int) -> Unit,
    onFormDataChange: (Int, DynamicFormModel) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
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
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable(onClick = popUpBackStack)
                    )
                },
                betweenText = when (formActionType) {
                    FormActionType.MODIFY -> "폼 수정하기"
                    FormActionType.CREATE -> "신정차 폼"
                },
                modifier = Modifier.padding(vertical = 16.dp),
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                modifier = Modifier.fillMaxWidth(),
            ) {
                formList.forEachIndexed { index, item ->
                    FormCard(
                        formIndex = index,
                        formData = item,
                        onFormDataChange = onFormDataChange,
                        deleteThisForm = deleteForm,
                    )
                }

                FormAddButton(onClick = addFormAtList)
            }

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(16.dp))

            ExpoButton(
                text = "다음",
                color = colors.main,
                onClick = submitForm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(vertical = 12.dp),
            )
        }
    }
}

@Preview
@Composable
private fun FormCreateScreenPreview() {
    FormScreen(
        formList = listOf(
            DynamicFormModel(
                title = "제목",
                formType = FormType.DROPDOWN.name,
                itemList = listOf("예시", "예시 1"),
                requiredStatus = true,
                otherJson = true,
            ),
            DynamicFormModel(
                title = "제목",
                formType = FormType.DROPDOWN.name,
                itemList = listOf("예시", "예시 1"),
                requiredStatus = true,
                otherJson = true,
            ),
        ),
        popUpBackStack = {},
        addFormAtList = { },
        submitForm = {},
        deleteForm = { _ -> },
        onFormDataChange = { _, _ -> },
        formActionType = FormActionType.MODIFY,
    )
}