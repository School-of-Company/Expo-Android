package com.school_of_company.design_system.component.bottomsheet

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun <T> SettingBottomSheet(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedItem: T?,
    onUpdateItem: (T) -> Unit,
    content: @Composable (item: T, updateItem: (T) -> Unit) -> Unit
) {
    if (isOpen) {
        Dialog(onDismissRequest = onDismiss) {
            if (selectedItem != null) {
                content(selectedItem, onUpdateItem)
            } else {
                Log.d("ExpoModifyScreen - SettingBottomSheet", "selectedItem is null")
            }
        }
    }
}