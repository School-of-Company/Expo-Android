package com.school_of_company.form.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.form.CreateFormUseCase
import com.school_of_company.domain.usecase.form.GetFormUseCase
import com.school_of_company.domain.usecase.form.ModifyFormUseCase
import com.school_of_company.form.viewModel.uiState.FormUiState
import com.school_of_company.form.viewModel.uiState.GetFormUiState
import com.school_of_company.model.model.form.DynamicFormModel
import com.school_of_company.model.model.form.FormRequestAndResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
internal class FormViewModel @Inject constructor(
    private val createFormUseCase: CreateFormUseCase,
    private val getFormUseCase: GetFormUseCase,
    private val modifyFormUseCase: ModifyFormUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val FORM_STATE = "form_state"
        private const val INFORMATION_TEXT_STATE = "information_text_state"
    }

    private val _formUiState = MutableStateFlow<FormUiState>(FormUiState.Loading)
    internal val formUiState = _formUiState.asStateFlow()

    private val _getFormUiState = MutableStateFlow<GetFormUiState>(GetFormUiState.Loading)

    internal val formState = savedStateHandle.getStateFlow(FORM_STATE, listOf(DynamicFormModel.createDefault()))
    internal val informationTextState = savedStateHandle.getStateFlow(INFORMATION_TEXT_STATE, "")

    private var _isNotFoundError = MutableStateFlow(false)
    internal fun setIsNotFoundError(value: Boolean) {
        _isNotFoundError.value = value
    }

    private var _isConflictError = MutableStateFlow(false)
    internal fun setIsConflictError(value: Boolean) {
        _isConflictError.value = value
    }

    internal fun createForm(
        expoId: String,
        participantType: String,
        informationText: String,
    ) = viewModelScope.launch {
        _formUiState.value = FormUiState.Loading
        createFormUseCase(
            expoId = expoId,
            body = FormRequestAndResponseModel(
                participantType = participantType,
                dynamicForm = formState.value,
                informationText = informationText
            ),
        )
            .onSuccess {
                it.catch { remoteError ->
                    _formUiState.value = when {
                        remoteError is HttpException -> when (remoteError.code()) {
                            409 -> FormUiState.Conflict
                            else -> FormUiState.Error(remoteError)
                        }
                        else -> FormUiState.Error(remoteError)
                    }
                }.collect {
                    _formUiState.value = FormUiState.Success
                }
            }
            .onFailure { error ->
                _formUiState.value = FormUiState.Error(error)
            }
    }

    internal fun modifyForm(
        expoId: String,
        participantType: String,
        informationText: String,
    ) = viewModelScope.launch {
        _formUiState.value = FormUiState.Loading
        modifyFormUseCase(
            expoId = expoId,
            body = FormRequestAndResponseModel(
                participantType = participantType,
                dynamicForm = formState.value,
                informationText = informationText
            ),
        )
            .onSuccess {
                it.catch { remoteError ->
                    _formUiState.value = when {
                        remoteError is HttpException -> when (remoteError.code()) {
                            404 -> FormUiState.NotFound
                            else -> FormUiState.Error(remoteError)
                        }
                        else -> FormUiState.Error(remoteError)
                    }
                }.collect {
                    _formUiState.value = FormUiState.Success
                }
            }
            .onFailure { error ->
                _formUiState.value = FormUiState.Error(error)
            }
    }

    internal fun getForm(
        expoId: String,
        participantType: String,
    ) = viewModelScope.launch {
        _getFormUiState.value = GetFormUiState.Loading
        getFormUseCase(
            expoId = expoId,
            participantType = participantType,
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getFormUiState.value = GetFormUiState.Loading
                    is Result.Success -> with(result.data) {
                        _getFormUiState.value = GetFormUiState.Success
                        savedStateHandle[FORM_STATE] = dynamicForm
                        savedStateHandle[INFORMATION_TEXT_STATE] = informationText
                    }

                    is Result.Error -> _getFormUiState.value =
                        GetFormUiState.Error(result.exception)
                }
            }
    }

    internal fun updateDynamicFormItem(index: Int, newItem: DynamicFormModel) {
        val currentList = formState.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = newItem
            savedStateHandle[FORM_STATE] = currentList
        }
    }

    internal fun removeDynamicFormItem(index: Int) {
        val currentList = formState.value.toMutableList()
        if (index in currentList.indices) {
            currentList.removeAt(index)
            savedStateHandle[FORM_STATE] = currentList
        }
    }

    internal fun addEmptyDynamicFormItem() {
        savedStateHandle[FORM_STATE] = formState.value + DynamicFormModel.createDefault()
    }

    internal fun setInformationTextState(value: String) {
        savedStateHandle[INFORMATION_TEXT_STATE] = value
    }
}