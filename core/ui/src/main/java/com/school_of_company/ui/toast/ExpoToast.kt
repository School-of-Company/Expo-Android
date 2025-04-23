package com.school_of_company.ui.toast

import android.content.Context
import android.widget.Toast

fun makeToast(
    context: Context,
    toastMessage: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(
        context,
        toastMessage,
        duration
    ).show()
}