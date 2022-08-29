package com.enciyo.jetquitsmoking.ui.theme

import android.widget.Space
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val MaterialTheme.paddings: Spacer
    get() = Spacer()


data class Spacer(
    val pagePadding: Dp = 12.dp,
    val itemSpace: Dp = 4.dp
)

data class CustomShapes(
    val primaryButtonShapes: RoundedCornerShape = RoundedCornerShape(18.dp),
)


val MaterialTheme.customShapes
    @ReadOnlyComposable
    get() = CustomShapes()

