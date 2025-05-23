package com.school_of_company.expo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.exception.NoResponseException
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.data.repository.expo.ExpoRepository
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.data.repository.juso.AddressRepository
import com.school_of_company.data.repository.kakao.KakaoRepository
import com.school_of_company.data.repository.standard.StandardRepository
import com.school_of_company.data.repository.training.TrainingRepository
import com.school_of_company.expo.enum.CurrentScreen
import com.school_of_company.expo.enum.FilterOptionEnum
import com.school_of_company.expo.enum.TrainingCategory
import com.school_of_company.expo.util.getMultipartFile
import com.school_of_company.expo.viewmodel.uistate.CheckExpoSurveyDynamicFormEnableUiState
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
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

@HiltViewModel
internal class ExpoViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository,
    private val addressRepository: AddressRepository,
    private val expoRepository: ExpoRepository,
    private val imageRepository: ImageRepository,
    private val standardRepository: StandardRepository,
    private val trainingRepository: TrainingRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val MODIFY_TITLE = "modify_title"
        private const val STARTED_DATE = "started_date"
        private const val ENDED_DATE = "ended_date"
        private const val INTRODUCE_TITLE = "introduce_title"
        private const val COORDINATEX = "coordinatesx"
        private const val COORDINATEY = "coordinatesy"
        private const val COVER_IMAGE = "cover_image"
        private const val STARTED = "started"
        private const val ENDED = "ended"
        private const val MODIFY_ADDRESS = "modify_address"
        private const val MODIFY_LOCATION = "modify_location"
        private const val CREATE_ADDRESS = "create_address"
        private const val CREATE_LOCATION = "create_location"
        private const val CURRENT_SCREEN = "current_screen"
        private const val IMAGE_URL = "image_url"
    }

    internal var currentScreen = savedStateHandle.getStateFlow(CURRENT_SCREEN, CurrentScreen.NONE.name)

    internal var modify_address = savedStateHandle.getStateFlow(key = MODIFY_ADDRESS, initialValue = "")
    internal var modify_location = savedStateHandle.getStateFlow(key = MODIFY_LOCATION, initialValue = "")

    internal var create_address = savedStateHandle.getStateFlow(key = CREATE_ADDRESS, initialValue = "")
    internal var create_location = savedStateHandle.getStateFlow(key = CREATE_LOCATION, initialValue = "")

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _trainingProgramTextState = MutableStateFlow<PersistentList<TrainingProRequestParam>>(persistentListOf())
    internal val trainingProgramTextState = _trainingProgramTextState.asStateFlow()

    private val _standardProgramTextState = MutableStateFlow<PersistentList<StandardProRequestParam>>(persistentListOf())
    internal val standardProgramTextState = _standardProgramTextState.asStateFlow()

    private val _trainingProgramModifyTextState = MutableStateFlow<PersistentList<TrainingProIdRequestParam>>(persistentListOf())
    internal val trainingProgramModifyTextState = _trainingProgramModifyTextState.asStateFlow()

    private val _standardProgramModifyTextState = MutableStateFlow<PersistentList<StandardProIdRequestParam>>(persistentListOf())
    internal val standardProgramModifyTextState = _standardProgramModifyTextState.asStateFlow()

    private val _getExpoInformationUiState = MutableStateFlow<GetExpoInformationUiState>(GetExpoInformationUiState.Loading)
    internal val getExpoInformationUiState = _getExpoInformationUiState.asStateFlow()

    private val _getCoordinatesUiState = MutableStateFlow<GetCoordinatesUiState>(GetCoordinatesUiState.Loading)
    internal val getCoordinatesUiState = _getCoordinatesUiState.asStateFlow()

    private val _getCoordinatesToAddressUiState = MutableStateFlow<GetCoordinatesToAddressUiState>(GetCoordinatesToAddressUiState.Loading)
    internal val getCoordinatesToAddressUiState = _getCoordinatesToAddressUiState.asStateFlow()

    private val _getAddressUiState = MutableStateFlow<GetAddressUiState>(GetAddressUiState.Loading)
    internal val getAddressUiState = _getAddressUiState.asStateFlow()

    private val _registerExpoInformationUiState = MutableStateFlow<RegisterExpoInformationUiState>(RegisterExpoInformationUiState.Initial)
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

    private val _checkExpoSurveyDynamicFormEnableUiState = MutableStateFlow<CheckExpoSurveyDynamicFormEnableUiState>(CheckExpoSurveyDynamicFormEnableUiState.Loading)

    private val _allExpoList = MutableStateFlow<List<ExpoListResponseEntity>>(emptyList())

    private val _selectedFilter = MutableStateFlow<FilterOptionEnum?>(null)
    internal val selectedFilter: StateFlow<FilterOptionEnum?> = _selectedFilter.asStateFlow()

    internal var modify_title = savedStateHandle.getStateFlow(key = MODIFY_TITLE, initialValue = "")

    internal var started_date = savedStateHandle.getStateFlow(key = STARTED_DATE, initialValue = "")

    internal var ended_date = savedStateHandle.getStateFlow(key = ENDED_DATE, initialValue = "")

    internal var introduce_title = savedStateHandle.getStateFlow(key = INTRODUCE_TITLE, initialValue = "")

    internal var cover_image = savedStateHandle.getStateFlow(key = COVER_IMAGE, initialValue = "")

    internal var coordinateX = savedStateHandle.getStateFlow(key = COORDINATEX, initialValue = "")

    internal var coordinateY = savedStateHandle.getStateFlow(key = COORDINATEY, initialValue = "")

    private var imageUrl = savedStateHandle.getStateFlow(key = IMAGE_URL, initialValue = "")

    val expoListSize: StateFlow<Int> = getExpoListUiState
        .map { state ->
            when (state) {
                is GetExpoListUiState.Success -> state.data.size
                else -> 0
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val addressList: StateFlow<PersistentList<JusoModel>> = getAddressUiState
        .map { state ->
            when (state) {
                is GetAddressUiState.Success -> state.data.toPersistentList()
                else -> persistentListOf()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), persistentListOf())

    internal val address: StateFlow<String> = combine(
        currentScreen,
        modify_address,
        create_address
    ) { screen, modifyAddress, createAddress ->
        when (CurrentScreen.valueOf(screen)) {
            CurrentScreen.MODIFY -> modifyAddress
            CurrentScreen.CREATE -> createAddress
            else -> ""
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")


    internal val location: StateFlow<String> = combine(
        currentScreen,
        modify_location,
        create_location
    ) { screen, modifyLocation, createLocation ->
        when (CurrentScreen.valueOf(screen)) {
            CurrentScreen.MODIFY -> modifyLocation
            CurrentScreen.CREATE -> createLocation
            else -> ""
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    internal fun getExpoInformation(expoId: String) = viewModelScope.launch {
        expoRepository.getExpoInformation(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoInformationUiState.value = GetExpoInformationUiState.Loading
                    is Result.Success -> {
                        _getExpoInformationUiState.value = GetExpoInformationUiState.Success(result.data)

                        result.data.let {
                            setImageUrl(it.coverImage ?: "")
                            onModifyTitleChange(it.title)
                            onStartedDateChange(it.startedDay.formatNoneHyphenServerDate())
                            onEndedDateChange(it.finishedDay.formatNoneHyphenServerDate())
                            onIntroduceTitleChange(it.description)
                            if (address.value.isNotBlank()) {
                                setSearchedData()
                                onCoordinateChange(x = coordinateX.value, y = coordinateY.value)
                                convertXYToJibun(x = coordinateX.value, y = coordinateY.value)
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

    internal fun registerExpoInformation() =
        viewModelScope.launch {
            expoRepository.registerExpoInformation(
                body = ExpoAllRequestParam(
                    title = modify_title.value,
                    startedDay = started_date.value.autoFormatToDateTime(),
                    finishedDay = ended_date.value.autoFormatToDateTime(),
                    description = introduce_title.value,
                    location = location.value,
                    coverImage = (imageUpLoadUiState.value as ImageUpLoadUiState.Success).data.imageURL,
                    x = coordinateX.value,
                    y = coordinateY.value,
                    addStandardProRequestDto = standardProgramTextState.value.map { list ->
                            list.copy(
                                startedAt = list.startedAt.autoFormatToDateTime(),
                                endedAt = list.endedAt.autoFormatToDateTime(),
                            )
                        },
                    addTrainingProRequestDto = trainingProgramTextState.value.map { list ->
                        list.copy(
                            startedAt = list.startedAt.autoFormatToDateTime(),
                            endedAt = list.endedAt.autoFormatToDateTime(),
                        )
                    }

                )
            ).asResult()
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
        _registerExpoInformationUiState.value = RegisterExpoInformationUiState.Initial
        _getCoordinatesUiState.value = GetCoordinatesUiState.Loading
        _getAddressUiState.value = GetAddressUiState.Loading
    }

    internal fun modifyExpoInformation(
        expoId: String,
    ) = viewModelScope.launch {
        val imageUrlToUse = when (val state = imageUpLoadUiState.value) {
            is ImageUpLoadUiState.Success -> state.data.imageURL
            else -> imageUrl.value
        }
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
        expoRepository.modifyExpoInformation(
            expoId = expoId,
            body = ExpoModifyRequestParam(
                title = modify_title.value,
                startedDay = started_date.value.autoFormatToDateTime(),
                finishedDay = ended_date.value.autoFormatToDateTime(),
                description = introduce_title.value,
                location = location.value,
                coverImage = imageUrlToUse,
                x = coordinateX.value,
                y = coordinateY.value,
                updateStandardProRequestDto = standardProgramModifyTextState.value.map { list ->
                    list.copy(
                        startedAt = list.startedAt.autoFormatToDateTime(),
                        endedAt = list.endedAt.autoFormatToDateTime(),
                    )
                },
                updateTrainingProRequestDto = trainingProgramModifyTextState.value.map { list ->
                    list.copy(
                        startedAt = list.startedAt.autoFormatToDateTime(),
                        endedAt = list.endedAt.autoFormatToDateTime(),
                    )
                }
            )
        )
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
                    is Result.Success -> _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Success
                    is Result.Error -> _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun resetExpoInformation() {
        _imageUpLoadUiState.value = ImageUpLoadUiState.Loading
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading

        onCoordinateChange("", "")
        onSearchedCoordinateChange("", "")
        onIntroduceTitleChange("")
        onStartedDateChange("")
        onEndedDateChange("")
        onLocationChange("")
        onAddressChange("")
        onCoverImageChange(null)
        onModifyTitleChange("")
        onStartedChange("")
        onEndedChange("")
        _trainingProgramTextState.value = persistentListOf()
        _standardProgramTextState.value = persistentListOf()
    }

    internal fun deleteExpoInformation(expoId: String) = viewModelScope.launch {
        _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Loading
        expoRepository.deleteExpoInformation(expoId = expoId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Loading
                    is Result.Success -> _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Success
                    is Result.Error -> _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Error(result.exception)
                }
            }
    }

    internal fun resetDeleteExpoInformationState() {
        _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Loading
    }

    internal fun getExpoList() = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        expoRepository.getExpoList()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getExpoListUiState.value = GetExpoListUiState.Loading
                    is Result.Success -> {
                        _allExpoList.value = result.data

                        if (result.data.isEmpty()) {
                            _getExpoListUiState.value = GetExpoListUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            if (_selectedFilter.value != null) {
                                checkExpoSurveyDynamicFormEnable()
                                _swipeRefreshLoading.value = false
                            } else {
                                _getExpoListUiState.value = GetExpoListUiState.Success(result.data)
                                _swipeRefreshLoading.value = false
                            }
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

        imageRepository.imageUpLoad(multipartFile)
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
        standardRepository.standardProgramList(expoId = expoId)
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
                        }.toPersistentList()
                    }
                    is Result.Error -> _getStandardProgramListUiState.value = GetStandardProgramListUiState.Error(result.exception)
                }
            }
    }


    internal fun getTrainingProgramList(expoId: String) = viewModelScope.launch {
        _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Loading
        trainingRepository.trainingProgramList(expoId = expoId)
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
                        }.toPersistentList()
                    }
                    is Result.Error -> _getTrainingProgramListUiState.value = GetTrainingProgramListUiState.Error(result.exception)
                }
            }
    }

    internal fun initSearchedUiState() {
        _getAddressUiState.value = GetAddressUiState.Loading
        _getCoordinatesUiState.value = GetCoordinatesUiState.Loading
    }

    internal fun searchLocation(searchText: String) =
        viewModelScope.launch {
            onSearchedCoordinateChange(x = "", y = "")
            addressRepository.getAddress(keyword = searchText)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _getAddressUiState.value = GetAddressUiState.Loading
                        is Result.Success -> {
                            if (result.data.isNotEmpty()) {
                                _getAddressUiState.value = GetAddressUiState.Success(result.data.toPersistentList())
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
        kakaoRepository.getCoordinates(address = searchText)
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
                        onAddressChange(result.data.addressName)

                        _getCoordinatesUiState.value = GetCoordinatesUiState.Success(result.data)
                    }
                    is Result.Error -> _getCoordinatesUiState.value = GetCoordinatesUiState.Error(result.exception)
                }
            }
    }

    private fun convertXYToJibun(x: String, y: String) = viewModelScope.launch {
        kakaoRepository.getAddress(x = x, y = y)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Loading
                    is Result.Success -> if (result.data.addressName == "Unknown") {
                        _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Error(NoResponseException())
                    } else {
                        _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Success(result.data)
                        onAddressChange(result.data.addressName)
                    }
                    is Result.Error -> _getCoordinatesToAddressUiState.value = GetCoordinatesToAddressUiState.Error(result.exception)
                }
            }
    }

    private fun checkExpoSurveyDynamicFormEnable() = viewModelScope.launch {
        val currentFilter = _selectedFilter.value ?: return@launch
        _checkExpoSurveyDynamicFormEnableUiState.value = CheckExpoSurveyDynamicFormEnableUiState.Loading
        expoRepository.checkExpoSurveyDynamicFormEnable()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _checkExpoSurveyDynamicFormEnableUiState.value = CheckExpoSurveyDynamicFormEnableUiState.Loading
                    is Result.Success -> {
                        val filterExpoId = result.data.expoValid.filter { expo ->
                            when (currentFilter) {
                                FilterOptionEnum.TRAINING_FORM_TRUE -> expo.traineeFormCreatedStatus
                                FilterOptionEnum.TRAINING_FORM_FALSE -> !expo.traineeFormCreatedStatus
                                FilterOptionEnum.STUDENT_FORM_TRUE -> expo.standardFormCreatedStatus
                                FilterOptionEnum.STUDENT_FORM_FALSE -> !expo.standardFormCreatedStatus
                            }
                        }.map { it.expoId }

                        val filteredData = _allExpoList.value.filter { it.id in filterExpoId }

                        _getExpoListUiState.value = if (filteredData.isEmpty()) {
                            GetExpoListUiState.Empty
                        } else {
                            GetExpoListUiState.Success(filteredData)
                        }
                    }
                    is Result.Error -> _checkExpoSurveyDynamicFormEnableUiState.value = CheckExpoSurveyDynamicFormEnableUiState.Error(result.exception)
                }
            }
    }

    internal fun onFilterSelected(filter: FilterOptionEnum) {
        _selectedFilter.value = if (_selectedFilter.value == filter) null else filter

        if (_selectedFilter.value != null) {
            checkExpoSurveyDynamicFormEnable()
        } else {
            _getExpoListUiState.value = GetExpoListUiState.Success(_allExpoList.value)
        }
    }

    internal fun updateTrainingProgramModifyText(index: Int, updateItem: TrainingProIdRequestParam) {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.set(index, updateItem)
    }

    internal fun updateStandardProgramModifyText(index: Int, updateItem: StandardProIdRequestParam) {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.set(index, updateItem)
    }

    internal fun addTrainingProgramModifyText() {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.add(
            TrainingProIdRequestParam(
                id = 0,
                title = "",
                startedAt = "",
                endedAt = "",
                category = ""
            )
        )
    }

    internal fun addStandardProgramModifyText() {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.add(
            StandardProIdRequestParam(
                id = 0,
                title = "",
                startedAt = "",
                endedAt = ""
            )
        )
    }

    private fun setSearchedData() {
        if (address.value.isNotEmpty()) {
            onCoordinateChange(coordinateX.value, coordinateY.value)
            onAddressChange(address.value)
        }
    }

    internal fun removeTrainingProgramModifyText(index: Int) {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.removeAt(index)
    }

    internal fun removeStandardProgramModifyText(index: Int) {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.removeAt(index)
    }

    internal fun updateExistingTrainingProgramModify(index: Int, updatedItem: TrainingProIdRequestParam) {
        _trainingProgramModifyTextState.value = _trainingProgramModifyTextState.value.set(index, updatedItem)
    }

    internal fun updateExistingStandardProgramModify(index: Int, updatedItem: StandardProIdRequestParam) {
        _standardProgramModifyTextState.value = _standardProgramModifyTextState.value.set(index, updatedItem)
    }


    internal fun updateTrainingProgramText(index: Int, updateItem: TrainingProRequestParam) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.set(index, updateItem)
    }

    internal fun addTrainingProgramText() {
        _trainingProgramTextState.value = _trainingProgramTextState.value.add(
                TrainingProRequestParam(
                    title = "",
                    startedAt = "",
                    endedAt = "",
                    category = TrainingCategory.CHOICE.name
                )
            )
    }

    internal fun removeTrainingProgramText(index: Int) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.removeAt(index)
    }

    internal fun updateStandardProgramText(index: Int, updateItem: StandardProRequestParam) {
        _standardProgramTextState.value = _standardProgramTextState.value.set(index, updateItem)
    }

    internal fun addStandardProgramText() {
        _standardProgramTextState.value = _standardProgramTextState.value.add(
            StandardProRequestParam(
                title = "",
                startedAt = "",
                endedAt = ""
            )
        )
    }

    internal fun removeStandardProgramText(index: Int) {
        _standardProgramTextState.value = _standardProgramTextState.value.removeAt(index)
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

    internal fun onCoverImageChange(value: String?) {
        savedStateHandle[COVER_IMAGE] = value
    }

    private fun onStartedChange(value: String) {
        savedStateHandle[STARTED] = value
    }

    private fun onEndedChange(value: String) {
        savedStateHandle[ENDED] = value
    }

    private fun onCoordinateChange(x: String, y: String) {
        savedStateHandle[COORDINATEX] = x
        savedStateHandle[COORDINATEY] = y
    }

    private fun onSearchedCoordinateChange(x: String, y: String) {
        savedStateHandle[COORDINATEX] = x
        savedStateHandle[COORDINATEY] = y
    }

    internal fun onAddressChange(value: String) {
        when (CurrentScreen.valueOf(currentScreen.value)) {
            CurrentScreen.MODIFY -> savedStateHandle[MODIFY_ADDRESS] = value
            CurrentScreen.CREATE -> savedStateHandle[CREATE_ADDRESS] = value
            else -> Unit
        }
    }

    internal fun onLocationChange(value: String) {
        when (CurrentScreen.valueOf(currentScreen.value)) {
            CurrentScreen.MODIFY -> savedStateHandle[MODIFY_LOCATION] = value
            CurrentScreen.CREATE -> savedStateHandle[CREATE_LOCATION] = value
            else -> Unit
        }
    }

    internal fun setCurrentScreen(screen: CurrentScreen) {
        savedStateHandle[CURRENT_SCREEN] = screen.name
    }

    private fun setImageUrl(value: String) {
        savedStateHandle[IMAGE_URL] = value
    }
}