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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment

/**
 * Defines the placement of navigation controls relative to the scrollable content.
 */
@Stable
sealed class CollectionAlignment {
    /** No navigation controls are displayed. */
    object None : CollectionAlignment()
    /** Place both navigation controls at the top (or start). */
    object Top : CollectionAlignment()
    /** Place both navigation controls at the bottom (or end). */
    object Bottom : CollectionAlignment()
    /** Place both navigation controls at the start. */
    object Start : CollectionAlignment()
    /** Place both navigation controls at the end. */
    object End : CollectionAlignment()
    /** Place controls horizontally opposite (e.g., Start and End). */
    object Horizontal : CollectionAlignment()
    /** Place controls vertically opposite (e.g., Top and Bottom). */
    object Vertical : CollectionAlignment()
}

/**
 * Represents the scroll orientation of a Collection component.
 */
@Stable
sealed class CollectionOrientation {
    object Horizontal : CollectionOrientation()
    object Vertical : CollectionOrientation()
}

/**
 * Defines the navigation behavior mode.
 */
@Immutable
enum class CollectionMode {
    /** Navigates to the absolute start or end of the collection. */
    Edged,
    /** Navigates through the collection one visible viewport (page) at a time. */
    Paged
}

/**
 * Encapsulates layout settings for Collection components based on their orientation.
 */
@Stable
sealed class CollectionLayoutSpec {
    data class Vertical(
        val arrangement: Arrangement.Vertical = Arrangement.spacedBy(CollectionDimensionDefaults.default.itemSpacing),
        val alignment: Alignment.Horizontal = Alignment.Start
    ) : CollectionLayoutSpec()

    data class Horizontal(
        val arrangement: Arrangement.Horizontal = Arrangement.spacedBy(CollectionDimensionDefaults.default.itemSpacing),
        val alignment: Alignment.Vertical = Alignment.Top
    ) : CollectionLayoutSpec()
}

/**
 * Default [CollectionLayoutSpec] configurations.
 */
object CollectionLayoutDefaults {
    val Vertical = CollectionLayoutSpec.Vertical()
    val Horizontal = CollectionLayoutSpec.Horizontal()
}
