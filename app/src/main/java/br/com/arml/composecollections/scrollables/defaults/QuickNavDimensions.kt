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

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Data class representing the dimensions and spacing tokens used across QuickNav components.
 *
 * Values are based on Material Design 3 spacing and layout guidelines.
 *
 * @property panelToContentSpacing Gap between the navigation panels and the scrollable content.
 * @property itemSpacing Standard spacing between items in a list or grid.
 * @property buttonSpacing Spacing between navigation buttons within the same panel.
 * @property indicatorThickness Height (horizontal) or width (vertical) of the progress indicator.
 */
data class QuickNavDimensions(
    val panelToContentSpacing: Dp,
    val itemSpacing: Dp,
    val buttonSpacing: Dp,
    val indicatorThickness: Dp
)

/**
 * Default dimension tokens following Material Design 3 guidelines.
 */
object QuickNavDimensionDefaults {
    val default = QuickNavDimensions(
        panelToContentSpacing = 4.dp, // Small gap for close association
        itemSpacing = 12.dp,         // Standard M3 list item spacing
        buttonSpacing = 8.dp,       // M3 small component spacing
        indicatorThickness = 4.dp   // M3 linear progress indicator thickness
    )
}
