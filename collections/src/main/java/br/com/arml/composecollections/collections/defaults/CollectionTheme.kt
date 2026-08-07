/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.defaults

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class CollectionIcons(
    val up: ImageVector,
    val down: ImageVector,
    val left: ImageVector,
    val right: ImageVector
)

object CollectionIconDefaults {
    val default = CollectionIcons(
        up = Icons.Filled.KeyboardArrowUp,
        down = Icons.Filled.KeyboardArrowDown,
        left = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        right = Icons.AutoMirrored.Filled.KeyboardArrowRight
    )
}

@Immutable
data class CollectionTransitions(
    val enter: EnterTransition,
    val exit: ExitTransition
)

object CollectionTransitionDefaults {
    val default = CollectionTransitions(
        enter = CollectionVisibilityTransitions.fadeIn,
        exit = CollectionVisibilityTransitions.fadeOut
    )
}

val LocalCollectionLabels = staticCompositionLocalOf<CollectionLabels?> { null }
val LocalCollectionIcons = staticCompositionLocalOf { CollectionIconDefaults.default }
val LocalCollectionTransitions = staticCompositionLocalOf { CollectionTransitionDefaults.default }
val LocalCollectionDimensions = staticCompositionLocalOf { CollectionDimensionDefaults.default }

@Composable
fun CollectionTheme(
    labels: CollectionLabels? = null,
    icons: CollectionIcons = CollectionIconDefaults.default,
    transitions: CollectionTransitions = CollectionTransitionDefaults.default,
    dimensions: CollectionDimensions = CollectionDimensionDefaults.default,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCollectionLabels provides labels,
        LocalCollectionIcons provides icons,
        LocalCollectionTransitions provides transitions,
        LocalCollectionDimensions provides dimensions,
        content = content
    )
}

object CollectionTheme {
    val labels: CollectionLabels
        @Composable
        get() = LocalCollectionLabels.current ?: CollectionLabelDefaults.edgedLabels()

    val icons: CollectionIcons
        @Composable
        get() = LocalCollectionIcons.current

    val transitions: CollectionTransitions
        @Composable
        get() = LocalCollectionTransitions.current

    val dimensions: CollectionDimensions
        @Composable
        get() = LocalCollectionDimensions.current
}
