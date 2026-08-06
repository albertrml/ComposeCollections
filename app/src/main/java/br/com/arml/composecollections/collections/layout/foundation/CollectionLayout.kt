/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.layout.foundation

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
import br.com.arml.composecollections.collections.defaults.CollectionTheme

@Composable
fun CollectionLayout(
    modifier: Modifier = Modifier,
    isOverlay: Boolean = true,
    contentTop: @Composable (Modifier) -> Unit = {},
    contentBottom: @Composable (Modifier) -> Unit = {},
    contentLeft: @Composable (Modifier) -> Unit = {},
    contentRight: @Composable (Modifier) -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = {},
) {
    val panelSpacing = CollectionTheme.dimensions.panelToContentSpacing
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
