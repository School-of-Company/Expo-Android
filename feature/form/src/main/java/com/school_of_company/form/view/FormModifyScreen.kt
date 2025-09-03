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
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.form.enum.FormType
import com.school_of_company.form.view.component.FormAddButton
import com.school_of_company.form.view.component.FormCard
import com.school_of_company.form.view.component.PersonaInformationFormCard
import com.school_of_company.form.viewModel.FormViewModel
import com.school_of_company.form.viewModel.uiState.FormUiState
import com.school_of_company.model.model.form.DynamicFormModel
import java.text.Normalizer.Form

@Composable
internal fun FormModifyRoute(
    modifier: Modifier = Modifier,
    expoId: String,
    participantType: String,
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: FormViewModel = hiltViewModel(),
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val formActionUiState by viewModel.formUiState.collectAsStateWithLifecycle()
    val informationTextState by viewModel.informationTextState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getForm(
            expoId = expoId,
            participantType = participantType,
        )
    }

    LaunchedEffect(formActionUiState) {
        when (formActionUiState) {
            is FormUiState.Loading -> Unit
            is FormUiState.Success -> {
                popUpBackStack()
                onErrorToast(null, R.string.form_modify_success)
            }
            is FormUiState.NotFound -> {
                viewModel.setIsNotFoundError(true)
                onErrorToast(null, R.string.form_modify_not_found)
            }
            is FormUiState.Conflict -> Unit
            is FormUiState.Error -> onErrorToast(null, R.string.form_modify_fail)
        }
    }

    FormModifyScreen(
        modifier = modifier,
        informationTextState = informationTextState,
        formList = formState,
        popUpBackStack = popUpBackStack,
        addFormAtList = viewModel::addEmptyDynamicFormItem,
        submitForm = {
            viewModel.modifyForm(
                expoId,
                participantType,
                informationTextState,
            )
        },
        deleteForm = viewModel::removeDynamicFormItem,
        onInformationTextStateChange = viewModel::setInformationTextState,
        onFormDataChange = viewModel::updateDynamicFormItem
    )
}

@Composable
private fun FormModifyScreen(
    modifier: Modifier = Modifier,
    informationTextState: String,
    formList: List<DynamicFormModel>,
    focusManager: FocusManager = LocalFocusManager.current,
    scrollState: ScrollState = rememberScrollState(),
    popUpBackStack: () -> Unit,
    addFormAtList: () -> Unit,
    submitForm: () -> Unit,
    deleteForm: (Int) -> Unit,
    onInformationTextStateChange: (String) -> Unit,
    onFormDataChange: (Int, DynamicFormModel) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = colors.white)
                .padding(horizontal = 16.dp,)
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
                betweenText = "폼 수정하기",
                modifier = Modifier.padding(
                    top = 68.dp,
                    bottom = 16.dp
                ),
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

                PersonaInformationFormCard(
                    value = informationTextState,
                    onTextChange = onInformationTextStateChange
                )

                FormAddButton(onClick = addFormAtList)
            }

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(16.dp))

            ExpoStateButton(
                text = "수정 완료",
                state = if (
                    informationTextState.isNotEmpty() &&
                    formList.isNotEmpty() &&
                    formList.all { form ->
                        when (FormType.valueOf(form.formType)) {
                            FormType.SENTENCE -> form.title.isNotEmpty()
                            FormType.CHECKBOX, FormType.DROPDOWN, FormType.MULTIPLE ->
                                form.title.isNotEmpty() &&
                                form.itemList.isNotEmpty() &&
                                form.itemList.all { it.isNotEmpty() }
                        }
                    }) ButtonState.Enable else ButtonState.Disable,
                onClick = submitForm,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(bottom = 28.dp))
        }
    }
}

@Preview
@Composable
private fun FormModifyScreenPreview() {
    FormModifyScreen(
        informationTextState = "informationTextState",
        formList = listOf(
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
        onInformationTextStateChange = { _ -> },
    )
}