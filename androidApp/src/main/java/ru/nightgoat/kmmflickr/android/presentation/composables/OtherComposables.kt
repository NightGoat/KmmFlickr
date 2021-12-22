package ru.nightgoat.kmmflickr.android.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.nightgoat.kmmflickr.android.presentation.theme.defaultPadding


@Composable
fun SimpleSpacer(size: Dp = defaultPadding) {
    Spacer(modifier = Modifier.size(size))
}