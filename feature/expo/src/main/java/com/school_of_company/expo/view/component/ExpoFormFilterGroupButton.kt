package com.school_of_company.expo.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class FilterOption(
    val label: String,
    val selected: Boolean,
    val onClick: () -> Unit
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ExpoFormFilterGroupButton(
    modifier: Modifier = Modifier,
    options: PersistentList<FilterOption>
) {
    ExpoAndroidTheme { colors, typography ->
        FlowRow(
            horizontalArrangement = Arrangement.Start,
            maxItemsInEachRow = 2,
            modifier = modifier.fillMaxWidth(),
        ) {
            options.forEach { option ->
                ExpoFormFilterButton(
                    selected = option.selected,
                    onClick = option.onClick,
                    buttonLabel = option.label,
                    modifier = Modifier.padding(
                        end = 5.dp,
                        top = 10.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun ExpoFormFilterButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    buttonLabel: String
) {
    ExpoAndroidTheme { colors, typography ->
        FilterChip(
            selected = selected,
            label = {
                Text(
                    text = buttonLabel,
                    style = typography.bodyRegular2,
                    color = if (selected) colors.white else colors.gray400
                )
            },
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(
                1.dp,
                if (selected) colors.main else colors.gray400
            ),
            colors = FilterChipDefaults.filterChipColors(
                containerColor = colors.white,
                selectedContainerColor = colors.main,
            ),
            onClick = onClick,
            modifier = modifier
                .wrapContentWidth()
                .height(46.dp)
        )
    }
}

@Preview
@Composable
private fun ExpoFormFilterButtonPreview() {
    ExpoFormFilterGroupButton(
        options = persistentListOf(
            FilterOption("현장실습 O", true) {  },
            FilterOption("현장실습 X", false) { },
            FilterOption("학생참여 O", false) {  },
            FilterOption("학생참여 X", false) { },
        )
    )
}