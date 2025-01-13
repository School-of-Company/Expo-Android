import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.expo.view.component.CreatedExpoListItem
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CreatedExpoList(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    expoList: ImmutableList<ExpoListResponseEntity>,
    selectedIndex: Int,
    swipeRefreshState: SwipeRefreshState, // 상위에서 전달받은 SwipeRefreshState
    onItemClick: (Boolean, Int) -> Unit,
    onRefresh: () -> Unit, // 새로고침 콜백
) {
    ExpoAndroidTheme { colors, _ ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onRefresh,// 새로고침 시 호출되는 함수
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    contentColor = colors.main
                )
            }
        ) {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(expoList) { index, item ->
                    CreatedExpoListItem(
                        scrollState = scrollState,
                        selectedIndex = selectedIndex,
                        item = item,
                        index = index,
                        onClick = { onItemClick(selectedIndex == index, index) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CreatedExpoListPreview() {
    val swipeRefreshState = remember { SwipeRefreshState(false) }

    CreatedExpoList(
        expoList = persistentListOf(
            ExpoListResponseEntity(
                id = "1",
                title = "2024 AI광주미래교육박람회",
                description = "",
                startedDay = "09.10",
                finishedDay = "09.20",
                coverImage = null
            ),
        ),
        onItemClick = { _, _ -> },
        selectedIndex = 1,
        onRefresh = { /* 새로고침 시 동작할 함수 */ },
        swipeRefreshState = swipeRefreshState,// 상위에서 전달한 상태
        scrollState = ScrollState(1)
        )
}