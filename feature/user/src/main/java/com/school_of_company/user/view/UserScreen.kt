package com.school_of_company.user.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.modifier.padding.paddingHorizontal
import com.school_of_company.design_system.component.uistate.empty.ShowEmptyState
import com.school_of_company.design_system.component.uistate.error.ShowErrorState
import com.school_of_company.design_system.icon.LogoutIcon
import com.school_of_company.design_system.icon.WarnIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.user.view.component.SignUpRequestList
import com.school_of_company.user.view.component.UserAllowButton
import com.school_of_company.user.view.component.UserBottomSheet
import com.school_of_company.user.view.component.UserDeleteButton
import com.school_of_company.user.view.component.UserDialog
import com.school_of_company.user.viewmodel.UserViewModel
import com.school_of_company.user.viewmodel.uistate.AllowAdminRequestUiState
import com.school_of_company.user.viewmodel.uistate.GetAdminRequestAllowListUiState
import com.school_of_company.user.viewmodel.uistate.LogoutUiState
import com.school_of_company.user.viewmodel.uistate.ServiceWithdrawalUiState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun UserRoute(
    modifier: Modifier = Modifier,
    onMainNavigate: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: UserViewModel = hiltViewModel()
) {
    val swipeRefreshLoading by viewModel.swipeRefreshLoading.collectAsStateWithLifecycle()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = swipeRefreshLoading)

    val getAdminRequestAllowListUiState by viewModel.getAdminRequestAllowListUiState.collectAsStateWithLifecycle()
    val allowAdminRequestUiState by viewModel.allowAdminRequestUiState.collectAsStateWithLifecycle()
    val serviceWithdrawalUiState by viewModel.serviceWithdrawalUiState.collectAsStateWithLifecycle()
    val logoutUiState by viewModel.logoutUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAdminRequestAllowList()
    }

    LaunchedEffect(allowAdminRequestUiState) {
        when (allowAdminRequestUiState) {
            is AllowAdminRequestUiState.Loading -> Unit
            is AllowAdminRequestUiState.Success -> {
                onErrorToast(null, R.string.sign_up_request_allow_success)
                viewModel.getAdminRequestAllowList()
            }

            is AllowAdminRequestUiState.Error -> {
                onErrorToast(null, R.string.sign_up_request_allow_fail)
            }
        }
    }

    LaunchedEffect(serviceWithdrawalUiState) {
        when (serviceWithdrawalUiState) {
            is ServiceWithdrawalUiState.Loading -> Unit
            is ServiceWithdrawalUiState.Success -> {
                onErrorToast(null, R.string.withdraw_success)
                onMainNavigate()
            }
            is ServiceWithdrawalUiState.Error -> {
                onErrorToast(null, R.string.withdraw_fail)
            }
        }
    }

    LaunchedEffect(logoutUiState) {
        when (logoutUiState) {
            is LogoutUiState.Loading -> Unit
            is LogoutUiState.Success -> {
                onErrorToast(null, R.string.logout_success)
                onMainNavigate()
            }
            is LogoutUiState.Error -> {
                onErrorToast(null, R.string.logout_fail)
            }
        }
    }

    UserScreen(
        modifier = modifier,
        getAdminRequestAllowListUiState = getAdminRequestAllowListUiState,
        swipeRefreshState = swipeRefreshState,
        logoutCallBack = viewModel::logout,
        withdrawalCallBack = viewModel::serviceWithdrawal,
        getSignUpRequestList = { viewModel.getAdminRequestAllowList() },
        deleteCallBack = { /* todo : 기능 추가 */ },
        successCallBack = viewModel::allowAdminRequest,
        onErrorToast = onErrorToast
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserScreen(
    modifier: Modifier = Modifier,
    getAdminRequestAllowListUiState: GetAdminRequestAllowListUiState,
    swipeRefreshState: SwipeRefreshState,
    scrollState: ScrollState = rememberScrollState(),
    logoutCallBack: () -> Unit,
    withdrawalCallBack: () -> Unit,
    getSignUpRequestList: () -> Unit,
    deleteCallBack: (Long) -> Unit,
    successCallBack: (Long) -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    val (selectedId, setSelectedId) = rememberSaveable { mutableLongStateOf(0L) }
    val (openBottomSheet, isOpenBottomSheet) = rememberSaveable { mutableStateOf(false) }
    val (openLogoutDialog, isOpenLogoutDialog) = rememberSaveable { mutableStateOf(false) }
    val (openWithdrawDialog, isOpenWithdrawDialog) = rememberSaveable { mutableStateOf(false) }

    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .paddingHorizontal(top = 68.dp)
        ) {

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "이름",
                            style = typography.captionRegular1,
                            color = colors.gray500
                        )

                        Text(
                            text = "이명훈", // todo : Apply UiState
                            style = typography.captionBold2,
                            color = colors.black
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "아이디",
                            style = typography.captionRegular1,
                            color = colors.gray500
                        )

                        Text(
                            text = "audgns3825 ", // todo : Apply UiState
                            style = typography.captionBold2,
                            color = colors.black
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(14.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "이메일",
                            style = typography.captionRegular1,
                            color = colors.gray500
                        )

                        Text(
                            text = "audgns3825@gmail.com", // todo : Apply UiState
                            style = typography.captionBold2,
                            color = colors.black
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                LogoutIcon(
                    tint = colors.error,
                    modifier = Modifier.expoClickable { isOpenBottomSheet(true) }
                )
            }

            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = "회원가입 요청",
                style = typography.bodyBold2,
                color = colors.black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .paddingHorizontal(
                        top = 10.dp,
                        horizontal = 16.dp
                    )
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)) {
                    WarnIcon(
                        tint = colors.gray300,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = "옆으로 넘겨서 확인해보세요.",
                        style = typography.captionRegular2,
                        color = colors.gray300
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Spacer(
                    modifier = Modifier
                        .padding(0.dp)
                        .width(1.dp)
                        .height(14.dp)
                        .background(color = colors.gray100)
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)) {
                    Text(
                        text = "회원가입 요청",
                        style = typography.captionRegular2,
                        color = colors.gray500
                    )

                    when (getAdminRequestAllowListUiState) {
                        is GetAdminRequestAllowListUiState.Loading -> {
                            Text(
                                text = "로딩중..",
                                style = typography.captionRegular2,
                                color = colors.gray200
                            )
                        }

                        is GetAdminRequestAllowListUiState.Success -> {
                            Text(
                                text = "${getAdminRequestAllowListUiState.data.size}명",
                                style = typography.captionRegular2,
                                color = colors.main
                            )
                        }

                        is GetAdminRequestAllowListUiState.Error -> {
                            Text(
                                text = "데이터를 불러올 수 없습니다..",
                                style = typography.captionRegular2,
                                color = colors.gray200
                            )
                        }

                        is GetAdminRequestAllowListUiState.Empty -> {
                            Text(
                                text = "0명",
                                style = typography.captionRegular2,
                                color = colors.main
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = colors.gray200)
            )

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = colors.white
                    )
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .horizontalScroll(scrollState)
                ) {
                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "성명",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(80.dp)
                    )

                    Text(
                        text = "아이디",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(120.dp)
                    )

                    Text(
                        text = "이메일",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(180.dp)
                    )

                    Text(
                        text = "전화번호",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.width(120.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = colors.gray100)
            )

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { getSignUpRequestList() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        contentColor = colors.main
                    )
                }
            ) {
                when (getAdminRequestAllowListUiState) {
                    is GetAdminRequestAllowListUiState.Success -> {
                        Column {
                            SignUpRequestList(
                                item = getAdminRequestAllowListUiState.data.toImmutableList(),
                                horizontalScrollState = scrollState,
                                selectedIndex = selectedId,
                                onClick = { id ->
                                    setSelectedId(if (selectedId == id) 0L else id)
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    16.dp,
                                    Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                UserAllowButton(
                                    enabled = selectedId != 0L,
                                    onClick = {
                                        if (selectedId == 0L) {
                                            onErrorToast(
                                                null,
                                                R.string.check_sign_up_request_list_item
                                            )
                                        } else {
                                            successCallBack(selectedId)
                                        }
                                    },
                                )

                                UserDeleteButton(
                                    enabled = selectedId != 0L,
                                    onClick = { deleteCallBack(selectedId) },
                                )
                            }
                        }
                    }

                    is GetAdminRequestAllowListUiState.Error -> {
                        ShowErrorState(
                            scrollState = scrollState,
                            errorText = "데이터를 불러올 수 없어요!",
                        )
                    }

                    is GetAdminRequestAllowListUiState.Empty -> {
                        ShowEmptyState(
                            scrollState = scrollState,
                            emptyMessage = "회원가입 요청이 없어요.."
                        )
                    }

                    is GetAdminRequestAllowListUiState.Loading -> Unit
                }
            }
        }
    }

    if (openBottomSheet) {
        UserBottomSheet(
            onLogoutClick = {
                isOpenBottomSheet(false)
                isOpenLogoutDialog(true)
            },
            onWithdrawClick = {
                isOpenBottomSheet(false)
                isOpenWithdrawDialog(true)
            },
            onCancelClick = { isOpenBottomSheet(false) }
        )
    }

    if (openLogoutDialog) {
        Dialog(onDismissRequest = { isOpenLogoutDialog(false) }) {

            UserDialog(
                titleText = "정말로 로그아웃 하시겠습니까?",
                contentText = "로그아웃 했을 경우 다시 로그인 해야하는 경우가 발생합니다.",
                buttonText = "로그아웃",
                onConfirmClick = {
                    logoutCallBack()
                    isOpenLogoutDialog(false)
                },
                onCancelClick = { isOpenLogoutDialog(false) }
            )
        }
    }

    if (openWithdrawDialog) {
        Dialog(onDismissRequest = { isOpenWithdrawDialog(false) }) {

            UserDialog(
                titleText = "정말로 탈퇴하기 하시겠습니까?",
                contentText = "탈퇴하기 했을 경우 다시 로그인 해야하는 경우가 발생합니다",
                buttonText = "탈퇴하기",
                onConfirmClick = {
                    withdrawalCallBack()
                    isOpenWithdrawDialog(false)
                },
                onCancelClick = { isOpenWithdrawDialog(false) }
            )
        }
    }
}