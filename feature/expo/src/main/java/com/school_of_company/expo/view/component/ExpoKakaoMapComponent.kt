package com.school_of_company.expo.view.component

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.school_of_company.design_system.R
import com.school_of_company.ui.toast.makeToast

@Composable
internal fun HomeKakaoMap(
    modifier: Modifier = Modifier,
    locationX: Double,
    locationY: Double,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val fixedLatLng = remember {
        // 고정 라벨 위치 (예: 특정 x, y 좌표)
        LatLng.from(
            locationY,
            locationX
        ) // 고정 좌표 설정
    }

    AndroidView(
        modifier = modifier.height(200.dp),
        factory = { _ ->
            mapView.apply {
                mapView.start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() {
                            makeToast(context = context, toastMessage = "지도를 불러오는데 실패했습니다.")
                        }

                        override fun onMapError(exception: Exception?) {
                            makeToast(context = context, toastMessage = "지도를 불러오는 중 알 수 없는 에러가 발생했습니다.")
                        }
                    },

                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {

                            // 카메라를 locationX, locationY로 이동
                            val cameraUpdate =
                                CameraUpdateFactory.newCenterPosition(fixedLatLng)
                            kakaoMap.moveCamera(cameraUpdate)

                            // 라벨 스타일 정의
                            val styles = kakaoMap.labelManager?.addLabelStyles(
                                LabelStyles.from(
                                    LabelStyle.from(R.drawable.map_label)
                                        .setAnchorPoint(0.5f, 1f)
                                )
                            )

                            // 고정된 위치에 라벨 추가
                            kakaoMap.labelManager?.layer?.addLabel(
                                LabelOptions.from("fixedLabel", fixedLatLng) // 고정 좌표로 라벨 추가
                                    .setStyles(styles) // 스타일 적용
                                    .setRank(1)
                            )
                        }

                        override fun getPosition(): LatLng {
                            return fixedLatLng
                        }
                    },
                )
            }
        },
    )
}

@Preview
@Composable
private fun HomeKakaoComponentPreview() {
    HomeKakaoMap(locationX = 35.14308063423194, locationY = 126.80042860412009)
}