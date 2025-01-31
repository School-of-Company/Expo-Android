package com.school_of_company.expo.view.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.modifier.clickable.expoClickable
import com.school_of_company.design_system.theme.color.ExpoColor
import com.school_of_company.model.model.juso.JusoModel

@Composable
internal fun AddressSearchResultItem(
    modifier: Modifier = Modifier,
    result: JusoModel,
    onClick: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .expoClickable { onClick(result.roadAddr) }
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = result.roadAddr,
            color = ExpoColor.gray300,
            fontWeight = FontWeight(400),
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
        )
    }
}