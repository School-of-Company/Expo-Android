package com.school_of_company.form.viewModel

import androidx.lifecycle.ViewModel
import com.school_of_company.form.enum.FormType
import com.school_of_company.form.viewModel.viewData.DynamicFormViewData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class FormCreateViewModel @Inject constructor(
) : ViewModel() {

    private val _formState = MutableStateFlow<List<DynamicFormViewData>>(emptyList())
    internal val formState = _formState.asStateFlow()

    internal fun updateDynamicFormItem(index: Int, newItem: DynamicFormViewData) {
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
        val newItem = DynamicFormViewData(
            title = "",
            formType = FormType.SENTENCE,
            itemList = emptyList(),
            requiredStatus = false,
            otherJson = false
        )
        _formState.value += newItem
    }
}