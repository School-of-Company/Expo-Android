package com.school_of_company.expo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.common.result.Result
import com.school_of_company.common.result.asResult
import com.school_of_company.domain.usecase.Image.ImageUpLoadUseCase
import com.school_of_company.domain.usecase.expo.DeleteExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.GetExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.GetExpoListUseCase
import com.school_of_company.domain.usecase.expo.ModifyExpoInformationUseCase
import com.school_of_company.domain.usecase.expo.RegisterExpoInformationUseCase
import com.school_of_company.domain.usecase.training.ModifyTrainingProgramUseCase
import com.school_of_company.domain.usecase.training.RegisterTrainingProgramListUseCase
import com.school_of_company.domain.usecase.training.RegisterTrainingProgramUseCase
import com.school_of_company.expo.enum.TrainingCategory
import com.school_of_company.expo.util.getMultipartFile
import com.school_of_company.expo.viewmodel.uistate.DeleteExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.GetExpoListUiState
import com.school_of_company.expo.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.ModifyTrainingProgramUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterExpoInformationUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterTrainingProgramListUiState
import com.school_of_company.expo.viewmodel.uistate.RegisterTrainingProgramUiState
import com.school_of_company.model.model.expo.ExpoRequestAndResponseModel
import com.school_of_company.model.model.training.TrainingDtoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpoViewModel @Inject constructor(
    private val getExpoInformationUseCase: GetExpoInformationUseCase,
    private val registerExpoInformationUseCase: RegisterExpoInformationUseCase,
    private val modifyExpoInformationUseCase: ModifyExpoInformationUseCase,
    private val deleteExpoInformationUseCase: DeleteExpoInformationUseCase,
    private val getExpoListUseCase: GetExpoListUseCase,
    private val imageUpLoadUseCase: ImageUpLoadUseCase,
    private val registerTrainingProgramUseCase: RegisterTrainingProgramUseCase,
    private val registerTrainingProgramListUseCase: RegisterTrainingProgramListUseCase,
    private val modifyTrainingProgramUseCase: ModifyTrainingProgramUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val MODIFY_TITLE = "modify_title"
        private const val STARTED_DATE = "started_date"
        private const val ENDED_DATE = "ended_date"
        private const val INTRODUCE_TITLE = "introduce_title"
        private const val ADDRESS = "address"
        private const val LOCATION = "location"
        private const val COVER_IMAGE = "cover_image"
        private const val STARTED = "started"
        private const val ENDED = "ended"
    }

    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _trainingProgramTextState = MutableStateFlow<List<String>>(emptyList())
    internal val trainingProgramTextState = _trainingProgramTextState.asStateFlow()

    private val _standardProgramTextState = MutableStateFlow<List<String>>(emptyList())
    internal val standardProgramTextState = _standardProgramTextState.asStateFlow()

    private val _categoryState = MutableStateFlow(TrainingCategory.CHOICE)
    val categoryState = _categoryState.asStateFlow()


    private val _getExpoInformationUiState = MutableStateFlow<GetExpoInformationUiState>(GetExpoInformationUiState.Loading)
    internal val getExpoInformationUiState = _getExpoInformationUiState.asStateFlow()

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

    private val _registerTrainingProgramUiState = MutableStateFlow<RegisterTrainingProgramUiState>(RegisterTrainingProgramUiState.Loading)
    internal val registerTrainingProgramUiState = _registerTrainingProgramUiState.asStateFlow()

    private val _registerTrainingProgramListUiState = MutableStateFlow<RegisterTrainingProgramListUiState>(RegisterTrainingProgramListUiState.Loading)
    internal val registerTrainingProgramListUiState = _registerTrainingProgramListUiState.asStateFlow()

    private val _modifyTrainingProgramUiState = MutableStateFlow<ModifyTrainingProgramUiState>(ModifyTrainingProgramUiState.Loading)
    internal val modifyTrainingProgramUiState = _modifyTrainingProgramUiState.asStateFlow()

    internal var modify_title = savedStateHandle.getStateFlow(key = MODIFY_TITLE, initialValue = "")

    internal var started_date = savedStateHandle.getStateFlow(key = STARTED_DATE, initialValue = "")

    internal var ended_date = savedStateHandle.getStateFlow(key = ENDED_DATE, initialValue = "")

    internal var introduce_title = savedStateHandle.getStateFlow(key = INTRODUCE_TITLE, initialValue = "")

    internal var address = savedStateHandle.getStateFlow(key = ADDRESS, initialValue = "")

    internal var location = savedStateHandle.getStateFlow(key = LOCATION, initialValue = "")

    internal var cover_image = savedStateHandle.getStateFlow(key = COVER_IMAGE, initialValue = "")

    internal var started = savedStateHandle.getStateFlow(key = STARTED, initialValue = "")

    internal var ended = savedStateHandle.getStateFlow(key = ENDED, initialValue = "")

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
            registerExpoInformationUseCase(body = body)
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
    }

    internal fun modifyExpoInformation(
        expoId: String,
        body: ExpoRequestAndResponseModel
    ) = viewModelScope.launch {
        _modifyExpoInformationUiState.value = ModifyExpoInformationUiState.Loading
        modifyExpoInformationUseCase(
            expoId = expoId,
            body = body
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
                }
            }
            .onFailure { error ->
                _deleteExpoInformationUiState.value = DeleteExpoInformationUiState.Error(error)
            }
    }

    internal fun expoList() = viewModelScope.launch {
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

    internal fun registerTrainingProgram(
        expoId: String,
        body: TrainingDtoModel
    ) = viewModelScope.launch {
        _registerTrainingProgramUiState.value = RegisterTrainingProgramUiState.Loading
        registerTrainingProgramUseCase(
            expoId = expoId,
            body = body
        )
            .onSuccess {
                it.catch { remoteError ->
                    _registerTrainingProgramUiState.value = RegisterTrainingProgramUiState.Error(remoteError)
                }.collect {
                    _registerTrainingProgramUiState.value = RegisterTrainingProgramUiState.Success
                }
            }
            .onFailure { error ->
                _registerTrainingProgramUiState.value = RegisterTrainingProgramUiState.Error(error)
            }
    }

    internal fun registerTrainingProgramList(
        expoId: String,
        body: List<TrainingDtoModel>
    ) = viewModelScope.launch {
        _registerTrainingProgramListUiState.value = RegisterTrainingProgramListUiState.Loading
        registerTrainingProgramListUseCase(
            expoId = expoId,
            body = body
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
            body = body
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

    internal fun updateTrainingProgramText(index: Int, newText: String) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            set(index, newText)
        }
    }

    internal fun addTrainingProgramText() {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            add("")
        }
    }

    internal fun removeTrainingProgramText(index: Int) {
        _trainingProgramTextState.value = _trainingProgramTextState.value.toMutableList().apply {
            removeAt(index)
        }
    }

    internal fun updateStandardProgramText(index: Int, newText: String) {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            set(index, newText)
        }
    }

    internal fun addStandardProgramText() {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            add("")
        }
    }

    internal fun removeStandardProgramText(index: Int) {
        _standardProgramTextState.value = _standardProgramTextState.value.toMutableList().apply {
            removeAt(index)
        }
    }

    internal fun updateCategory(newCategory: TrainingCategory) {
        _categoryState.value = newCategory
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
}