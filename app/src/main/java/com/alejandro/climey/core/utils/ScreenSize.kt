package com.alejandro.climey.core.utils

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable

enum class ScreenSize {
    Compact,
    CompactLandscape,
    Medium,
    Expanded
}

@Composable
fun getDeviceSize(): ScreenSize {
    val windowSize = currentWindowAdaptiveInfo(true).windowSizeClass

    return when {
        windowSize.isAtLeastBreakpoint(840, 480) -> ScreenSize.Expanded
        windowSize.isAtLeastBreakpoint(600, 480) -> ScreenSize.Medium
        windowSize.isHeightAtLeastBreakpoint(480) -> ScreenSize.Compact
        else -> ScreenSize.CompactLandscape
    }
}