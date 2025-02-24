package com.school_of_company.form.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.form.CreateFormUseCase
import com.school_of_company.domain.usecase.form.GetFormUseCase
import com.school_of_company.domain.usecase.form.ModifyFormUseCase
import com.school_of_company.form.enum.FormType
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
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
internal class FormViewModel @Inject constructor(
    private val createFormUseCase: CreateFormUseCase,
    private val getFormUseCase: GetFormUseCase,
    private val modifyFormUseCase: ModifyFormUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _coverImage by savedStateHandle.saveable {
        mutableStateOf("")
    }

    private val _formState = MutableStateFlow<List<DynamicFormModel>>(listOf(DynamicFormModel.createDefault()))
    internal val formState = _formState.asStateFlow()

    private val _formUiState = MutableStateFlow<FormUiState>(FormUiState.Loading)
    internal val formUiState = _formUiState.asStateFlow()

    private val _getFormUiState = MutableStateFlow<GetFormUiState>(GetFormUiState.Loading)
    internal val getFormUiState = _formUiState.asStateFlow()

    internal fun updateDynamicFormItem(index: Int, newItem: DynamicFormModel) {
        val currentList = _formState.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] = newItem
            _formState.value = currentList
        }
    }

    internal fun removeDynamicFormItem(index: Int) {
        val currentList = _formState.value.toMutableList()
        if (index in currentList.indices) {
            currentList.removeAt(index)
            _formState.value = currentList
        }
    }

    internal fun addEmptyDynamicFormItem() {
        _formState.value += DynamicFormModel.createDefault()
    }

    internal fun createForm(
        expoId: String,
        informationImage: String,
        participantType: String,
    ) = viewModelScope.launch {
        _formUiState.value = FormUiState.Loading
        createFormUseCase(
            expoId = expoId,
            body = FormRequestAndResponseModel(
                informationImage = informationImage,
                participantType = participantType,
                dynamicForm = _formState.value
            ),
        )
            .onSuccess {
                it.catch { remoteError ->
                    _formUiState.value = FormUiState.Error(remoteError)
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
    ) = viewModelScope.launch {
        _formUiState.value = FormUiState.Loading
        modifyFormUseCase(
            expoId = expoId,
            body = FormRequestAndResponseModel(
                informationImage = _coverImage,
                participantType = participantType,
                dynamicForm = _formState.value
            ),
        )
            .onSuccess {
                it.catch { remoteError ->
                    _formUiState.value = FormUiState.Error(remoteError)
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
        ).asResult()
            .collectLatest {
                when (it) {
                    is Result.Loading -> _getFormUiState.value = GetFormUiState.Loading
                    is Result.Success -> with(it.data) {
                        _getFormUiState.value = GetFormUiState.Success
                        _formState.value = dynamicForm
                        _coverImage = informationImage
                    }
                    is Result.Error -> _getFormUiState.value = GetFormUiState.Error(it.exception)
                }
            }
    }
}