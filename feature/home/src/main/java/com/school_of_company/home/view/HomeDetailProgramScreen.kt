package com.school_of_company.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.component.topbar.ExpoTopBar
import com.school_of_company.design_system.icon.LeftArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.home.view.component.ProgramList
import com.school_of_company.home.view.component.ProgramTabRowItem
import com.school_of_company.home.view.component.ProgramTempList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun generateProgramSampleData(): ImmutableList<ProgramTempList> {
    return List(20) {
        ProgramTempList(
            programName = "프로그램 이름",
            check = true,
            must = false
        )
    }.toPersistentList()
}

@Composable
internal fun HomeDetailProgramRoute(
    onBackClick: () -> Unit,
    navigateToProgramDetail: () -> Unit
) {
    HomeDetailProgramScreen(
        programItem = generateProgramSampleData(),
        onBackClick = onBackClick,
        navigateToProgramDetail = navigateToProgramDetail
    )
}

@Composable
internal fun HomeDetailProgramScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(pageCount = { 2 }),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    programItem: ImmutableList<ProgramTempList>,
    onBackClick: () -> Unit,
    navigateToProgramDetail: () -> Unit,
) {
    ExpoAndroidTheme { colors, typography ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(top = 68.dp)
        ) {

            ExpoTopBar(
                startIcon = { LeftArrowIcon(
                        tint = colors.black,
                        modifier = Modifier.expoClickable { onBackClick() }
                    ) },
                betweenText = "프로그램",
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.padding(top = 28.dp))

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = colors.main,
                        modifier = modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    )
                },
                modifier = Modifier.width(230.dp)
            ) {
                persistentListOf(
                    "일반 프로그램",
                    "연수 프로그램"
                ).forEachIndexed { index, title ->
                    ProgramTabRowItem(
                        isCurrentIndex = index == pagerState.currentPage,
                        title = title,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = colors.gray200
                    )
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.weight(0.5f))

                    Text(
                        text = "프로그램",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        modifier = Modifier.weight(2f)
                    )

                    Text(
                        text = "선택",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "필수",
                        style = typography.captionBold1,
                        color = colors.gray600,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> {
                        ProgramList(
                            item = programItem,
                            navigateToProgramDetail = navigateToProgramDetail
                        )
                    }

                    1 -> {
                        ProgramList(
                            item = programItem,
                            navigateToProgramDetail = navigateToProgramDetail
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeDetailProgramScreenPreview() {
    HomeDetailProgramScreen(
        programItem = persistentListOf(
            ProgramTempList(
                programName = "adsfasfas",
                check = true,
                must = true
            ),

            ProgramTempList(
                programName = "adsfasfas",
                check = true,
                must = true
            ),
        ),
        onBackClick = {},
        navigateToProgramDetail = {}
    )
}