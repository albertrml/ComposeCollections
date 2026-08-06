/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.layout.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.scrollables.defaults.QuickNavTheme

/**
 * A layout scaffold that organizes content and navigation controls.
 *
 * This component supports two layout modes:
 * 1. **Stacked**: Navigation controls are placed outside the scrollable content, either
 * above/below (Column) or on the sides (Row).
 * 2. **Overlay**: Navigation controls float on top of the content at the specified boundaries.
 *
 * It uses `weight` and `wrapContent` modifiers to ensure the content dictates the size
 * while keeping the layout stable during navigation button animations.
 *
 * @param modifier The modifier to be applied to the scaffold root.
 * @param isOverlay If true, navigation slots will float over the [content].
 * @param contentTop Slot for navigation controls at the top boundary.
 * @param contentBottom Slot for navigation controls at the bottom boundary.
 * @param contentLeft Slot for navigation controls at the left boundary.
 * @param contentRight Slot for navigation controls at the right boundary.
 * @param content The main scrollable content of the scaffold.
 */
@Composable
fun QuickNavLayout(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = true,
    contentTop: @Composable (Modifier) -> Unit = {},
    contentBottom: @Composable (Modifier) -> Unit = {},
    contentLeft: @Composable (Modifier) -> Unit = {},
    contentRight: @Composable (Modifier) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    val panelSpacing = QuickNavTheme.dimensions.panelToContentSpacing
    if (isOverlay) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            content(PaddingValues(0.dp))
            Box(Modifier.align(Alignment.TopCenter)) { contentTop(Modifier) }
            Box(Modifier.align(Alignment.BottomCenter)) { contentBottom(Modifier) }
            Box(Modifier.align(Alignment.CenterStart)) { contentLeft(Modifier) }
            Box(Modifier.align(Alignment.CenterEnd)) { contentRight(Modifier) }
        }
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(panelSpacing)
        ) {
            contentTop(Modifier)
            Row(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(panelSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                contentLeft(Modifier)
                Box(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .wrapContentWidth()
                ) {
                    content(PaddingValues(0.dp))
                }
                contentRight(Modifier)
            }
            contentBottom(Modifier)
        }
    }
}
