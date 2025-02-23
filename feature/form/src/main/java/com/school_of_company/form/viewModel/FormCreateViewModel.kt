package com.school_of_company.form.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.domain.usecase.form.CreateFormUseCase
import com.school_of_company.form.enum.FormType
import com.school_of_company.form.viewModel.uiState.CreateFormUiState
import com.school_of_company.model.model.form.DynamicFormModel
import com.school_of_company.model.model.form.FormRequestAndResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FormCreateViewModel @Inject constructor(
    private val createFormUseCase: CreateFormUseCase,
) : ViewModel() {

    private val _formState = MutableStateFlow<List<DynamicFormModel>>(emptyList())
    internal val formState = _formState.asStateFlow()

    private val _createFormUiState = MutableStateFlow<CreateFormUiState>(CreateFormUiState.Loading)
    internal val createFormUiState = _createFormUiState.asStateFlow()

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
        val newItem = DynamicFormModel(
            title = "",
            formType = FormType.SENTENCE.name,
            itemList = listOf(""),
            requiredStatus = false,
            otherJson = false
        )
        _formState.value += newItem
    }

    internal fun createForm(
        expoId: String,
        informationImage: String,
        participantType: String,
    ) = viewModelScope.launch {
        _createFormUiState.value = CreateFormUiState.Loading
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
                    _createFormUiState.value = CreateFormUiState.Error(remoteError)
                }.collect {
                    _createFormUiState.value = CreateFormUiState.Success
                }
            }
            .onFailure { error ->
                _createFormUiState.value = CreateFormUiState.Error(error)
            }
    }
}