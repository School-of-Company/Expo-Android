package com.school_of_company.ui.preview

import androidx.compose.ui.tooling.preview.Preview

/**
 * 다양한 기기 구성에 대한 Compose UI 프리뷰를 생성합니다.
 *
 * 이 어노테이션을 사용하면 여러 기기 유형(폰, 폴더블, 태블릿)에 대한
 * UI 프리뷰를 한 번에 생성할 수 있습니다.
 *
 * 사용 예:
 * ```
 * @ExpoPreviews
 * @Composable
 * fun MyComposablePreview() {
 *     MyComposable()
 * }
 * ```
 */
@Preview(name = "phone_1", device = "spec:shape=Normal,width=360,height=880,unit=dp,dpi=480")
@Preview(name = "phone_2", device = "spec:shape=Normal,width=412,height=732,unit=dp,dpi=420")
@Preview(name = "s23", device = "spec:shape=Normal,width=360,height=780,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
annotation class ExpoPreviews