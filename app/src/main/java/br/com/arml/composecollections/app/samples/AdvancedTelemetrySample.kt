/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.app.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.collections.components.indicators.CollectionPageCounter
import br.com.arml.composecollections.collections.components.indicators.CollectionScrollbar
import br.com.arml.composecollections.collections.defaults.CollectionAlignment
import br.com.arml.composecollections.collections.defaults.CollectionMode
import br.com.arml.composecollections.collections.layout.list.CollectionList
import br.com.arml.composecollections.collections.state.rememberCollectionListState

@Preview(showBackground = true, name = "Advanced Telemetry Sample")
@Composable
fun AdvancedTelemetrySample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            val state = rememberCollectionListState(
                mode = CollectionMode.Paged,
            )
            
            Box(Modifier.fillMaxSize()) {
                CollectionList(
                    state = state,
                    modifier = Modifier.fillMaxSize(),
                    navigationAlignment = CollectionAlignment.Top,
                    expandLayout = true,
                    showIndicator = true // Standard linear indicator
                ) {
                    // Constant item height for accurate page counting
                    items(100) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(8.dp)
                                .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Item #$index")
                        }
                    }
                }

                // Modern Scrollbar on the right
                CollectionScrollbar(
                    progress = state.scrollProgress,
                    isScrolling = state.isScrolling,
                    isHorizontal = false,
                    modifier = Modifier.align(Alignment.CenterStart)
                )

                // Page Counter at the bottom
                CollectionPageCounter(
                    currentPage = state.currentPage,
                    totalPages = state.totalPages,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 32.dp)
                )
            }
        }
    }
}
