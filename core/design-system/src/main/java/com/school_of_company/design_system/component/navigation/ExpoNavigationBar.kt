package com.school_of_company.design_system.component.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.design_system.theme.color.ExpoColor

@Composable
fun RowScope.ExpoNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable () -> Unit,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = label,
        icon = if (selected) selectedIcon else icon,
        enabled = enabled,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = ExpoColor.main,
            unselectedIconColor = ExpoColor.gray500,
            selectedTextColor = ExpoColor.main,
            unselectedTextColor = ExpoColor.gray500,
            indicatorColor = ExpoColor.white
        )
    )
}

@Composable
fun ExpoNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    ExpoAndroidTheme { colors, _ ->
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = colors.gray200
            )
            NavigationBar(
                modifier = modifier,
                containerColor = colors.white,
                contentColor = colors.gray500,
                tonalElevation = 0.dp,
                content = content
            )
        }
    }
}

@Preview
@Composable
fun ExpoNavigationPreview() {
    val items = listOf(
        "프로필",
        "홈",
        "프로그램",
        "명단 관리",
        "박람회 생성",
    )
    val icons = listOf(
        R.drawable.ic_house,
        R.drawable.ic_program,
        R.drawable.ic_append,
        R.drawable.ic_expo,
        R.drawable.ic_user,
    )
    ExpoAndroidTheme { colors, typography ->
        ExpoNavigationBar {
            items.forEachIndexed { index, item ->
                ExpoNavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item
                        )
                    },
                    selectedIcon = {
                        Icon(
                            painter = painterResource(id = icons[index]),
                            contentDescription = item,
                            tint = colors.main
                        )
                    },
                    label = {
                        Text(
                            text = item,
                            style = typography.captionRegular2
                        )
                    },
                    selected = index == 0,
                    onClick = {},
                )
            }
        }
    }
}