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
import com.school_of_company.domain.usecase.kakao.GetCoordinatesToAddressUseCase
import com.school_of_company.domain.usecase.kakao.GetCoordinatesUseCase
import com.school_of_company.domain.usecase.standard.StandardProgramListUseCase
import com.school_of_company.domain.usecase.training.TrainingProgramListUseCase
import com.school_of_company.expo.enum.TrainingCategory
import com.school_of_company.expo.util.getMultipartFile
import com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetAddressUiState
import com.school_of_company.expo.viewmodel.uistate.GetCoordinatesToAddressUiState
import com.school_of_company.expo.viewmodel.uistate.GetCoordinatesUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.expo.viewmodel.uistate.GetStandardProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.GetTrainingProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState
import com.school_of_company.model.model.juso.JusoModel
import com.school_of_company.model.param.expo.ExpoAllRequestParam
import com.school_of_company.model.param.expo.ExpoModifyRequestParam
import com.school_of_company.model.param.expo.StandardProIdRequestParam
import com.school_of_company.model.param.expo.StandardProRequestParam
import com.school_of_company.model.param.expo.TrainingProIdRequestParam
import com.school_of_company.model.param.expo.TrainingProRequestParam
import com.school_of_company.ui.util.autoFormatToDateTime
import com.school_of_company.ui.util.formatNoneHyphenServerDate
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
    private val getCoordinatesToAddressUseCase: GetCoordinatesToAddressUseCase,
    private val registerExpoInformationUseCase: RegisterExpoInformationUseCase,
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
        private const val SEARCHED_COORDINATEX = "searched_coordinatesx"
        private const val SEARCHED_COORDINATEY = "searched_coordinatesy"
        private const val SEARCHED_LOCATION = "searched_location"
        private const val COVER_IMAGE = "cover_image"
        private const val STARTED = "started"
        private const val ENDED = "ended"
    }

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _trainingProgramTextState = MutableStateFlow<List<TrainingProRequestParam>>(emptyList())
    internal val trainingProgramTextState = _trainingProgramTextState.asStateFlow()

    private val _standardProgramTextState = MutableStateFlow<List<StandardProRequestParam>>(emptyList())
    internal val standardProgramTextState = _standardProgramTextState.asStateFlow()

    private val _trainingProgramModifyTextState = MutableStateFlow<List<TrainingProIdRequestParam>>(emptyList())
    internal val trainingProgramModifyTextState = _trainingProgramModifyTextState.asStateFlow()

    private val _standardProgramModifyTextState = MutableStateFlow<List<StandardProIdRequestParam>>(emptyList())
    internal val standardProgramModifyTextState = _standardProgramModifyTextState.asStateFlow()

    private val _getExpoInformationUiState = MutableStateFlow<GetExpoInformationUiState>(GetExpoInformationUiState.Loading)
    internal val getExpoInformationUiState = _getExpoInformationUiState.asStateFlow()

    private val _getCoordinatesUiState = MutableStateFlow<GetCoordinatesUiState>(GetCoordinatesUiState.Loading)
    internal val getCoordinatesUiState = _getCoordinatesUiState.asStateFlow()

    private val _getCoordinatesToAddressUiState = MutableStateFlow<GetCoordinatesToAddressUiState>(GetCoordinatesToAddressUiState.Loading)
    internal val getCoordinatesToAddressUiState = _getCoordinatesToAddressUiState.asStateFlow()

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

    private val _getStandardProgramListUiState = MutableStateFlow<GetStandardProgramListUiState>(GetStandardProgramListUiState.Loading)
    internal val getStandardProgramListUiState = _getStandardProgramListUiState.asStateFlow()

    private val _getTrainingProgramListUiState = MutableStateFlow<GetTrainingProgramListUiState>(GetTrainingProgramListUiState.Loading)
    internal val getTrainingProgramListUiState = _getTrainingProgramListUiState.asStateFlow()

    internal var modify_title = savedStateHandle.getStateFlow(key = MODIFY_TITLE, initialValue = "")

    internal var started_date = savedStateHandle.getStateFlow(key = STARTED_DATE, initialValue = "")

    internal var ended_date = savedStateHandle.getStateFlow(key = ENDED_DATE, initialValue = "")

    internal var introduce_title = savedStateHandle.getStateFlow(key = INTRODUCE_TITLE, initialValue = "")

    internal var address = savedStateHandle.getStateFlow(key = ADDRESS, initialValue = "")

    internal var cover_image = savedStateHandle.getStateFlow(key = COVER_IMAGE, initialValue = "")

    internal var location = savedStateHandle.getStateFlow(key = LOCATION, initialValue = "")

    internal var coordinateX = savedStateHandle.getStateFlow(key = COORDINATEX, initialValue = "")

    internal var coordinateY = savedStateHandle.getStateFlow(key = COORDINATEY, initialValue = "")

    internal var searched_location = savedStateHandle.getStateFlow(key = SEARCHED_LOCATION, initialValue = "")

    internal var searched_coordinateX = savedStateHandle.getStateFlow(key = SEARCHED_COORDINATEX, initialValue = "")

    internal var searched_coordinateY = savedStateHandle.getStateFlow(key = SEARCHED_COORDINATEY, initialValue = "")

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

    val coverImage:StateFlow<String> = getExpoInformationUiState
        .map { state ->
            when(state) {
                is GetExpoInformationUiState.Success -> {
                    state.data.coverImage ?: ""
                }
                else -> ""
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

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
                            onStartedDateChange(it.startedDay.formatNoneHyphenServerDate())
                            onEndedDateChange(it.finishedDay.formatNoneHyphenServerDate())
                            onIntroduceTitleChange(it.description)
                            if (searched_location.value.isNotBlank()) {
                                onLocationChange(searched_location.value)
                                onCoordinateChange(x = searched_coordinateX.value, y = searched_coordinateX.value)
                                convertXYToJibun(x = searched_coordinateX.value, y = searched_coordinateX.value)
                            } else {
                                onLocationChange(it.location)
                                onCoordinateChange(x = it.x, y = it.y)
                                convertXYToJibun(x = it.x, y = it.y)
                            }
                            onCoverImageChange(it.coverImage)
                        }
                    }
                    is Result.Error -> _getExpoInformationUiState.value = GetExpoInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun registerExpoInformation(body: ExpoAllRequestParam) =
        viewModelScope.launch {
            _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
            registerExpoInformationUseCase(
                body = body.copy(
                    startedDay = body.startedDay.autoFormatToDateTime(),
                    finishedDay = body.finishedDay.autoFormatToDateTime(),
                    addStandardProRequestDto = body.addStandardProRequestDto.map { list ->
                        list.copy(
                            startedAt = list.startedAt.autoFormatToDateTime(),
                            endedAt = list.endedAt.autoFormatToDateTime(),
                        )
                    },
                    addTrainingProRequestDto = body.addTrainingProRequestDto.map { list ->
                        list.copy(
                            startedAt = list.startedAt.autoFormatToDateTime(),
                            endedAt = list.endedAt.autoFormatToDateTime(),
                        )
                    }
                ),
            )
                .asResult() // TODO: 컨벤션 확인
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
                        is Result.Success -> _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Success(result.data)
                        is Result.Error -> _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Error(result.exception)
                    }
                }
        }

    internal fun initRegisterExpo() {
        _imageUpLoadUiState.value = ImageUpLoadUiState.Loading
        _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Loading
        _getCoordinatesUiState.value = GetCoordinatesUiState.Loading
        _getAddressUiState.value = GetAddressUiState.Loading
    }

    internal fun modifyExpoInformation(
        expoId: String,
        body: ExpoModifyRequestParam
    ) = viewModelScope.launch {
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
        modifyExpoInformationUseCase(
            expoId = expoId,
            body = body.copy(
                startedDay = body.startedDay.autoFormatToDateTime(),
                finishedDay = body.finishedDay.autoFormatToDateTime(),
                updateStandardProRequestDto = body.updateStandardProRequestDto.map { list ->
                    list.copy(
                        startedAt = list.startedAt.autoFormatToDateTime(),
                        endedAt = list.endedAt.autoFormatToDateTime(),
                    )
                },
                updateTrainingProRequestDto = body.updateTrainingProRequestDto.map { list ->
                    list.copy(
                        startedAt = list.startedAt.autoFormatToDateTime(),
                        endedAt = list.endedAt.autoFormatToDateTime(),
                    )
                }
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
        _imageUpLoadUiState.value = ImageUpLoadUiState.Loading
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
    }

    internal fun resetExpoInformation() {
            onIntroduceTitleChange("")
            onStartedDateChange("")
            onEndedDateChange("")
            onAddressChange("")
            onLocationChange("")
            onCoverImageChange(null)
            onModifyTitleChange("")
            onStartedChange("")
            onEndedChange("")
            _trainingProgramTextState.value = emptyList()
            _standardProgramTextState.value = emptyList()
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

    internal fun resetDeleteExpoInformationState() {
        _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Loading
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

    internal fun getStandardProgramList(expoId: String) = viewModelScope.launch {
        _getStandardProgramListUiState.value = GetStandardProgramListUiState.Loading
        standardProgramListUseCase(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getStandardProgramListUiState.value = GetStandardProgramListUiState.Loading
                    is Result.Success -> {
                        _getStandardProgramListUiState.value = GetStandardProgramListUiState.Success(result.data)

                        _standardProgramModifyTextState.value = result.data.map { program ->
                            StandardProIdRequestParam(
                                id = program.id,
                                title = program.title,
                                startedAt = program.startedAt.formatNoneHyphenServerDate(),
                                endedAt = program.endedAt.formatNoneHyphenServerDate()
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

                        _trainingProgramModifyTextState.value = result.data.map { program ->
                            TrainingProIdRequestParam(
                                id = program.id,
                                title = program.title,
                                startedAt = program.startedAt.formatNoneHyphenServerDate(),
                                endedAt = program.endedAt.formatNoneHyphenServerDate(),
                                category = program.category
                            )
                        }
                    }
                    is Result.Error -> _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Error(result.exception)
                }
            }
    }

    internal fun initSearchedData() {
        onSearchedLocationChange("")
        onSearchedCoordinateChange("", "")
    }

    internal fun initSearchedUiState() {
        _getAddressUiState.value = GetAddressUiState.Loading
        _getCoordinatesUiState.value = GetCoordinatesUiState.Loading
    }

    internal fun initializeWithSearchedData() {
        onLocationChange(searched_location.value)
        onCoordinateChange(
            searched_coordinateX.value,
            searched_coordinateY.value
        )
        initSearchedData()
    }

    internal fun searchLocation(searchText: String) =
        viewModelScope.launch {
            onSearchedCoordinateChange(x = "", y = "")
            getAddressUseCase(searchText = searchText)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _getAddressUiState.value = GetAddressUiState.Loading
                        is Result.Success -> {
                            if (result.data.isNotEmpty()) {
                                _getAddressUiState.value = GetAddressUiState.Success(result.data)
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
        _getAddressUiState.value = GetAddressUiState.Loading
        getCoordinatesUseCase(address = searchText)
            .asResult()
            .collectLatest { result ->
                when(result){
                    is Result.Loading -> _getCoordinatesUiState.value = GetCoordinatesUiState.Loading
                    is Result.Success -> if (result.data.addressName == "Unknown") {
                        _getCoordinatesUiState.value = GetCoordinatesUiState.Error(NoResponseException())
                    } else {
                        onSearchedCoordinateChange(
                            x = result.data.x.toDoubleOrNull()?.let { "%.6f".format(it) } ?: "0.000000",
                            y = result.data.y.toDoubleOrNull()?.let { "%.6f".format(it) } ?: "0.000000"
                        )
                        _getCoordinatesUiState.value = GetCoordinatesUiState.Success(result.data)
                    }
                    is Result.Error -> _getCoordinatesUiState.value = GetCoordinatesUiState.Error(result.exception)
                }
            }
    }

    private fun convertXYToJibun(x: String, y: String) = viewModelScope.launch {
        getCoordinatesToAddressUseCase(x = x, y = y)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Loading
                    is Result.Success -> if (result.data.addressName == "Unknown") {
                        _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Error(NoResponseException())
                    } else {
                        _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Success(result.data)
                    }
                    is Result.Error -> _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Error(result.exception)
                }
            }
    }

    internal fun updateTrainingProgramModifyText(index: Int, updateItem: TrainingProIdRequestParam) {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.toMutableList().apply {
            set(index, updateItem)
        }
    }

    internal fun updateStandardProgramModifyText(index: Int, updateItem: StandardProIdRequestParam) {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.toMutableList().apply {
            set(index, updateItem)
        }
    }

    internal fun addTrainingProgramModifyText() {
        _trainingProgramModifyTextState.value += TrainingProIdRequestParam(
            id = 0,
            title = "",
            startedAt = "",
            endedAt = "",
            category = ""
        )
    }

    internal fun addStandardProgramModifyText() {
        _standardProgramModifyTextState.value += StandardProIdRequestParam(
            id = 0,
            title = "",
            startedAt = "",
            endedAt = ""
        )
    }

    internal fun removeTrainingProgramModifyText(index: Int) {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.toMutableList().apply {
            removeAt(index)
        }
    }

    internal fun removeStandardProgramModifyText(index: Int) {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.toMutableList().apply {
            removeAt(index)
        }
    }

    internal fun updateExistingTrainingProgramModify(index: Int, updatedItem: TrainingProIdRequestParam) {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.toMutableList().apply {
            this[index] = updatedItem
        }
    }

    internal fun updateExistingStandardProgramModify(index: Int, updatedItem: StandardProIdRequestParam) {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.toMutableList().apply {
            this[index] = updatedItem
        }
    }

    internal fun updateTrainingProgramText(index: Int, updateItem: TrainingProRequestParam) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            set(index, updateItem)
        }
    }

    internal fun addTrainingProgramText() {
        _trainingProgramTextState.value += TrainingProRequestParam(
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

    internal fun updateStandardProgramText(index: Int, updateItem: StandardProRequestParam) {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            set(index, updateItem)
        }
    }

    internal fun addStandardProgramText() {
        _standardProgramTextState.value += StandardProRequestParam(
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

    internal fun updateExistingTrainingProgram(index: Int, updatedItem: TrainingProRequestParam) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            this[index] = updatedItem
        }
    }

    internal fun updateExistingStandardProgram(index: Int, updatedItem: StandardProRequestParam) {
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

    internal fun onCoverImageChange(value: String?) {
        savedStateHandle[COVER_IMAGE] = value
    }

    internal fun onStartedChange(value: String) {
        savedStateHandle[STARTED] = value
    }

    internal fun onEndedChange(value: String) {
        savedStateHandle[ENDED] = value
    }

    internal fun onLocationChange(value: String) {
        savedStateHandle[LOCATION] = value
    }

    internal fun onCoordinateChange(x: String, y: String) {
        savedStateHandle[COORDINATEX] = x
        savedStateHandle[COORDINATEY] = y
    }

    internal fun onSearchedLocationChange(value: String) {
        savedStateHandle[SEARCHED_LOCATION] = value
    }

    internal fun onSearchedCoordinateChange(x: String, y: String) {
        savedStateHandle[SEARCHED_COORDINATEX] = x
        savedStateHandle[SEARCHED_COORDINATEY] = y
    }
}