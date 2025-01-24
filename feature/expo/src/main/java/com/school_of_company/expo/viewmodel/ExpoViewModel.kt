package com.school_of_company.expo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.exception.NoResponseException
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.Image.ImageUpLoadUseCase
import com.school_of_company.domain.usecase.expo.DeleteExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.GetExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.GetExpoListUseCase
import com.school_of_company.domain.usecase.expo.ModifyExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.RegisterExpoInformationUseCase
import com.school_of_company.domain.usecase.juso.GetAddressUseCase
import com.school_of_company.domain.usecase.kakao.GetCoordinatesUseCase
import com.school_of_company.domain.usecase.standard.ModifyStandardProgramUseCase
import com.school_of_company.domain.usecase.standard.RegisterStandardListProgramUseCase
import com.school_of_company.domain.usecase.standard.StandardProgramListUseCase
import com.school_of_company.domain.usecase.training.ModifyTrainingProgramUseCase
import com.school_of_company.domain.usecase.training.RegisterTrainingProgramListUseCase
import com.school_of_company.domain.usecase.training.TrainingProgramListUseCase
import com.school_of_company.expo.enum.TrainingCategory
import com.school_of_company.expo.util.getMultipartFile
import com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetAddressUiState
import com.school_of_company.expo.viewmodel.uistate.GetCoordinatesUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.expo.viewmodel.uistate.GetStandardProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.GetTrainingProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyStandardProgramUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyTrainingProgramUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterStandardProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterTrainingProgramListUiState
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.model.model.juso.JusoModel
import com.school_of_company.model.model.standard.StandardRequestModel
import com.school_of_company.model.model.training.TrainingDtoModel
import com.school_of_company.ui.util.autoFormatToDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExpoViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val getExpoListUseCase: GetExpoListUseCase,
    private val imageUpLoadUseCase: ImageUpLoadUseCase,
    private val getCoordinatesUseCase: GetCoordinatesUseCase,
    private val getExpoInformationUseCase: GetExpoInformationUseCase,
    private val standardProgramListUseCase: StandardProgramListUseCase,
    private val trainingProgramListUseCase: TrainingProgramListUseCase,
    private val deleteExpoInformationUseCase: DeleteExpoInformationUseCase,
    private val modifyExpoInformationUseCase: ModifyExpoInformationUseCase,
    private val modifyTrainingProgramUseCase: ModifyTrainingProgramUseCase,
    private val modifyStandardProgramUseCase: ModifyStandardProgramUseCase,
    private val registerExpoInformationUseCase: RegisterExpoInformationUseCase,
    private val registerStandardProgramListUseCase: RegisterStandardListProgramUseCase,
    private val registerTrainingProgramListUseCase: RegisterTrainingProgramListUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val MODIFY_TITLE = "modify_title"
        private const val STARTED_DATE = "started_date"
        private const val ENDED_DATE = "ended_date"
        private const val INTRODUCE_TITLE = "introduce_title"
        private const val ADDRESS = "address"
        private const val COORDINATEX = "coordinatesx"
        private const val COORDINATEY = "coordinatesy"
        private const val LOCATION = "location"
        private const val COVER_IMAGE = "cover_image"
        private const val STARTED = "started"
        private const val ENDED = "ended"
    }

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _trainingProgramTextState = MutableStateFlow<List<TrainingDtoModel>>(emptyList())
    internal val trainingProgramTextState = _trainingProgramTextState.asStateFlow()

    private val _standardProgramTextState = MutableStateFlow<List<StandardRequestModel>>(emptyList())
    internal val standardProgramTextState = _standardProgramTextState.asStateFlow()

    private val _categoryState = MutableStateFlow(TrainingCategory.CHOICE)
    val categoryState = _categoryState.asStateFlow()

    private val _getExpoInformationUiState = MutableStateFlow<GetExpoInformationUiState>(GetExpoInformationUiState.Loading)
    internal val getExpoInformationUiState = _getExpoInformationUiState.asStateFlow()

    private val _getCoordinatesUiState = MutableStateFlow<GetCoordinatesUiState>(GetCoordinatesUiState.Loading)
    internal val getCoordinatesUiState = _getCoordinatesUiState.asStateFlow()

    private val _getAddressUiState = MutableStateFlow<GetAddressUiState>(GetAddressUiState.Loading)
    internal val getAddressUiState = _getAddressUiState.asStateFlow()

    private val _registerExpoInformationUiState = MutableStateFlow<RegisterExpoInformationUiState>(RegisterExpoInformationUiState.Loading)
    internal val registerExpoInformationUiState = _registerExpoInformationUiState.asStateFlow()

    private val _modifyExpoInformationUiState = MutableStateFlow<ModifyExpoInformationUiState>(ModifyExpoInformationUiState.Loading)
    internal val modifyExpoInformationUiState = _modifyExpoInformationUiState.asStateFlow()

    private val _deleteExpoInformationUiState = MutableStateFlow<DeleteExpoInformationUiState>(DeleteExpoInformationUiState.Loading)
    internal val deleteExpoInformationUiState = _deleteExpoInformationUiState.asStateFlow()

    private val _getExpoListUiState = MutableStateFlow<GetExpoListUiState>(GetExpoListUiState.Loading)
    internal val getExpoListUiState = _getExpoListUiState.asStateFlow()

    private val _imageUpLoadUiState = MutableStateFlow<ImageUpLoadUiState>(ImageUpLoadUiState.Loading)
    internal val imageUpLoadUiState = _imageUpLoadUiState.asStateFlow()

    private val _registerTrainingProgramListUiState = MutableStateFlow<RegisterTrainingProgramListUiState>(RegisterTrainingProgramListUiState.Loading)
    internal val registerTrainingProgramListUiState = _registerTrainingProgramListUiState.asStateFlow()

    private val _modifyTrainingProgramUiState = MutableStateFlow<ModifyTrainingProgramUiState>(ModifyTrainingProgramUiState.Loading)
    internal val modifyTrainingProgramUiState = _modifyTrainingProgramUiState.asStateFlow()

    private val _modifyStandardProgramUiState = MutableStateFlow<ModifyStandardProgramUiState>(ModifyStandardProgramUiState.Loading)
    internal val modifyStandardProgramUiState = _modifyStandardProgramUiState.asStateFlow()

    private val _registerStandardProgramListUiState = MutableStateFlow<RegisterStandardProgramListUiState>(RegisterStandardProgramListUiState.Loading)
    internal val registerStandardProgramListUiState = _registerStandardProgramListUiState.asStateFlow()

    private val _getStandardProgramListUiState = MutableStateFlow<GetStandardProgramListUiState>(GetStandardProgramListUiState.Loading)
    internal val getStandardProgramListUiState = _getStandardProgramListUiState.asStateFlow()

    private val _getTrainingProgramListUiState = MutableStateFlow<GetTrainingProgramListUiState>(GetTrainingProgramListUiState.Loading)
    internal val getTrainingProgramListUiState = _getTrainingProgramListUiState.asStateFlow()

    internal var modify_title = savedStateHandle.getStateFlow(key = MODIFY_TITLE, initialValue = "")

    internal var started_date = savedStateHandle.getStateFlow(key = STARTED_DATE, initialValue = "")

    internal var ended_date = savedStateHandle.getStateFlow(key = ENDED_DATE, initialValue = "")

    internal var introduce_title = savedStateHandle.getStateFlow(key = INTRODUCE_TITLE, initialValue = "")

    internal var address = savedStateHandle.getStateFlow(key = ADDRESS, initialValue = "")

    internal var location = savedStateHandle.getStateFlow(key = LOCATION, initialValue = "")

    internal var cover_image = savedStateHandle.getStateFlow(key = COVER_IMAGE, initialValue = "")

    internal var coordinateX = savedStateHandle.getStateFlow(key = COORDINATEX, initialValue = "")

    internal var coordinateY = savedStateHandle.getStateFlow(key = COORDINATEY, initialValue = "")

    val expoListSize: StateFlow<Int> = getExpoListUiState
        .map { state ->
            when (state) {
                is GetExpoListUiState.Success -> state.data.size
                else -> 0
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val addressList: StateFlow<List<JusoModel>> = getAddressUiState
        .map { state ->
            when (state) {
                is GetAddressUiState.Success -> state.data
                else -> listOf()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf())

    internal fun getExpoInformation(expoId: String) = viewModelScope.launch {
        getExpoInformationUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoInformationUiState.value = GetExpoInformationUiState.Loading
                    is Result.Success -> {
                        _getExpoInformationUiState.value = GetExpoInformationUiState.Success(result.data)

                        result.data.let {
                            onModifyTitleChange(it.title)
                            onStartedDateChange(it.startedDay)
                            onEndedDateChange(it.finishedDay)
                            onIntroduceTitleChange(it.description)
                            onLocationChange(it.location)
                            onCoverImageChange(it.coverImage)
                        }
                    }
                    is Result.Error -> _getExpoInformationUiState.value = GetExpoInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun registerExpoInformation(body: ExpoRequestAndResponseModel) =
        viewModelScope.launch {
            _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
            registerExpoInformationUseCase(
                body = body.copy(
                    startedDay = body.startedDay.autoFormatToDateTime(),
                    finishedDay = body.finishedDay.autoFormatToDateTime(),
                ),
            )
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        Result.Loading -> _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
                        is Result.Success -> _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Success(result.data)
                        is Result.Error -> _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Error(result.exception)
                    }
                }
        }

    internal fun initRegisterExpo() {
        _imageUpLoadUiState.value = ImageUpLoadUiState.Loading
        _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
        _registerTrainingProgramListUiState.value = RegisterTrainingProgramListUiState.Loading
    }

    internal fun modifyExpoInformation(
        expoId: String,
        body: ExpoRequestAndResponseModel
    ) = viewModelScope.launch {
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
        modifyExpoInformationUseCase(
            expoId = expoId,
            body = body.copy(
                startedDay = body.startedDay.autoFormatToDateTime(),
                finishedDay = body.finishedDay.autoFormatToDateTime(),
            )
        )
            .onSuccess {
                it.catch { remoteError ->
                    _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Error(remoteError)
                }.collect {
                    _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Success
                }
            }
            .onFailure { error ->
                _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Error(error)
            }
    }

    internal fun initModifyExpo() {
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
        _modifyTrainingProgramUiState.value = ModifyTrainingProgramUiState.Loading
        _modifyStandardProgramUiState.value = ModifyStandardProgramUiState.Loading
    }

    internal fun resetExpoInformation() {
        if (_registerExpoInformationUiState.value is RegisterExpoInformationUiState.Success) {
            onStartedDateChange("")
            onEndedDateChange("")
            onIntroduceTitleChange("")
            onAddressChange("")
            onLocationChange("")
            onCoverImageChange(null)
            onModifyTitleChange("")
            onStartedChange("")
            onEndedChange("")
            _trainingProgramTextState.value = emptyList()
            _standardProgramTextState.value = emptyList()
        }
    }

    internal fun deleteExpoInformation(expoId: String) = viewModelScope.launch {
        _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Loading
        deleteExpoInformationUseCase(expoId = expoId)
            .onSuccess {
                it.catch { remoteError ->
                    _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Error(remoteError)
                }.collect {
                    _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Success
                    getExpoList()
                }
            }
            .onFailure { error ->
                _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Error(error)
            }
    }

    internal fun getExpoList() = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        getExpoListUseCase()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoListUiState.value = GetExpoListUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getExpoListUiState.value = GetExpoListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _getExpoListUiState.value = GetExpoListUiState.Success(result.data)
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _getExpoListUiState.value = GetExpoListUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
                    }
                }
            }
    }

    internal fun imageUpLoad(
        context: Context,
        image: Uri
    ) = viewModelScope.launch {
        val multipartFile = getMultipartFile(context, image)

        if (multipartFile == null) {
            _imageUpLoadUiState.value = ImageUpLoadUiState.Error(IllegalStateException("이미지 파일 변환 실패"))
            return@launch
        }

        imageUpLoadUseCase(multipartFile)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _imageUpLoadUiState.value = ImageUpLoadUiState.Loading
                    is Result.Success -> _imageUpLoadUiState.value = ImageUpLoadUiState.Success(result.data)
                    is Result.Error -> _imageUpLoadUiState.value = ImageUpLoadUiState.Error(result.exception)
                }
            }
    }

    internal fun registerTrainingProgramList(
        expoId: String,
        body: List<TrainingDtoModel>
    ) = viewModelScope.launch {
        _registerTrainingProgramListUiState.value = RegisterTrainingProgramListUiState.Loading
        registerTrainingProgramListUseCase(
            expoId = expoId,
            body = body.map { list ->
                list.copy(
                    startedAt = list.startedAt.autoFormatToDateTime(),
                    endedAt = list.endedAt.autoFormatToDateTime(),
                )
            }
        )
            .onSuccess {
                it.catch { remoteError ->
                    _registerTrainingProgramListUiState.value = RegisterTrainingProgramListUiState.Error(remoteError)
                }.collect {
                    _registerTrainingProgramListUiState.value = RegisterTrainingProgramListUiState.Success
                }
            }
            .onFailure { error ->
                _registerTrainingProgramListUiState.value = RegisterTrainingProgramListUiState.Error(error)
            }
    }

    internal fun modifyTrainingProgram(
        trainingProId: Long,
        body: TrainingDtoModel
    ) = viewModelScope.launch {
        _modifyTrainingProgramUiState.value = ModifyTrainingProgramUiState.Loading
        modifyTrainingProgramUseCase(
            trainingProId = trainingProId,
            body = body.copy(
                startedAt = body.startedAt.autoFormatToDateTime(),
                endedAt = body.endedAt.autoFormatToDateTime(),
            )
        )
            .onSuccess {
                it.catch { remoteError ->
                    _modifyTrainingProgramUiState.value = ModifyTrainingProgramUiState.Error(remoteError)
                }.collect {
                    _modifyTrainingProgramUiState.value = ModifyTrainingProgramUiState.Success
                }
            }
            .onFailure { error ->
                _modifyTrainingProgramUiState.value = ModifyTrainingProgramUiState.Error(error)
            }
    }

    internal fun modifyStandardProgram(
        standardProId: Long,
        body: StandardRequestModel
    ) = viewModelScope.launch {
        _modifyStandardProgramUiState.value = ModifyStandardProgramUiState.Loading
        modifyStandardProgramUseCase(
            standardProId = standardProId,
            body = body.copy(
                startedAt = body.startedAt.autoFormatToDateTime(),
                endedAt = body.endedAt.autoFormatToDateTime(),
            )
        )
            .onSuccess {
                it.catch { remoteError ->
                    _modifyStandardProgramUiState.value = ModifyStandardProgramUiState.Error(remoteError)
                }.collect {
                    _modifyStandardProgramUiState.value = ModifyStandardProgramUiState.Success
                }
            }
            .onFailure { error ->
                _modifyStandardProgramUiState.value = ModifyStandardProgramUiState.Error(error)
            }
    }

    internal fun registerStandardProgramList(
        expoId: String,
        body: List<StandardRequestModel>
    ) = viewModelScope.launch {
        _registerStandardProgramListUiState.value = RegisterStandardProgramListUiState.Loading
        registerStandardProgramListUseCase(
            expoId = expoId,
            body = body.map { list ->
                list.copy(
                    startedAt = list.startedAt.autoFormatToDateTime(),
                    endedAt = list.endedAt.autoFormatToDateTime(),
                )
            }
        )
            .onSuccess {
                it.catch { remoteError ->
                    _registerStandardProgramListUiState.value = RegisterStandardProgramListUiState.Error(remoteError)
                }.collect {
                    _registerStandardProgramListUiState.value = RegisterStandardProgramListUiState.Success
                }
            }
            .onFailure { error ->
                _registerStandardProgramListUiState.value = RegisterStandardProgramListUiState.Error(error)
            }
    }

    internal fun getStandardProgramList(expoId: String) = viewModelScope.launch {
        _getStandardProgramListUiState.value = GetStandardProgramListUiState.Loading
        standardProgramListUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getStandardProgramListUiState.value = GetStandardProgramListUiState.Loading
                    is Result.Success -> {
                        _getStandardProgramListUiState.value = GetStandardProgramListUiState.Success(result.data)

                        _standardProgramTextState.value = result.data.map { program ->
                            StandardRequestModel(
                                title = program.title,
                                startedAt = program.startedAt,
                                endedAt = program.endedAt
                            )
                        }
                    }
                    is Result.Error -> _getStandardProgramListUiState.value = GetStandardProgramListUiState.Error(result.exception)
                }
            }
    }

    internal fun getTrainingProgramList(expoId: String) = viewModelScope.launch {
        _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Loading
        trainingProgramListUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Loading
                    is Result.Success -> {
                        _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Success(result.data)

                        _trainingProgramTextState.value = result.data.map { program ->
                            TrainingDtoModel(
                                title = program.title,
                                startedAt = program.startedAt,
                                endedAt = program.endedAt,
                                category = program.category
                            )
                        }
                    }
                    is Result.Error -> _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Error(result.exception)
                }
            }
    }

    internal fun searchLocation(searchText: String) =
        viewModelScope.launch {
            getAddressUseCase(searchText = searchText)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _getAddressUiState.value = GetAddressUiState.Loading
                        is Result.Success -> {
                            if(result.data.isNotEmpty()){
                                _getAddressUiState.value =  GetAddressUiState.Success(result.data)
                            } else {
                                _getAddressUiState.value = GetAddressUiState.Error(NoResponseException())
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
                        onCoordinatexChange(result.data.x)
                        onCoordinateYChange(result.data.y)
                        _getCoordinatesUiState.value = GetCoordinatesUiState.Success(result.data)
                    }
                    is Result.Error -> _getCoordinatesUiState.value = GetCoordinatesUiState.Error(result.exception)
                }
            }
    }

    internal fun updateTrainingProgramText(index: Int, updateItem: TrainingDtoModel) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            set(index, updateItem)
        }
    }

    internal fun addTrainingProgramText() {
        _trainingProgramTextState.value += TrainingDtoModel(
            title = "",
            startedAt = "",
            endedAt = "",
            category = TrainingCategory.CHOICE.name
        )
    }

    internal fun removeTrainingProgramText(index: Int) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            removeAt(index)
        }
    }

    internal fun updateStandardProgramText(index: Int, updateItem: StandardRequestModel) {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            set(index, updateItem)
        }
    }

    internal fun addStandardProgramText() {
        _standardProgramTextState.value += StandardRequestModel(
            title = "",
            startedAt = "",
            endedAt = ""
        )
    }

    internal fun removeStandardProgramText(index: Int) {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            removeAt(index)
        }
    }

    internal fun updateExistingTrainingProgram(index: Int, updatedItem: TrainingDtoModel) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            this[index] = updatedItem
        }
    }

    internal fun updateExistingStandardProgram(index: Int, updatedItem: StandardRequestModel) {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            this[index] = updatedItem
        }
    }

    internal fun onModifyTitleChange(value: String) {
        savedStateHandle[MODIFY_TITLE] = value
    }

    internal fun onStartedDateChange(value: String) {
        savedStateHandle[STARTED_DATE] = value
    }

    internal fun onEndedDateChange(value: String) {
        savedStateHandle[ENDED_DATE] = value
    }

    internal fun onIntroduceTitleChange(value: String) {
        savedStateHandle[INTRODUCE_TITLE] = value
    }

    internal fun onAddressChange(value: String) {
        savedStateHandle[ADDRESS] = value
    }

    internal fun onLocationChange(value: String) {
        savedStateHandle[LOCATION] = value
    }

    internal fun onCoverImageChange(value: String?) {
        savedStateHandle[COVER_IMAGE] = value
    }

    internal fun onStartedChange(value: String) {
        savedStateHandle[STARTED] = value
    }

    internal fun onEndedChange(value: String) {
        savedStateHandle[ENDED] = value
    }

    internal fun onCoordinatexChange(value: String) {
        savedStateHandle[COORDINATEX] = value
    }

    internal fun onCoordinateYChange(value: String) {
        savedStateHandle[COORDINATEY] = value
    }

}