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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

/**
 * Defines the placement of navigation controls relative to the scrollable content.
 */
sealed class NavigationAlignment {
    /** Place both navigation controls at the top (or start). */
    object Top : NavigationAlignment()
    /** Place both navigation controls at the bottom (or end). */
    object Bottom : NavigationAlignment()
    /** Place both navigation controls at the start. */
    object Start : NavigationAlignment()
    /** Place both navigation controls at the end. */
    object End : NavigationAlignment()
    /** Place controls horizontally opposite (e.g., Start and End). */
    object Horizontal : NavigationAlignment()
    /** Place controls vertically opposite (e.g., Top and Bottom). */
    object Vertical : NavigationAlignment()
}

/**
 * Represents the scroll orientation of a QuickNav component.
 */
sealed class QuickNavOrientation {
    object Horizontal : QuickNavOrientation()
    object Vertical : QuickNavOrientation()
}

/**
 * Encapsulates layout settings for QuickNav components based on their orientation.
 *
 * Use this class to define how items are arranged and aligned within the scrollable container.
 */
sealed class QuickNavLayoutSpec {
    /**
     * Vertical layout settings typically used for LazyColumn.
     *
     * @property arrangement The vertical spacing between items.
     * @property alignment The horizontal alignment of items within the column.
     */
    data class Vertical(
        val arrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
        val alignment: Alignment.Horizontal = Alignment.Start
    ) : QuickNavLayoutSpec()

    /**
     * Horizontal layout settings typically used for LazyRow.
     *
     * @property arrangement The horizontal spacing between items.
     * @property alignment The vertical alignment of items within the row.
     */
    data class Horizontal(
        val arrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
        val alignment: Alignment.Vertical = Alignment.Top
    ) : QuickNavLayoutSpec()
}

/**
 * Default [QuickNavLayoutSpec] configurations.
 */
object QuickNavLayoutDefaults {
    /** Default vertical layout spec with 12dp spacing and start alignment. */
    val Vertical = QuickNavLayoutSpec.Vertical()
    /** Default horizontal layout spec with 12dp spacing and top alignment. */
    val Horizontal = QuickNavLayoutSpec.Horizontal()
}
