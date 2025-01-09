package com.school_of_company.user.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SignUpRequestList(
    modifier: Modifier = Modifier,
    item: ImmutableList<AdminRequestAllowListResponseEntity> = persistentListOf(),
    horizontalScrollState: ScrollState,
) {
    ExpoAndroidTheme { colors, _ ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .padding(start = 16.dp)
        ) {
            itemsIndexed(item) { index, item ->
                SignUpRequestListItem(
                    index = index + 1,
                    data = item,
                    horizontalScrollState = horizontalScrollState
                )
            }
        }
    }
}