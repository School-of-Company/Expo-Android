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
@Preview(name = "phone_1", device = "spec:width=360dp,height=880dp,dpi=480")
@Preview(name = "phone_2", device = "spec:width=412dp,height=732dp,dpi=420")
@Preview(name = "s23", device = "spec:width=360dp,height=780dp,dpi=480")
@Preview(name = "foldable", device = "spec:width=673dp,height=841dp,dpi=480")
@Preview(name = "tablet", device = "spec:width=1280dp,height=800dp,dpi=480")
annotation class ExpoPreviews