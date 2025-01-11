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
import com.school_of_company.ui.toast.makeToast

@Composable
internal fun HomeKakaoMap(
    modifier: Modifier = Modifier,
    locationX: Double,
    locationY: Double,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

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
                            makeToast(context = context, toastMessage = "지도를 불러오는 중 알 수 없는 에러가 발생했습니다.\n onMapError: $exception")
                        }
                    },

                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(kakaoMap: KakaoMap) {
                            val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(locationX, locationY))
                            kakaoMap.moveCamera(cameraUpdate)
                        }

                        override fun getPosition(): LatLng {
                            return LatLng.from(locationX, locationY)
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