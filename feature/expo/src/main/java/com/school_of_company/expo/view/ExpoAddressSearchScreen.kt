package com.school_of_company.expo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.ExpoStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.textfield.ExpoSearchIconTextField
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.AddressSearchResultItem
import com.school_of_company.expo.view.component.HomeKakaoMap
import com.school_of_company.expo.viewmodel.ExpoViewModel
import com.school_of_company.expo.viewmodel.uistate.GetAddressUiState
import com.school_of_company.expo.viewmodel.uistate.GetCoordinatesUiState
import com.school_of_company.model.model.juso.JusoModel

@Composable
internal fun ExpoAddressSearchRoute(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: ExpoViewModel = hiltViewModel(),
) {
    val getAddressUiState by viewModel.getAddressUiState.collectAsStateWithLifecycle()
    val getCoordinatesUiState by viewModel.getCoordinatesUiState.collectAsStateWithLifecycle()

    val addressList by viewModel.addressList.collectAsStateWithLifecycle()
    val location by viewModel.location.collectAsStateWithLifecycle()
    val coordinateX by viewModel.coordinateX.collectAsStateWithLifecycle()
    val coordinateY by viewModel.coordinateY.collectAsStateWithLifecycle()

    LaunchedEffect(getCoordinatesUiState) {
        when (getCoordinatesUiState) {
            is GetCoordinatesUiState.Error -> onErrorToast(
                (getCoordinatesUiState as GetCoordinatesUiState.Error).exception,
                R.string.get_address_convert_fail
            )

            else -> Unit
        }
    }

    LaunchedEffect(getAddressUiState) {
        when (getAddressUiState) {
            is GetAddressUiState.Error -> onErrorToast(
                (getAddressUiState as GetAddressUiState.Error).exception,
                R.string.get_address_fail
            )

            else -> Unit
        }
    }

    ExpoAddressSearchScreen(
        modifier = modifier,
        location = location,
        coordinateX = coordinateX,
        coordinateY = coordinateY,
        addressList = addressList,
        popUpBackStack = popUpBackStack,
        onLocationSearch = { viewModel.searchLocation(location) },
        onLocationChange = viewModel::onLocationChange,
        onAddressItemClick = { item ->
            viewModel.convertJibunToXY(item)
            viewModel.onLocationChange(item)
        },
    )
}

@Composable
private fun ExpoAddressSearchScreen(
    modifier: Modifier = Modifier,
    location: String,
    coordinateX: String,
    coordinateY: String,
    addressList: List<JusoModel>,
    popUpBackStack: () -> Unit,
    onLocationSearch: () -> Unit,
    onLocationChange: (String) -> Unit,
    onAddressItemClick: (String) -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(16.dp),
        ) {
            ExpoTopBar(
                modifier = Modifier.padding(vertical = 16.dp),
                startIcon = {
                    LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable(onClick = popUpBackStack)
                    )
                },
                betweenText = "장소",
            )

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "위치 검색",
                    style = typography.bodyRegular2,
                    color = colors.gray600,
                    fontWeight = FontWeight.W600,
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExpoSearchIconTextField(
                    placeholder = "검색할 주소를 알려주세요.",
                    isDisabled = false,
                    onValueChange = onLocationChange,
                    onButtonClicked = onLocationSearch,
                    value = location,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.fillMaxWidth()) {

                LazyColumn(modifier.zIndex(1f)) {
                    itemsIndexed(addressList) { index, address ->
                        AddressSearchResultItem(
                            result = address,
                            onClick = onAddressItemClick
                        )

                        if (index < addressList.lastIndex) {
                            HorizontalDivider(
                                color = colors.gray300,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }
                    }
                }

                if (
                    coordinateX.isNotBlank()
                    && coordinateY.isNotBlank()
                ) {
                    HomeKakaoMap(
                        locationX = coordinateX.toDouble(),
                        locationY = coordinateY.toDouble(),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ExpoStateButton(
                text = "생성하기",
                state = if (
                    location.isNotEmpty() &&
                    coordinateX.isNotBlank() &&
                    coordinateY.isNotBlank()
                ) {
                    ButtonState.Enable
                } else {
                    ButtonState.Disable
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                onClick = popUpBackStack
            )
        }
    }
}