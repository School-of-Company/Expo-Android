package com.school_of_company.form.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.icon.DownArrowIcon
import com.school_of_company.design_system.icon.UpArrowIcon
import com.school_of_company.design_system.theme.ExpoAndroidTheme
import com.school_of_company.form.enum.FormType

@Composable
internal fun FormDropDown(
    modifier: Modifier = Modifier,
    currentItem: FormType,
    onItemClick: (FormType) -> Unit,
) {
    val expanded = rememberSaveable { mutableStateOf(false) }

    ExpoAndroidTheme { colors, typography ->
        Column(modifier = modifier) {
            Row(
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = colors.gray500,
                        shape = RoundedCornerShape(size = 6.dp),
                    )
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(size = 6.dp),
                    )
                    .expoClickable { expanded.value = true }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
            ) {
                Icon(
                    painter = painterResource(currentItem.iconRes),
                    contentDescription = null,
                    tint = colors.gray500
                )

                Text(
                    text = currentItem.typeName,
                    style = typography.captionRegular1,
                    fontWeight = FontWeight.W400,
                    color = colors.gray400,
                )

                if (expanded.value) {
                    UpArrowIcon(tint = colors.gray500)
                } else {
                    DownArrowIcon(tint = colors.gray500)
                }
            }

            DropdownMenu(
                modifier = Modifier
                    .background(
                        color = colors.white,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = colors.gray200,
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .padding(16.dp),
                shadowElevation = 12.dp,
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(space = 22.dp)) {
                    FormType.values()
                        .filter { item ->
                            item.typeName != currentItem.typeName
                        }
                        .forEach { filteredItem ->
                            val interactionSource = remember { MutableInteractionSource() }
                            val isPressed by interactionSource.collectIsPressedAsState()

                            Row(
                                modifier = Modifier
                                    .expoClickable(interactionSource = interactionSource) {
                                        onItemClick(filteredItem)
                                        expanded.value = false
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(filteredItem.iconRes),
                                    tint = if (isPressed) colors.main else colors.gray500,
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = filteredItem.typeName,
                                    style = typography.captionRegular1,
                                    fontWeight = FontWeight.W400,
                                    color = if (isPressed) colors.main else colors.gray500,
                                )
                            }
                        }
                }
            }
        }
    }
}


@Preview
@Composable
private fun FormDropDownPreview() {
    var currentItem = remember {
        mutableStateOf(FormType.SENTENCE)
    }
    FormDropDown(
        onItemClick = { currentItem.value = it },
        currentItem = currentItem.value
    )
}