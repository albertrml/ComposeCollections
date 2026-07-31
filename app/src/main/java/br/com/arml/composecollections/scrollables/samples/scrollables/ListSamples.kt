/*
 * Copyright 2026 Albert Richard Moraes Lopes
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.arml.composecollections.scrollables.samples.scrollables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arml.composecollections.scrollables.defaults.NavigationAlignment
import br.com.arml.composecollections.scrollables.defaults.QuickNavLayoutSpec
import br.com.arml.composecollections.scrollables.layout.list.EdgedList
import br.com.arml.composecollections.scrollables.layout.list.PagedList

@Composable
fun ListItem(index: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = "Item #$index",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true, name = "Vertical Paged List")
@Composable
fun VerticalPagedListSample() {
    MaterialTheme {
        PagedList(
            modifier = Modifier.fillMaxSize(),
            navigationAlignment = NavigationAlignment.Bottom
        ) {
            items(100) { ListItem(it) }
        }
    }
}

@Preview(showBackground = true, name = "Horizontal Edged List")
@Composable
fun HorizontalEdgedListSample() {
    MaterialTheme {
        EdgedList(
            modifier = Modifier.fillMaxWidth(),
            layoutSpec = QuickNavLayoutSpec.Horizontal(),
            navigationAlignment = NavigationAlignment.End
        ) {
            items(50) { index ->
                Surface(
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("#$index", modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Vertical Overlay List")
@Composable
fun VerticalOverlayListSample() {
    MaterialTheme {
        PagedList(
            modifier = Modifier.fillMaxSize(),
            isOverlay = true,
            navigationAlignment = NavigationAlignment.Vertical
        ) {
            items(100) { ListItem(it) }
        }
    }
}

@Preview(showBackground = true, name = "Horizontal Paged List")
@Composable
fun HorizontalPagedListSample() {
    MaterialTheme {
        PagedList(
            modifier = Modifier.fillMaxWidth(),
            layoutSpec = QuickNavLayoutSpec.Horizontal(),
            navigationAlignment = NavigationAlignment.Bottom
        ) {
            items(100) { index ->
                Surface(
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Page #$index", modifier = Modifier.padding(32.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Sticky Header List")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StickyHeaderListSample() {
    MaterialTheme {
        PagedList(
            modifier = Modifier.fillMaxSize(),
            navigationAlignment = NavigationAlignment.End
        ) {
            val grouped = (0..100).groupBy { it / 10 }
            grouped.forEach { (header, items) ->
                stickyHeader {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.tertiary,
                    ) {
                        Text(
                            "Grupo $header",
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }
                items(items) { index ->
                    ListItem(index)
                }
            }
        }
    }
}
