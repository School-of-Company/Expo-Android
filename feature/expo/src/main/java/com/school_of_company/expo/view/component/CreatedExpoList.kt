import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.expo.view.component.CreatedExpoListItem
import com.school_of_company.model.entity.expo.ExpoListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun CreatedExpoList(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    selectedIndex: Int,
    expoList: ImmutableList<ExpoListResponseEntity>,
    onItemClick: (Boolean, Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(expoList) { index, item ->
            CreatedExpoListItem(
                modifier = Modifier.fillMaxWidth(),
                scrollState = scrollState,
                selectedIndex = selectedIndex,
                item = item,
                index = index,
                onClick = { onItemClick(selectedIndex == index, index) }
            )
        }
    }
}

@Preview
@Composable
fun CreatedExpoListPreview() {
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
        scrollState = ScrollState(1)
    )
}