/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.defaults

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing the icons used across QuickNav components.
 *
 * @property up Icon for scrolling up (Vertical).
 * @property down Icon for scrolling down (Vertical).
 * @property left Icon for scrolling left (Horizontal).
 * @property right Icon for scrolling right (Horizontal).
 */
data class QuickNavIcons(
    val up: ImageVector,
    val down: ImageVector,
    val left: ImageVector,
    val right: ImageVector
)

/** Default icon set using Material standard Keyboard arrows. */
object QuickNavIconDefaults {
    val default = QuickNavIcons(
        up = Icons.Filled.KeyboardArrowUp,
        down = Icons.Filled.KeyboardArrowDown,
        left = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        right = Icons.AutoMirrored.Filled.KeyboardArrowRight
    )
}

/**
 * Encapsulates enter and exit transitions for navigation buttons.
 *
 * @property enter The transition used when a button becomes visible.
 * @property exit The transition used when a button is hidden.
 */
data class QuickNavTransitions(
    val enter: EnterTransition,
    val exit: ExitTransition
)

/** Default transitions using a subtle fade and vertical slide. */
object QuickNavTransitionDefaults {
    val default = QuickNavTransitions(
        enter = TransitionDefaults.fadeIn,
        exit = TransitionDefaults.fadeOut
    )
}

/** CompositionLocal for accessing [QuickNavLabels]. Defaults to null (reverts to Edged defaults). */
val LocalQuickNavLabels = staticCompositionLocalOf<QuickNavLabels?> { null }
/** CompositionLocal for accessing [QuickNavIcons]. Defaults to standard arrows. */
val LocalQuickNavIcons = staticCompositionLocalOf { QuickNavIconDefaults.default }
/** CompositionLocal for accessing [QuickNavTransitions]. Defaults to standard fade/slide. */
val LocalQuickNavTransitions = staticCompositionLocalOf { QuickNavTransitionDefaults.default }
/** CompositionLocal for accessing [QuickNavDimensions]. Defaults to M3 tokens. */
val LocalQuickNavDimensions = staticCompositionLocalOf { QuickNavDimensionDefaults.default }

/**
 * Theme wrapper for QuickNav components.
 *
 * Use this at the top of your UI tree to globally configure the look and feel
 * of all QuickNav lists and grids in your application.
 *
 * @param labels Custom labels/tags (optional).
 * @param icons Custom icon set (optional).
 * @param transitions Custom visibility transitions (optional).
 * @param dimensions Custom dimension tokens (optional).
 * @param content The UI content that will consume these theme settings.
 */
@Composable
fun QuickNavTheme(
    labels: QuickNavLabels? = null,
    icons: QuickNavIcons = QuickNavIconDefaults.default,
    transitions: QuickNavTransitions = QuickNavTransitionDefaults.default,
    dimensions: QuickNavDimensions = QuickNavDimensionDefaults.default,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalQuickNavLabels provides labels,
        LocalQuickNavIcons provides icons,
        LocalQuickNavTransitions provides transitions,
        LocalQuickNavDimensions provides dimensions,
        content = content
    )
}

/**
 * Helper object to access the current QuickNav theme settings from within Composables.
 */
object QuickNavTheme {
    /** Returns the current [QuickNavLabels]. If none is provided, returns [QuickNavLabelDefaults.edgedLabels]. */
    val labels: QuickNavLabels
        @Composable
        get() = LocalQuickNavLabels.current ?: QuickNavLabelDefaults.edgedLabels()

    /** Returns the current [QuickNavIcons] from the theme. */
    val icons: QuickNavIcons
        @Composable
        get() = LocalQuickNavIcons.current

    /** Returns the current [QuickNavTransitions] from the theme. */
    val transitions: QuickNavTransitions
        @Composable
        get() = LocalQuickNavTransitions.current

    /** Returns the current [QuickNavDimensions] from the theme. */
    val dimensions: QuickNavDimensions
        @Composable
        get() = LocalQuickNavDimensions.current
}
