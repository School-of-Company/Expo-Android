package com.school_of_company.expo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.exception.NoResponseException
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.juso.GetAddressUseCase
import com.school_of_company.domain.usecase.kakao.GetCoordinatesUseCase
import com.school_of_company.expo.viewmodel.uistate.GetAddressUiState
import com.school_of_company.expo.viewmodel.uistate.GetCoordinatesUiState
import com.school_of_company.model.model.juso.JusoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExpoAddressSearchViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val getCoordinatesUseCase: GetCoordinatesUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object{
        private const val COORDINATEX = "coordinatesx"
        private const val COORDINATEY = "coordinatesy"
        private const val LOCATION = "location"
    }

    private val _getAddressUiState = MutableStateFlow<GetAddressUiState>(GetAddressUiState.Loading)
    internal val getAddressUiState = _getAddressUiState.asStateFlow()

    private val _getCoordinatesUiState = MutableStateFlow<GetCoordinatesUiState>(GetCoordinatesUiState.Loading)
    internal val getCoordinatesUiState = _getCoordinatesUiState.asStateFlow()

    internal val location = savedStateHandle.getStateFlow(key = LOCATION, initialValue = "")

    val addressList: StateFlow<List<JusoModel>> = getAddressUiState
        .map { state ->
            when (state) {
                is GetAddressUiState.Success -> state.data
                else -> listOf()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    internal fun searchLocation(searchText: String) =
        viewModelScope.launch {
            getAddressUseCase(searchText = searchText)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _getAddressUiState.value = GetAddressUiState.Loading
                        is Result.Success -> {
                            if(result.data.isNotEmpty()) {
                                _getAddressUiState.value =  GetAddressUiState.Success(result.data)
                            } else {
                                _getAddressUiState.value = GetAddressUiState.Error(
                                    NoResponseException()
                                )
                            }
                        }
                        is Result.Error -> _getAddressUiState.value = GetAddressUiState.Error(result.exception)
                    }
                }
        }

    internal fun convertJibunToXY(searchText: String) = viewModelScope.launch {
        getCoordinatesUseCase(address = searchText)
            .asResult()
            .collectLatest { result ->
                when(result){
                    is Result.Loading -> _getCoordinatesUiState.value = GetCoordinatesUiState.Loading
                    is Result.Success -> if (result.data.addressName == "Unknown") {
                        _getCoordinatesUiState.value = GetCoordinatesUiState.Error(NoResponseException())
                    } else {
                        onCoordinateChange(
                            x = result.data.x,
                            y = result.data.y
                        )
                        _getCoordinatesUiState.value = GetCoordinatesUiState.Success(result.data)
                    }
                    is Result.Error -> _getCoordinatesUiState.value = GetCoordinatesUiState.Error(result.exception)
                }
            }
    }

    private fun onCoordinateChange(x: String, y:String) {
        savedStateHandle[COORDINATEX] = x
        savedStateHandle[COORDINATEY] = y
    }

    internal fun onLocationChange(value: String) {
        savedStateHandle[LOCATION] = value
    }
}