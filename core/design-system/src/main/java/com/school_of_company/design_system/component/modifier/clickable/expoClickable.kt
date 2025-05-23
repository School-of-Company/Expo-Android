package com.school_of_company.design_system.component.modifier.clickable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role

fun Modifier.expoClickable(
    enabled: Boolean = true,
    isIndication: Boolean = false,
    rippleColor: Color? = null,
    onClickLabel: String? = null,
    role: Role? = null,
    interactionSource: MutableInteractionSource? = null,
    interval: Long = 1_000L,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "expoClickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val currentOnClick = rememberUpdatedState(onClick)
    val multipleEventsCutter = remember { MultipleEventsCutter.get(intervalMs = interval) }
    val rememberedInteractionSource = remember { interactionSource ?: MutableInteractionSource() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { currentOnClick.value() } },
        role = role,
        indication = if (isIndication) {
            rippleColor?.let {
                ripple(
                    color = it,
                    bounded = false
                )
            } ?: LocalIndication.current
        } else null,
        interactionSource = rememberedInteractionSource,
    )
}
