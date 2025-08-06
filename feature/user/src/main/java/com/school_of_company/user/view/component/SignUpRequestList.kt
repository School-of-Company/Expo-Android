package com.school_of_company.user.view.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.model.entity.admin.AdminRequestAllowListResponseEntity
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SignUpRequestList(
    modifier: Modifier = Modifier,
    horizontalScrollState: ScrollState,
    item: ImmutableList<AdminRequestAllowListResponseEntity> = persistentListOf(),
    selectedIndex: Long,
    selectedId: Long,
    onClick: (Long) -> Unit,
    deleteCallBack: (Long) -> Unit,
    successCallBack: (Long) -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
) {
    ExpoAndroidTheme { colors, _ ->
        LazyColumn(
            modifier = modifier
                .background(color = colors.white)
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(
                items = item,
                key = { _, item -> item.id },
            ) { index, item ->
                SignUpRequestListItem(
                    index = index + 1,
                    selectedIndex = selectedIndex,
                    data = item,
                    horizontalScrollState = horizontalScrollState,
                    onClick = onClick,
                )
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 74.dp)
                ) {
                    UserAllowButton(
                        enabled = selectedId != 0L,
                        onClick = {
                            if (selectedId == 0L) {
                                onErrorToast(null, R.string.check_sign_up_request_list_item)
                            } else {
                                successCallBack(selectedId)
                            }
                        },
                    )

                    UserDeleteButton(
                        enabled = selectedId != 0L,
                        onClick = {
                            if (selectedId == 0L) {
                                onErrorToast(null, R.string.check_sign_up_request_list_item)
                            } else {
                                deleteCallBack(selectedId)
                            }
                        },
                    )
                }
            }
        }
    }
}
