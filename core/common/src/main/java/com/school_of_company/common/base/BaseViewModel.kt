package com.school_of_company.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 상태(State), 부작용(SideEffect), 그리고 인텐트(Intent)를 관리하는 추상화된 뷰모델 클래스.
 *
 * 이 뷰모델은 MVI 패턴을 따르며, 각 화면 또는 컴포넌트에 필요한 상태와 부작용을 관리할 수 있도록
 * 구조화되어 있습니다. 모든 뷰모델은 이 클래스를 상속받아 특정 상태(S)와 부작용(E), 그리고
 * 인텐트(I)에 대한 구체적인 구현을 제공해야 합니다.
 *
 * @param S 뷰모델에서 관리할 상태를 나타내는 데이터 클래스
 * @param E 뷰모델에서 발생할 수 있는 부작용을 나타내는 시일드 클래스
 * @param I 뷰모델에서 처리할 인텐트(사용자 동작 또는 이벤트)를 나타내는 클래스
 *
 * 예시 인텐트
 * ```
 * sealed class ExampleIntent {
 *     object LoadData : ExampleIntent() // 데이터를 로드하는 Intent
 *     object ClearData : ExampleIntent() // 데이터를 지우는 Intent
 * }
 * ```
 * 인탠트가 특정 함수(state를 변경하는 함수)를 실행 시킨다
 *
 * 예시 State
 * ```
 * // 상태를 나타내는 데이터 클래스
 * data class ExampleState(
 *     val data: String = "",  // 로드된 데이터
 *     val isLoading: Boolean = false  // 데이터 로드 중인지 여부
 * )
 * ```
 * 필수로 state의 기본값을 반환하는 함수를 구현해야한다
 *
 * 예시 사이드 이펙트
 * ```
 * sealed class ExampleSideEffect {
 *     object ShowLoadingError : ExampleSideEffect() // 로드 에러 메시지 출력
 *     object NavigateToDetails : ExampleSideEffect() // 디테일 화면으로 이동
 * }
 * ```
 */
abstract class BaseViewModel<S, E, I>(initialState: S) : ViewModel() {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<E> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    /**
     * Expo에서 [state]의 상태를 업데이트 하기 위해 사용하는 함수
     *
     * @param newState 새로운 상태를 반환하는 함수
     *
     * 함수 내부에서 반환값(마지막 줄)을 [state]로 작성할 수 있는 경우 다음과 같은 방식으로 사용한다.
     * ```
     * internal fun setRecruitmentId(recruitmentId: Long) = setState {
     *     state.value.copy(recruitmentId = recruitmentId)
     * }
     * ```
     * 위 경우에 해당하지 않는다면 다음과 같은 방식으로 사용한다.
     * ```
     * internal fun setEmail(email: String) {
     *     setState {
     *         state.value.copy(
     *             email = email,
     *             showEmailDescription = false,
     *         )
     *     }
     *     setButtonEnabled()
     * }
     * ```
     */
    protected fun setState(newState: () -> S) {
        _state.update { newState() }
    }

    /**
     * Expo에서 [sideEffect]를 발생시키기 위해 사용하는 함수
     *
     * @param sideEffect 방출할 sideEffect
     *
     * [sideEffect]가 예외 처리시 반드시 방출되어야 하고, 예외에 따라 [sideEffect] 클래스만 변경되는 경우라면 다음과 같은 방식으로 사용한다.
     */
    protected fun postSideEffect(sideEffect: E) {
        viewModelScope.launch(Dispatchers.IO) {
            _sideEffect.emit(sideEffect)
        }
    }

    /**
     * 인텐트를 처리하는 추상 함수.
     *
     * 상속받은 뷰모델에서 구체적인 인텐트 처리 로직을 구현해야 합니다. 인텐트는 사용자의 행동
     * 또는 비즈니스 로직에 따른 이벤트를 나타내며, 이를 기반으로 상태 업데이트 또는 부작용을
     * 처리할 수 있습니다.
     *
     * @param intent 처리할 인텐트.
     */
    abstract fun handleIntent(intent: I)
}