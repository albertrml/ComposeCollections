/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.collections.samples.collections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.collections.layout.list.CollectionPagedList

@Preview(showBackground = true, name = "Custom FAB Control Sample")
@Composable
fun CustomControlSample() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            val scope = rememberCoroutineScope()
            
            CollectionPagedList(
                modifier = Modifier.fillMaxSize(),
                showIndicator = true,
                isOverlay = true, // Important: FAB needs to float over the content
                expandLayout = true,
                backwardControl = { state ->
                    Box(Modifier.fillMaxSize().padding(16.dp)) {
                        AnimatedVisibility(
                            visible = state.showScrollToBackward,
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            FloatingActionButton(
                                onClick = { state.animateScrollToBackward(scope) },
                                containerColor = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(Icons.Default.ArrowUpward, contentDescription = "Go Up")
                            }
                        }
                    }
                },
                forwardControl = {}
            ) {
                items(100) { ListItem(it) }
            }
        }
    }
}
